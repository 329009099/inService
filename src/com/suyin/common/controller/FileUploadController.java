package com.suyin.common.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;

import com.suyin.common.model.Attachment;
import com.suyin.common.service.AttachmentService;
import com.suyin.common.service.ModuleNameService;

/**
 * 1.接受base格式的图片，保持
 * 2.接受文件流形式的图片，保持
 * @author Administrator
 *
 */
@RequestMapping("/fileUpload")
@Controller
public class FileUploadController {
	private Logger log = Logger.getLogger(FileUploadController.class);
	
	@Autowired
	private AttachmentService attachmentService;
	
	/**
	 * 目前微信端用户头像是压缩图片后生成的是base64位字符串，在该方法中把这个字符串转变为图片存起来
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/upload")
	public @ResponseBody Map<String,String> save(HttpServletRequest request) throws Exception {
		Map<String,String> result=new HashMap<String, String>();
		String imageData = request.getParameter("base64");
		String fileName=request.getParameter("name");
		String module=request.getParameter("module");
		log.info("uploading a file :");
		log.info("fileName :"+fileName);
		if (null == imageData || imageData.length() < 100) {
			result.put("flag", "1");
			result.put("msg", "发生未知错误");
			return result;
		} 
		if(!this.checkExtension(fileName)) {
			result.put("flag", "2");
			result.put("msg", "仅允许上传"+this.getAllowedFileTypes()+"格式的图片");
			return result;
		}
		imageData = imageData.substring(imageData.indexOf("base64,")+7);
		byte[] data = decode(imageData);
		if(data.length>10000000*8) {
			result.put("flag", "3");
			result.put("msg", "图片太大了");
			return result;
		}
		String rootUrl=this.getRootUrl(request);
		String rootPath=this.getRootPath(request);
		String newFileName=this.getNewFile(fileName);
		System.out.println(rootPath);
		log.info("saving a file :");
		log.info("rootUrl: "+rootUrl);
		log.info("rootPath: "+rootPath);
		log.info("newFileName: "+newFileName);
		try {
			if(saveImageToDisk(data,rootPath, newFileName)) {
				result.put("flag", "0");
				result.put("url", rootUrl+newFileName);
				result.put("attach", saveAttach(module,Integer.valueOf(data.length/8192), rootUrl, rootPath,newFileName));
				
			}else {
				result.put("flag", "4");
				result.put("msg", "图片存储错误了");
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			result.put("flag", "1");
			result.put("msg", "发生未知错误");
		}
		return result;
	}


	
	/**
	 * 该方法是针对传输文件流的方式的
	 * @param imgFile
	 * @param request
	 * @return
	 */
	@RequestMapping("/up")
	public @ResponseBody String upload(@RequestParam("imgFile")MultipartFile[] imgFile,HttpServletRequest request) {
		log.info("this is file upload begin");
		MultipartFile file=null;
		JSONObject jso = new JSONObject();
		if(imgFile.length==1) file=imgFile[0];
		if(imgFile.length>1) {
			log.info("sorry, but this controller only allow to upload one file one time"); 
			jso.put("error", 1);
			jso.put("msgId", "1");
			jso.put("msg", "sorry, but this controller only allow to upload one file one time");
			return jso.toString();
		}
		if(file.isEmpty()) {
			log.info("sorry, but you upload a empty file"); 
			jso.put("error", 1);
			jso.put("msgId", "2");
			jso.put("msg", "sorry, but you upload a empty file");
			return jso.toString();			
		}
		if(!this.checkExtension(file.getOriginalFilename())) {
			log.info("sorry, but you upload a empty file"); 
			jso.put("error", 1);
			jso.put("msgId", "3");
			jso.put("msg", "sorry, but the extension of the file is invalid");
			return jso.toString();				
		}
		try {
			String module=request.getParameter("module");
			String saveUrlRoot =this.getRootUrl(request);
			String savePathRoot=this.getRootPath(request);
			File directory=new File(savePathRoot);
			if(!directory.exists())
				directory.mkdirs();
			String newName=this.getNewFile( file.getOriginalFilename());
			File newFile=new File(savePathRoot,newName);
			log.info("save the file in folder: "+savePathRoot);
			FileUtils.copyInputStreamToFile(file.getInputStream(),newFile);
			jso.put("error", 0);
			jso.put("attach", this.saveAttach(module,Long.valueOf(file.getSize()/8192).intValue(), saveUrlRoot+newName, savePathRoot+newName, newName));
			jso.put("url", thumbNail(newFile,saveUrlRoot));
		
		} catch (IOException e) {
			log.error("exception occurs when uploading");
			log.error(e.getMessage());
			jso.put("error", 1);
			jso.put("msgId", "4");
			jso.put("msg", "sorry, but some unknown exception happens");
		}
		log.info("this is file upload end");
		return jso.toString();
	}
	
