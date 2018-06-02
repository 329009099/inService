package com.suyin.decorate.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.zxing.WriterException;
import com.suyin.common.ImageHandleHelper;
import com.suyin.common.QrCodeWriterUtils;
import com.suyin.common.Utils;
import com.suyin.common.model.CoinLog;
import com.suyin.common.service.CoinLogService;
import com.suyin.decorate.model.Decorate;
import com.suyin.decorate.model.ExpDecorateUser;
import com.suyin.decorate.service.DecorateService;
import com.suyin.decorate.service.ExpDecorateUserService;
import com.suyin.decoratemessage.model.DecorateMessage;
import com.suyin.decoratemessage.service.DecorateMessageService;
import com.suyin.decoraterecord.model.ExpDecorateRecord;
import com.suyin.decoraterecord.service.ExpDecorateRecordService;
import com.suyin.system.util.SystemPropertiesHolder;

/**
 * 海报图片生成处理类
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/qrcode")
public class CreateQrcodeController {

	private final static Logger log=Logger.getLogger(CreateQrcodeController.class);

	@Autowired
	private ExpDecorateRecordService expDecorateRecordService;//邀请记录
	@Autowired
	private DecorateService decorateService;//活动管理信息
	@Autowired
	private ExpDecorateUserService expDecorateUserService;//用户信息
	@Autowired
	private DecorateMessageService decorateMessageService;//消息记录
	/**
	 * 1:根据openid查询用户的图片信息
	 * 2:根据活动id查询活动的信息
	 * 3：根据邀请发起者，和被邀请者，更新发起者的佣金营销费
	 * 4:添加被邀请者查看信息时的数据记录(用户发起者记录，佣金的增长记录，用于发起者个人中心的 我邀请的谁，) sql关联查询
	 * 5:将查询数据组装反馈至share.jsp分享页面
	 * @param request
	 * @return
	 */
	@RequestMapping("/findShareProjecss")
	public @ResponseBody ModelMap findShareProjecss(HttpServletRequest request){
		ModelMap result=new ModelMap();
		String publishopenid=request.getParameter("publishopenid");//发起者id,首次分享人的openid
		String accptopenid=request.getParameter("accptopenid");//接收者openid，被邀请者 二维码或分享链接点击者的
		String id=request.getParameter("id");
		//根据发起者openid查询用户信息
		ExpDecorateUser decorateUser=expDecorateUserService.findUserInfoByUserIdOrOpenId("", publishopenid);
		result.put("user", decorateUser);
		//根据活动id查询活动信息
		Decorate decorate=decorateService.findDecorateById(id);
		result.put("decorate", decorate);
		//活动时间:0 :未开始，1:进行中，2:已结束(活动状态), status 活动状态
		if(1==decorate.getIsActDate() && 1==decorate.getStatus()){		
			//变更发起用户的佣金
			//判断发起者和接受者二者是否为同一id，是不进行操作，反之进行增加
			if(!publishopenid.equals(accptopenid)){
				//查询当前受邀者是否已经查看过，发起者的二维码，或点击过连接，不可进行二次佣金增长
				//根据 发起者和接收者 openid 查询是否存在，已经有记录，则不能继续，无记录的情况下，正常记录
				Map<String,Object>params=new HashMap<String,Object>();
				params.put("publishopenid", publishopenid);
				params.put("accptopenid", accptopenid);
				ExpDecorateRecord exRecord=expDecorateRecordService.findExpRecordByPublisAndReviewOpenid(params);
				if(null!=exRecord){
					//参与过了也可以是人气数，不增加金额了而已
					//expDecorateUserService.updateUserUseNum(publishopenid);
					log.debug("当前用户已经，被邀请过，无法再次邀请..");
				}else{
					try {

						DecimalFormat df= new DecimalFormat("######0.00");	
						//随机金额区间，记录本次佣金金额
						double  randomPrice=Utils.nextDouble(decorate.getBeginMoney().doubleValue(), decorate.getEndMoney().doubleValue());
						//检查总收益是否到达，封顶金额小于等于0时 
						double allowPrice=Utils.subUserPrice(decorate.getMaxMoney().doubleValue(), decorateUser.getCountPrice().doubleValue());
						try{
							//表示还未达到封顶金额
							if(allowPrice>0 && allowPrice>randomPrice){
								//如果达到封顶金额，将直接保存差值
								String commission= df.format(randomPrice);	
								this.crateMoney(decorateUser, publishopenid, accptopenid, commission);
							}else{
								//如果达到封顶金额，将直接保存差值						
								String commission= df.format(allowPrice);
								this.crateMoney(decorateUser, publishopenid, accptopenid, commission);

							}
						}catch(Exception ex){						
							log.error("增加佣金失败，写入订单失败....",ex);
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						log.error("金额转换失败....",e);
					}
				}
			}	
		}
		return result;
	}

	/**
	 * 创建用户金额
	 * 创建金额增加记录
	 * @param decorateUser
	 * @param publishopenid
	 * @param accptopenid
	 * @param commission
	 */
	private  void  crateMoney(ExpDecorateUser decorateUser,String publishopenid,String accptopenid,String commission){
		//最终记录的佣金，保留2位小数点
		//开始变更发起者佣金账户 减少余额
		double banlanzv1=decorateUser.getBalancePrice().doubleValue(); //加数
		double countzv1=decorateUser.getCountPrice().doubleValue();//总额加数
		double v2=Double.parseDouble(commission);//被加数
		//余额
		double commissionbanlanPrice=Utils.addUserPrice(banlanzv1, v2);//前往更新金额
		BigDecimal banlanprice=new BigDecimal(commissionbanlanPrice);
		//总额
		double commissionCountPrice=Utils.addUserPrice(countzv1,v2);//前往更新金额
		BigDecimal countprice=new BigDecimal(commissionCountPrice);	
		//查询受邀人的信息
		ExpDecorateUser invakeUser=expDecorateUserService.findUserInfoByUserIdOrOpenId("", accptopenid);
		//调用更新user账户方法，根据openid 更新余额
		ExpDecorateUser usereEntity=new ExpDecorateUser();
		usereEntity.setOpenid(publishopenid);
		usereEntity.setBalancePrice(banlanprice);
		usereEntity.setCountPrice(countprice);				
		expDecorateUserService.updateBalancePriceByOpendId(usereEntity);

		//1添加消息给发起者，在发起者的我的消息查看
		DecorateMessage message=new DecorateMessage();
		message.setContent("您邀请了用户"+invakeUser.getNickName()+"获得佣金"+commission+"(元)");
		message.setOpenid(publishopenid);
		message.setSendModuleName("分享");
		message.setSendEntity(5);
		decorateMessageService.addDecorateMessage(message);

		//向t_exp_decorate_record表中插入记录，记录详细，用于用户的钱包中的金额详细展示
		ExpDecorateRecord entity=new ExpDecorateRecord();
		entity.setPublishOpenid(publishopenid);
		entity.setAcceptOpenid(accptopenid);
		entity.setState(0);//0发起者，1受邀者
		entity.setCommissionPrice(commission);//本次生成的佣金金额
		entity.setMessage("您邀请了用户"+invakeUser.getNickName()+"获得佣金"+commission+"(元)");         
		entity.setType(0);//收益类型:0分享，1:购买福券返佣金，2签单奖励
		entity.setPriceState(0);//券状态 0:已收益 1:待收益
		expDecorateRecordService.addExpDecorateRecord(entity);

	}

	@RequestMapping(value="ddd")
	public @ResponseBody void ddd(HttpServletRequest request,HttpServletResponse response) throws Exception{
		try {
			String expId=request.getParameter("id");//活动id
			String tourl=request.getParameter("url");//跳转的url(二维码内的连接)
			String openid=request.getParameter("openid");
			//用户二维码生成路径
			String templatePath=request.getSession().getServletContext().getRealPath("/");
			BufferedImage image=QrCodeWriterUtils.createQrcodeImage("lz646775788", "http://www.baidu.com");
			File templatefile=new File(templatePath+"/WEB-INF/resources/template/template1.jpg");
			
		    //BufferedImage 转 InputStream
		    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		    ImageOutputStream imageOutput = ImageIO.createImageOutputStream(byteArrayOutputStream);
		    ImageIO.write(image, "png", imageOutput);
		    InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
		    //合成图片
			QrCodeWriterUtils.generateCode(templatefile.toString(), inputStream, "lz646775788", "lz",response);
		} catch (WriterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 创建海报二维码合成
	 * @param request
	 * @return
	 */
	@RequestMapping("/create")
	public @ResponseBody ModelMap create(HttpServletRequest request){
		ModelMap result=new ModelMap();
		String expId=request.getParameter("id");//活动id
		String tourl=request.getParameter("url");//跳转的url(二维码内的连接)
		String openid=request.getParameter("openid");
		//用户二维码生成路径
		String tempPath=SystemPropertiesHolder.get("QRTEMP_PATH");
		String templatePath=request.getSession().getServletContext().getRealPath("/");
		try{
			//本地模板文件路径
			File templatefile=new File(templatePath+"/WEB-INF/resources/template/template1.jpg");
			if(templatefile.exists()){
				log.debug("templatefile 模板文件存在");
			}
			//检查下二维码是否存在，有的话，就不再次生成了。
			String userCodeUrl="";
			File file = new File(tempPath+openid+".jpg");
			System.out.println(file.toString());
			if(!file.exists()){
				//生成二维码
				userCodeUrl=QrCodeWriterUtils.createQrcodeImage(openid, tourl, tempPath);
			}else{

				userCodeUrl=file.toString();
			}
			//海报输出路径
			String outUrl=SystemPropertiesHolder.get("HAIBAO_PATH");
			File outUrlFile=new File(outUrl);
			if(!outUrlFile.exists()){
				outUrlFile.mkdirs();
			}
			//海报文件查看部分
			String imgName = outUrl + openid + ".png";  
			String haibaoUrl="";
			File outputfile = new File(imgName);  
			if(!outputfile.exists()){
				//海报合成二维码
				haibaoUrl=ImageHandleHelper.generateCode(templatefile.toString(),userCodeUrl,outUrl,openid, openid); 
			}else{
				haibaoUrl="/resources/outImages/haibao/" + openid + ".png";  
			}

			log.debug(haibaoUrl);
			result.put("imgUrl", haibaoUrl);
			//返回图片流
		}catch(Exception ex){
			log.error("海报生成错误");
		}
		return result;
	}
	
}
