/*
 * 文件名：Tools.java
 * 版权：Copyright by www.suyinchina.com
 * 描述：
 * 修改人：WS
 * 修改时间：2015年3月25日
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.suyin.system.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletResponse;

public class Tools
{
    /**
     * 检测字符串是否不为空(null,"","null")
     * @param s
     * @return 不为空则返回true，否则返回false
     */
    public static boolean notEmpty(String s)
    {
        return s != null && !"".equals(s) && !"null".equals(s);
    }

    /**
     * 检测字符串是否为空(null,"","null")
     * @param s
     * @return 为空则返回true，不否则返回false
     */
    public static boolean isEmpty(String s)
    {
        return s == null || "".equals(s) || "null".equals(s);
    }

    /**
     * 字符串转换为字符串数组
     * @param str 字符串
     * @param splitRegex 分隔符
     * @return
     */
    public static String[] str2StrArray(String str, String splitRegex)
    {
        if (isEmpty(str))
        {
            return null;
        }
        return str.split(splitRegex);
    }

    /**
     * 用默认的分隔符(,)将字符串转换为字符串数组
     * @param str   字符串
     * @return
     */
    public static String[] str2StrArray(String str)
    {
        return str2StrArray(str, ",\\s*");
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
     * @param date
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String date2Str(Date date)
    {
        return date2Str(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
     * @param date
     * @return
     */
    public static Date str2Date(String date)
    {
        if (notEmpty(date))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try
            {
                return sdf.parse(date);
            }
            catch (ParseException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return new Date();
        }
        else
        {
            return null;
        }
    }
    public static String getDate(Date date, String format, Integer d)
    {
 
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, d);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        String dateString = formatter.format(date);
        return dateString;
    }
    /**
     * 按照参数format的格式，日期转字符串
     * @param date
     * @param format
     * @return
     */
    public static String date2Str(Date date, String format)
    {
        if (date != null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(date);
        }
        else
        {
            return "";
        }
    }

    /**
     * 字符串为null的返回""
     * @param date
     * @param format
     * @return
     */
    public static String nullToEmpty(String str)
    {
        if(isEmpty(str)){
            return "";
        }
        return str;
    }
    public static String getTime(String dateTime){
        String returnTime=dateTime.substring(0,10);
        String nowDate=new SimpleDateFormat("yyyy-MM-dd 00:00:00").format(new Date());
        long leftTime=(Tools.str2Date(dateTime).getTime())-Tools.str2Date(nowDate).getTime();
        if(Math.floor(leftTime/1000/60/60)<=24){
            int h=Integer.parseInt(dateTime.substring(11,13));
            if(h==0){
                returnTime="今天23:59"; 
            }
            else{
                returnTime="今天"+dateTime.substring(11,16);
            }
        }
        return returnTime;
    }

    /*时间比大小  -1为过期*/
    public static int timeCompare(String t1){
        int result = 0 ;
        if(null != t1){
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar c1=Calendar.getInstance();
            Calendar c2=Calendar.getInstance();
            try {
                c1.setTime(formatter.parse(t1));
                c2.setTime(formatter.parse(getCurrentTime()));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            result=c1.compareTo(c2);
        }
        return result;
    }

    public static String getCurrentTime(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 专用手机号码验证机制
     * Description: <br>
     * 
     * @param mobiles
     * @return 
     * @see
     */
    public static boolean isMobileNO(String mobiles){

        Pattern p = Pattern.compile("^((13[0-9])|(15[0-9])|(18[0-9])|(14[7])|(17[7]))\\d{8}$");

        Matcher m = p.matcher(mobiles);

        return m.matches();

    }
    /**
     * 获取配置文件key value
     * @param key
     * @param path
     * @return 
     * @see
     */
    public static String getValByKey(String key, String path)
    {
        String val = "";
        Properties p = new Properties();
        //
        p = new PropertiesUtils().getProperties(path);
        val = p.getProperty(key);
        return val;
    }

    /**
     * 生成6位随机数验证码 
     * @param integer
     * @return 
     * @see
     */
    public static String createRandom(Integer integer)
    {
        StringBuffer buff = new StringBuffer();
        for(int i=0;i<integer;i++){
            int num = (int)(Math.random()*10);
            buff.append(num);
        }
        String randomNum = buff.toString();
        return randomNum;
    }

    /**
     * 时间差比对 
     * @param failureTime
     * @param date
     * @return 
     * @see
     */
    public static long queryMarginTime(Date failureTime, Date date)
    {
        long margin=0;
        Calendar oldTime = Calendar.getInstance();
        Calendar nowTime = Calendar.getInstance();
        oldTime.setTime(failureTime);
        nowTime.setTime(date);
        long old = oldTime.getTimeInMillis();
        long now = nowTime.getTimeInMillis();
        margin = now-old;
        return margin;
    }
    /**
     * 增加对json格式的支持
     * 
     * @param data
     */
    public static void response(HttpServletResponse response, String data)
    {  PrintWriter out = null;
    try
    {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //        response.setContentType("text/json;charset=UTF-8");
        out= response.getWriter();

        out.write(data);

    }
    catch (IOException e)
    {
        e.printStackTrace();
    }finally{

        out.flush();
        out.close();
        out=null;
    }
    }
    
    /**
     * 
     * 几天后失效 
     * @param addDay
     * @return 
     * @see
     */
    public static String getValidTime(int addDay){

        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date); 
        calendar.add(calendar.DATE, addDay);
        date=calendar.getTime();

        String str=Tools.date2Str(date, "yyyy-MM-dd");
        return str;
    }

}
