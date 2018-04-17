package com.suyin.system.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;
public class ClientTokenUtil {
    /*
     * 拼接URL
     */
    public static String getToken(String consumerKey,String version) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException {

        if("".equals(version) || version == null){
            version = "1.0";
        }
        String signature = getSignature(consumerKey,version);

        System.out.println(signature);
        return signature;
    }
    /*
     * 依据传递的参数拼接baseString
     */
    public static String initBaseString(String consumerKey,String version){

        StringBuffer paramsStr = new StringBuffer();
        paramsStr.append("oauth_consumer_key="+consumerKey);
        paramsStr.append("&oauth_version="+version);
        String str=encoderStr(paramsStr.toString());
        return str;
    }

    //生成密钥
    public static String getSignature(String consumerKey,String version){
        String signature = "";
        String base_string = initBaseString(consumerKey, version);
        signature = new BASE64Encoder().encode(encodeHmacSHA(base_string.getBytes(),UUID.randomUUID().toString().getBytes())) ;
        return signature;
    }
    /**  
     * 使用HmacSHA算法计算  
     * @return 加密后的签名（长度为16的字节数组）  
     */  
    public static byte[] encodeHmacSHA(byte[] data, byte[] key){
        String method = "HmacSHA1";
        Key k = new SecretKeySpec(key,method);
        Mac mac = null;
        try {
            mac = Mac.getInstance(method);
            mac.init(k);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

        return mac.doFinal(data);   
    }
    /*
     * 获取随机字符
     */
    public static String getRandomString(int length){
        StringBuffer buffer=new StringBuffer("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");
        StringBuffer sb=new StringBuffer();
        Random r=new Random();
        int range=buffer.length();
        for(int i=0;i<length;i++){
            sb.append(buffer.charAt(r.nextInt(range)));
        }
        return sb.toString();
    }

    public static String encoderStr(String str){
        String result = "";
        try {
            result = URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 
     * 测试
     * @param args 
     * @see
     */
    public static void main(String[] args) {
        //		System.out.println(getRandomString(40));

        String user_iphone = "123123";
        String hmacMethod = "HMAC-SHA1";  
        String version = "1.0";
        String url = "";
        try {
            for (int i = 0; i < 100; i++) {
                url = getToken(user_iphone,version);
            }

        } catch (InvalidKeyException e1) {
            e1.printStackTrace();
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }

    }
}