	/**
	 * 
	 * @param module 模块名称
	 * @param fileSize 
	 * @param rootUrl
	 * @param rootPath
	 * @param newFileName
	 * @return
	 */
	private String saveAttach(String module,int fileSize, String rootUrl, String rootPath, String newFileName) {
		Attachment attach=new Attachment();
		attach.setDisplay_path(rootUrl+newFileName);
		attach.setFile_name(newFileName);
		attach.setFile_path(rootPath+newFileName);
		attach.setFile_size(fileSize);
		attach.setModule(module);
		attach.setFile_type(this.getExtension(newFileName));
		this.attachmentService.addAttachment(attach);
		return String.valueOf(attach.getAttachment_id());
	}
	
	/**
	 * 是否需要生成缩略图，如果要，就生成，并返回缩略图的文件名
	 * 默认不需要生成缩略图，直接返回原来的文件名
	 * @param image
	 * @return
	 */
	private String thumbNail(File image,String saveUrlRoot) {
		return saveUrlRoot+image.getName();
	}
	

	/**
	 * 获得文件存储的根目录，可以在子类中重写方法以获得不同的地址
	 * 默认是在存储在/WEB-INF/resources/outImages中（因为spring的配置了resources,如果要改为不在/WEB-INF/resources/中，那就比较麻烦了）
	 * @param request
	 * @return
	 */
	private String getRootPath(HttpServletRequest request) {
		//测试
		//return request.getSession().getServletContext().getRealPath("/WEB-INF/resources/outImages")+"/"+this.getParentPath(request);
		//正式
		return "/mydata/notuan_imgs/outImages/inService/"+this.getParentPath(request);
	}
	
	/**
	 * 获得文件的根url，可以在子类中重写方法
	 * 默认是 resources/outImages/XX（因为spring的配置了resources,如果要改为不已resources开头，那就比较麻烦了）
	 * @param request
	 * @return
	 */
	private String getRootUrl(HttpServletRequest request) {
		//测试
		//return getLocalURL(request)+"/resources/outImages/"+this.getParentPath(request);
		//正式
		return getLocalURL(request)+"/resources/outImages/inService/"+this.getParentPath(request);
	}
	
	private String getLocalURL(HttpServletRequest request) {
		//测试
		//return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		//正式
		return request.getContextPath();
	}
	
	//尽管存在一个outImage文件夹了，但是齐心赚和其他如评论，用户头像是存在不同的文件夹里的
	private String getParentPath(HttpServletRequest request) {
		if(ModuleNameService.USER_EXP_ZHUAN.equals(request.getParameter("module"))) {
			String temp=request.getParameter("exp_id");
			if(StringUtils.isBlank(temp))
				return "qixin/"+getDateString();
			else
				return "qixin/"+temp+"/"+getDateString();
		}else {
			return request.getParameter("module")+"/"+getDateString();
		}
	}
	
	
	private String getDateString() {
		return new SimpleDateFormat("yyyyMMdd").format(new Date())+"/";
	}
	
	
	/**
	 * 改写文件名，默认使用时间加随机数的算法，
	 * 可以在子类中重写方法以获得不同的文件名
	 * @param oldFileName
	 * @return
	 */
	private String getNewFile(String oldFileName) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		return df.format(new Date()) + "_" + new Random().nextInt(1000) + "."+this.getExtension(oldFileName);
	}
	
	/**
	 * 判断后缀名是否合法，可以重写
	 * @param fileName
	 * @return
	 */
	private boolean checkExtension(String fileName) {
		return this.getAllowedFileTypes().indexOf(this.getExtension(fileName))>=0;
	}
	/**
	 * 获得所有合法的文件后缀名，可以重写。
	 * 默认"gif", "jpg", "jpeg", "png", "bmp", "png" 合法
	 * @return
	 */
	private String getAllowedFileTypes() {
		return "gif,jpg,jpeg,png,bmp";  
	}
	/**
	 * 获得文件后缀名
	 * @param fileName
	 * @return
	 */
	private String getExtension(String fileName) {
		int index=fileName.lastIndexOf(".");
		if(index<0)
			throw new RuntimeException("the extension of the file is invalid");
		return fileName.substring(index+1).toLowerCase();
	}
	
	
	
	private boolean saveImageToDisk(byte[] data, String rootPath,String imageName){
		File root=new File(rootPath);
		if(!root.exists()){
			root.mkdirs();
		}
		FileOutputStream outputStream=null;
		try {
			outputStream = new FileOutputStream(new File(root, imageName));
			outputStream.write(data);
			outputStream.flush();
			outputStream.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			try {
				if(outputStream!=null) outputStream.close();
			} catch (IOException e) {
			}
		}
		return true;
	}
	
	private byte[] decode(String imageData) throws IOException {
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] data = decoder.decodeBuffer(imageData);
		for (int i = 0; i < data.length; ++i) {
			if (data[i] < 0) {
				data[i] += 256;
			}
		}
		return data;
	}
}
