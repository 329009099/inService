package com.suyin.common;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.suyin.common.zxing.MatrixToImageWriter;
import com.suyin.decorate.controller.CreateQrcodeController;
public class QrCodeWriterUtils {

    private final static Logger log=Logger.getLogger(QrCodeWriterUtils.class);
	/**
	 * 
	 * @param openid  用户微信openid
	 * @param text    二维码内置参数 tourl 扫描二维码时，跳转的路径
	 * @param tempPath 生成二维码时，定义的系统临时存放路径
	 * @return
	 * @throws WriterException
	 * @throws IOException
	 */
	public static synchronized String createQrcodeImage(String openid,String text,String tempPath) throws WriterException, IOException{
		MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
	
		String cordUrl="";
		try{
			BitMatrix bitMatrix = multiFormatWriter.encode(text+" ", BarcodeFormat.QR_CODE, 400, 400, hints);
			File path=new File(tempPath);
			if(!path.exists()){
				path.mkdirs();
			}
			//二维码临时路径/用户openid/.jpg 
			File file = new File(tempPath+openid+".jpg");
			if(!file.exists()){
				file.mkdir();
			}
			MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file);
			cordUrl=tempPath+openid+".jpg";
		}catch(Exception ex){
			log.error(ex);
		}
		return cordUrl;
	}
	public static void main(String[] args) {

		try {
			String content = "http://lizheng.nat100.top/wxService/decorate/share.html?expId=1&publishopenid=oEWBhuH1TWxGFhibxzLM4XYtbDYo";

			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

			Map hints = new HashMap();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			content+=" ";
			BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);

			File file = new File("d:\\picpic3.jpg");
			if(!file.exists()){
				file.mkdir();
			}
			MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
