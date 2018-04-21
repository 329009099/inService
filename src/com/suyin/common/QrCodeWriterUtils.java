package com.suyin.common;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.suyin.common.zxing.MatrixToImageWriter;
public class QrCodeWriterUtils {
	  public static void main(String[] args) {

	        try {
	            String content = "http://www.baidu.com";
	            
	            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
	            
	            Map hints = new HashMap();
	            hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
	           
	            BitMatrix bitMatrix = multiFormatWriter.encode(content, BarcodeFormat.QR_CODE, 400, 400, hints);
	            
	            File file = new File("d:\\picpic.jpg");
	            MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file);
	            
	        } catch (IOException e) {
	            e.printStackTrace();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	    }
}
