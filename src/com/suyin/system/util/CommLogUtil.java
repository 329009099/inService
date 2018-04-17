/*
 * 文件名：CommLogUtil.java
 * 版权：Copyright by www.isure.net
 * 描述：
 * 修改人：windows7
 * 修改时间：2015-12-7
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.suyin.system.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.suyin.system.model.BaseExpLogModel;
import com.suyin.system.model.BaseLogModel;
import com.suyin.system.model.UserLog;


/**
 * 文件记录操作的各项功能p,uv
 * @author lz
 * @version 2015-12-7
 * @see CommLogUtil
 * @since
 */

public class CommLogUtil
{

    private final static Logger log=Logger.getLogger(CommLogUtil.class);


    /**
     * 
     * 保存操作记录
     * @param clicentType  1:活动ID
     * @param logType      2:用户Id
     * @param modelNumber  3:详情Id
     * @param modelName    4:客户端类型 0 微信 1 ios 2 andriod
     * @param userId       5:活动类型 0抽奖式 1人气式，2兑换式 3试用式
     * @see
     */
    public static void saveExpOptLog(String expId,String userId,String detaiId,String clicentType,String expType)
    {
        try
        { 

            BaseExpLogModel optLog=new BaseExpLogModel();
            optLog.setExpId(expId);
            optLog.setUserId(userId);     
            optLog.setDetaiId(detaiId);
            optLog.setClicentType(clicentType);           
            optLog.setExpType(expType);
            String fileUrl = SystemPropertiesHolder.get(ConstantLogUtil.LOG_EXP_FILE_URL);

            File file = new File(fileUrl);
            if (!file.exists())
            {
                file.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(new File(fileUrl+Tools.date2Str(new Date(), ConstantLogUtil.YEAR_TYPE) + ConstantLogUtil.FILE_SUFFIX), true);
            FileChannel fcout = out.getChannel();
            FileLock flout = null;
            while (true)
            {
                flout = fcout.tryLock();
                if (flout != null)
                {
                    break;
                }else{
                    Thread.sleep(10);
                }
            }
            PrintWriter pw = new PrintWriter(out);
            pw.println(optLog.toString());
            pw.flush();
            out.close();
            pw.close();

        }
        catch (Exception e)
        {
            log.error("记录NO团网活动操作日志异常:"+e.getMessage());
        }
    }
    /**
     * 
     * 保存操作记录
     * @param clicentType  1:客户端类型 0 微信 1 ios 2 andriod
     * @param logType      2:日志类型
     * @param modelNumber  3:模块编号
     * @param modelName    4:模块名称
     * @param userId       5:用户userId主键
     * @see
     */
    public static void saveOptLog(String clicentType,String logType,String modelNumber,String modelName,String userId)
    {
        try
        { 


            BaseLogModel optLog=new BaseLogModel();
            optLog.setClicentType(clicentType);
            optLog.setLogType(logType);
            optLog.setModelName(modelName);
            optLog.setModelNumber(modelNumber);
            optLog.setUserId(userId);     

            String fileUrl = SystemPropertiesHolder.get(ConstantLogUtil.LOG_FILE_URL);

            File file = new File(fileUrl);
            if (!file.exists())
            {
                file.mkdirs();
            }
            FileOutputStream out = new FileOutputStream(new File(fileUrl+Tools.date2Str(new Date(), ConstantLogUtil.YEAR_TYPE) + ConstantLogUtil.FILE_SUFFIX), true);
            FileChannel fcout = out.getChannel();
            FileLock flout = null;
            while (true)
            {
                flout = fcout.tryLock();
                if (flout != null)
                {
                    break;
                }else{
                    Thread.sleep(10);
                }
            }
            PrintWriter pw = new PrintWriter(out);
            pw.println(optLog.toString());
            pw.flush();
            out.close();
            pw.close();

        }
        catch (Exception e)
        {
            log.error("记录NO团网操作日志异常:"+e.getMessage());
        }
    }


    /**
     * 
     * 文件读取方式，
     * 备用方式
     * @return
     * @throws IOException 
     * @see
     */
    public static   List<BaseLogModel> readOptLog() throws IOException{

        String fileUrl = SystemPropertiesHolder.get(ConstantLogUtil.LOG_FILE_URL);
        //获取前一天的文件
        String fileName =Tools.getDate(new Date(), "yyyyMMdd", -1);
        File file = new File(fileUrl+fileName+".txt");

        if (!file.exists())
        {
            log.error(fileName+".txt////"+"日志文件读取失败");
            return null;
        }

        String str="";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
        List<BaseLogModel> baseDbLog=new LinkedList<BaseLogModel>();
        List<UserLog> userDbLog=new  LinkedList<UserLog>();
        while ((str = br.readLine()) != null && !str.equals(""))
        {
            String[]  strs = str.split(",");
            BaseLogModel baseLog=new BaseLogModel();
            //                        baseLog.setUserId(strs[0]);//userId
            baseLog.setModelName(strs[1]);//模块名称           
            baseLog.setModelNumber(strs[2]);//模块编号
            baseLog.setLogType(strs[3]);//记录日志的类型
            baseLog.setClicentType(strs[4]);
            //            baseLog.setCreateTime(strs[5]);//日志记录时间

            if(!baseDbLog.contains(baseLog)){

                baseLog.setPv(1);
                baseDbLog.add(baseLog);
            }else{

                baseDbLog.get(baseDbLog.indexOf(baseLog)).setPv(baseDbLog.get(baseDbLog.indexOf(baseLog)).getPv()+1);

            }
            UserLog user=new UserLog();
            user.setlogType(strs[3]);
            user.setUserId(strs[0]);
            if(!userDbLog.contains(user)){

                userDbLog.add(user);
            }
            System.out.println(userDbLog);

        }

        List<BaseLogModel>uLogs=new ArrayList<BaseLogModel>();
        for (UserLog u:userDbLog)
        {
            BaseLogModel log = new BaseLogModel();
            log.setLogType(u.getlogType());
            if (!uLogs.contains(log))
            {
                log.setUv(1);
                uLogs.add(log);
            }
            else
            {
                uLogs.get(uLogs.indexOf(log)).setUv(uLogs.get(uLogs.indexOf(log)).getUv() + 1);
            }

        }
        for (int i = 0; i < baseDbLog.size(); i++ )
        {
            for (int j = 0; j < uLogs.size(); j++ )
            {
                if (baseDbLog.get(i).getLogType() ==uLogs.get(j).getLogType())
                {
                    baseDbLog.get(i).setUv(uLogs.get(j).getUv());
                }
            }
        }

        return baseDbLog;
    }
    /**
     * 
     * 保存活动日志记录
     * @param params 
     * @see
     */
    public static void expOptLog(Map<String,Object>params){

        if(null != params.get("userId"))
        {
            CommLogUtil.saveExpOptLog(params.get("expId").toString(), params.get("userId").toString(), params.get("detaiId").toString(), params.get("clicentType").toString(), params.get("expType").toString());
        }
    }

    /**
     * 
     * 保存活动日志记录
     * @param params 
     * @see
     */
    public static void expZhuanOptLog(Map<String,Object>params){

        if(null != params.get("userId"))
        {
            String type="";
            if("0".equals(params.get("expType").toString())){
                //帮我赚
                type="8";
            }else{
                
                //轻松赚
                type="9";
            }
            CommLogUtil.saveExpOptLog(params.get("expId").toString(), params.get("userId").toString(), params.get("detaiId").toString(), params.get("clicentType").toString(), type);
        }
    }
    /**
     * 
     * 操作活动详情日志记录
     * 
     * @param result
     * @param expType 
     * @see
     */
    public  static void CommLogTaskDetail(ModelMap result,String expType){

        if("0".equals(expType)){

            //帮我赚
            if(null != result.get("userid"))
            {
                CommLogUtil.saveOptLog(result.get("regtype").toString(), ConstantLogUtil.QING_SONG_DETIAL, ConstantLogUtil.QING_SONG_DETIAL_NUMBER, ConstantLogUtil.QING_SONG_DETIAL_NAME, result.get("userid").toString());
            }
        }else if("1".equals(expType)){
            //轻松赚
            if(null != result.get("userid"))
            {
                CommLogUtil.saveOptLog(result.get("regtype").toString(), ConstantLogUtil.QUAN_MIN_DETIAL, ConstantLogUtil.QUAN_MIN_DETIAL_NUMBER, ConstantLogUtil.QUAN_MIN_DETIAL_NAME, result.get("userid").toString());
            }

        }

    }
    /**
     * 
     * 赚金币列表的操作日志记录
     * 
     * @param result
     * @param expType 
     * @see
     */
    public  static  void CommLogTaskList(ModelMap result,String expType){

        if("0".equals(expType)){

            //帮我赚
            if(null != result.get("userid"))
            {
                CommLogUtil.saveOptLog(result.get("regtype").toString(), ConstantLogUtil.QING_SONG_LIST, ConstantLogUtil.QING_SONG_LIST_NUMBER, ConstantLogUtil.QING_SONG_LIST_NAME, result.get("userid").toString());
            }
        }else{
            //轻松赚
            if(null != result.get("userid"))
            {
                CommLogUtil.saveOptLog(result.get("regtype").toString(), ConstantLogUtil.QUAN_MIN_LIST, ConstantLogUtil.QUAN_MIN_LIST_NUMHER, ConstantLogUtil.QUAN_MIN_LIST_NAME, result.get("userid").toString());
            }
        }

    }

    /**
     * 
     * 操作日志记录
     * 
     * @param result
     * @param expType 
     * @see
     */
    public  static void NoMsmfCommLogDetial(ModelMap result,String expType){

        if("0".equals(expType)){

            //抽奖式
            if(null != result.get("userid"))
            {
                CommLogUtil.saveOptLog(result.get("regtype").toString(), ConstantLogUtil.CHOU_DETAIL, ConstantLogUtil.CHOU_DETAIL_NUMBER, ConstantLogUtil.CHOU_DETAIL_NAME, result.get("userid").toString());
            }
        }else if("1".equals(expType)){
            //人气式
            if(null != result.get("userid"))
            {
                CommLogUtil.saveOptLog(result.get("regtype").toString(), ConstantLogUtil.REN_QI_DETIAL, ConstantLogUtil.REN_QI_DETIAL_NUMBER, ConstantLogUtil.REN_QI_DETIAL_NAME, result.get("userid").toString());
            }
        }else if("2".equals(expType)){
            //兑换式
            if(null != result.get("userid"))
            {
                CommLogUtil.saveOptLog(result.get("regtype").toString(), ConstantLogUtil.DUI_DETAIL, ConstantLogUtil.DUI_DETAIL_NUMBER, ConstantLogUtil.DUI_DETAIL_NAME, result.get("userid").toString());
            }
        }else if("3".equals(expType)){
            //试用式
            if(null != result.get("userid"))
            {
                CommLogUtil.saveOptLog(result.get("regtype").toString(), ConstantLogUtil.SHI_DETIAL, ConstantLogUtil.SHI_DETIAL_NUMBER, ConstantLogUtil.SHI_DETIAL_NAME, result.get("userid").toString());
            }
        }

    }
    /**
     * 
     * 免费的四个活动列表操作日志记录
     * 
     * @param result
     * @param expType 
     * @see
     */
    public  static void NoMsmfCommLogList(ModelMap result,String expType){

        if("0".equals(expType)){

            //抽奖式
            if(null != result.get("userid"))
            {
                CommLogUtil.saveOptLog(result.get("regtype").toString(), ConstantLogUtil.CHOU_LIST, ConstantLogUtil.CHOU_LIST_NUMBER, ConstantLogUtil.CHOU_LIST_NAME, result.get("userid").toString());
            }
        }else if("1".equals(expType)){
            //人气式
            if(null != result.get("userid"))
            {
                CommLogUtil.saveOptLog(result.get("regtype").toString(), ConstantLogUtil.REN_QI_LIST, ConstantLogUtil.REN_QI_LIST_NUMBER, ConstantLogUtil.REN_QI_LIST_NAME, result.get("userid").toString());
            }
        }else if("2".equals(expType)){
            //兑换式
            if(null != result.get("userid"))
            {
                CommLogUtil.saveOptLog(result.get("regtype").toString(), ConstantLogUtil.DUI_LIST, ConstantLogUtil.DUI_LIST_NUMBER, ConstantLogUtil.DUI_LIST_NAME, result.get("userid").toString());
            }
        }else if("3".equals(expType)){
            //试用式
            if(null != result.get("userid"))
            {
                CommLogUtil.saveOptLog(result.get("regtype").toString(), ConstantLogUtil.SHI_LIST, ConstantLogUtil.SHI_LIST_NUMBER, ConstantLogUtil.SHI_LIST_NAME, result.get("userid").toString());
            }
        }

    }
    /**
     * 
     * 测试
     * @param args 
     * @throws IOException 
     * @see
     */
    public static void main(String[] args) throws IOException
    {


        CommLogUtil.readOptLog();
        for (int i = 0; i < 5; i++ )
        {

            CommLogUtil.saveOptLog("0", "1", "CESHI", "12", "646234");
        }

    }




}
