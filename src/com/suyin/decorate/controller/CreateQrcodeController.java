package com.suyin.decorate.controller;

import java.io.File;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suyin.common.ImageHandleHelper;
import com.suyin.common.QrCodeWriterUtils;
import com.suyin.common.Utils;
import com.suyin.decorate.model.Decorate;
import com.suyin.decorate.model.ExpDecorateUser;
import com.suyin.decorate.service.DecorateService;
import com.suyin.decorate.service.ExpDecorateUserService;
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
				log.debug("当前用户已经，被邀请过，无法再次邀请..");
			}else{
				try {
					//随机金额区间，记录本次佣金金额
					double  randomPrice=Utils.nextDouble(decorate.getBeginMoney().doubleValue(), decorate.getEndMoney().doubleValue());
					DecimalFormat df= new DecimalFormat("######0.00");
					String commission= df.format(randomPrice);
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
					//调用更新user账户方法，根据openid 更新余额
					ExpDecorateUser usereEntity=new ExpDecorateUser();
					usereEntity.setOpenid(publishopenid);
					usereEntity.setBalancePrice(banlanprice);
					usereEntity.setCountPrice(countprice);
					expDecorateUserService.updateBalancePriceByOpendId(usereEntity);

					//向t_exp_decorate_record表中插入记录，记录详细，用于用户的钱包中的金额详细展示
					ExpDecorateRecord entity=new ExpDecorateRecord();
					entity.setPublishOpenid(publishopenid);
					entity.setAcceptOpenid(accptopenid);
					entity.setState(1);
					entity.setCommissionPrice(commission);//本次生成的佣金金额
					expDecorateRecordService.addExpDecorateRecord(entity);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					log.error("金额转换失败....",e);
				}
			}
		}

		return result;
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

	public static void main(String[] args) {
		double min=0.10;
		double max=1.20;
		try {
			for (int i = 0; i < 1000; i++) {
				double  randomPrice=Utils.nextDouble(min, max);
				System.out.println(randomPrice);
			}
	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
