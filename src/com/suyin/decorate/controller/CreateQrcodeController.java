package com.suyin.decorate.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suyin.common.ImageHandleHelper;
import com.suyin.common.QrCodeWriterUtils;
import com.suyin.common.Utils;
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
