/*
 * 文件名：ExpInvolvedServiceImpl.java
 * 版权：Copyright by www.isure.net
 * 描述：
 * 修改人：windows7
 * 修改时间：2015-9-15
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.suyin.exp.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import test.ExpControllerTest;

import com.suyin.common.mapper.CoinLogMapper;
import com.suyin.common.mapper.MessageMapper;
import com.suyin.common.model.CoinLog;
import com.suyin.exp.model.Experience;
import com.suyin.exp.service.ExpDetailService;
import com.suyin.exp.service.ExpInvolvedService;
import com.suyin.exp.service.ExperienceService;
import com.suyin.exporder.model.ExpOrder;
import com.suyin.exporder.model.ExpVoucher;
import com.suyin.exporder.service.ExpOrderService;
import com.suyin.exporder.service.ExpVoucherService;
import com.suyin.expshare.model.ExpShare;
import com.suyin.expshare.service.ExpShareService;
import com.suyin.expzhuan.mapper.ExperienceTaskMapper;
import com.suyin.expzhuan.model.ExperienceTaskOrder;
import com.suyin.nouser.model.Nouser;
import com.suyin.nouser.service.NouserService;
import com.suyin.system.util.SendMessage;
import com.suyin.system.util.Tools;
import com.suyin.system.util.VoucherUtil;

/**
 * 活动参与 
 * 活动类型 expType 为0 抽奖式,1人气式,2兑换式，3试用式
 * 展示方式 show_type 0:普通类型1:拼图:2刮刮卡3抽签
 * @author lz
 * @version 2015-9-12
 * @see ExpInvolvedController
 * @since
 */
@Transactional
@Service("ExpInvolvedService")
public class ExpInvolvedServiceImpl implements ExpInvolvedService
{
    @Autowired
    private ExperienceService experienceService;//活动相关信息
    @Autowired
    private ExpDetailService expDetailService; //活动详情
    @Autowired
    private ExpOrderService expOrderService;//订单处理
    @Autowired
    private ExpVoucherService expVoucherService; //活动券处理
    @Autowired
    private NouserService nouserService; //用户信息
    @Autowired
    private ExpShareService expShareService; //用户分享记录
    @Autowired
    private MessageMapper messageMapper; //消息记录
    @Autowired
    private ExperienceTaskMapper experienceTaskMapper;//动态属性匹配是否符合
    @Autowired
    private CoinLogMapper coinLogMapper;
    /**
     * 效验匹配动态属性是否符合条件
     * @param order
     * @return 
     * @see
     */
    @Override
    public int isQualified(ExperienceTaskOrder order){

        return experienceTaskMapper.isQualified(order);
    }


    /**
     * 抽奖式 
     * @param exp
     * @param condition
     * @return 
     * @see
     */
    @Override
    public String inVolvePrize(Experience exp, Map<String, Object> condition)
    {
        String result="";
        Map<String, Object> resultExpInfo=new HashMap<String, Object>();
        //查询当前用户是否参与本活动，或是否中奖等操作       
        resultExpInfo=this.expDetailService.findIsUserExpStauts(condition);;
        if(null!=resultExpInfo){
            //已经参与过活动
            result="invalidDetailInfo";
        }else{

            //该用户在当前条件下查询，订单中不存在有关数据，允许下步操作。
            //查询当前时间段内的产品数量是否满足
            Map<String,Object>  detailInfo=expDetailService.findExpIsProNum(condition);
            if(null!=detailInfo){
                int proNum=Integer.parseInt(detailInfo.get("pro_num").toString());
                int expNum=Integer.parseInt(detailInfo.get("exp_num").toString());

                if(expNum>=proNum){
                    //产品数量不足
                    result="invlidProNum";
                }else{

                    //查询活动概率
                    exp=experienceService.findExpById(exp);
                    if(null!=exp){
                        //活动是否开始
                        if(1==exp.getIsBegin()){

                            //个人中心我的消息记录
                            Map<String, String> message=new HashMap<String, String>();
                            message.put("userId", condition.get("userId").toString());
                            message.put("expId", exp.getExpId().toString());
                            message.put("content", "您参加了"+exp.getTitle());
                            this.messageMapper.addMessage(message);
                            //计算是否在随机数内
                            result=RanDomPrizeExpInfo(exp,condition,detailInfo);

                        }else{

                            //活动没有设置开始
                            result="started";
                        }
                    }else{

                        //活动概率等信息查询有误
                        result="invalidExpInfo";
                    }

                }

            }else{
                //当前时间内无活动，活动已结束。
                result="invalidTimeExp";
            }
        }

        return result;
    }


    /**
     * 抽奖式__相关逻辑数据添加
     * @param exp
     * @return 
     * @see
     */
    private String RanDomPrizeExpInfo(Experience exp,Map<String, Object> map,Map<String, Object> detailInfo){
        String result="";
        Random random = new Random();
        ExpOrder ordreModel=new ExpOrder();
        ExpVoucher voucherModel=new ExpVoucher();
        //装载订单记录信息
        ordreModel.setDetailId(Integer.parseInt(map.get("detailId").toString()));
        ordreModel.setExpId(exp.getExpId());
        ordreModel.setExpType(exp.getExpType());
        ordreModel.setMemberId(Integer.parseInt(map.get("memberId").toString()));
        ordreModel.setUserId(Integer.parseInt(map.get("userId").toString()));
        ordreModel.setRegType(Integer.parseInt(map.get("regtype").toString()));
        if(random.nextInt(100)<exp.getProbability()){
            //生成券      
            String vouCode=VoucherUtil.getRandomString(12);           
            //保存订单信息
            ordreModel.setStatus(1);
            ordreModel.setVoucherStatus(1);
            expOrderService.addExpOrder(ordreModel);  
            //保存券的信息
            voucherModel.setVouCode(vouCode);
            voucherModel.setOrderId(ordreModel.getOrderId());
            voucherModel.setUserId(ordreModel.getUserId());
            if(null!=detailInfo.get("add_day").toString() && !"0".equals(detailInfo.get("add_day").toString())){
                int addDay=Integer.parseInt(detailInfo.get("add_day").toString());
                String validTime=Tools.getValidTime(addDay);
                //几天后失效日期
                voucherModel.setValidity(validTime);
            }else {
                //自然失效日期
                voucherModel.setValidity(detailInfo.get("validity").toString());
            }
            //查询用户信息
            Nouser nouser=nouserService.findNouserWhereByUserId(ordreModel.getUserId());
            //短信发送 1手机号码，2券号 ，3产品名称 
            SendMessage.orderVoucherMessage(nouser.getUserPhone(),vouCode+"",detailInfo.get("pro_name").toString());
            expVoucherService.addExpVoucher(voucherModel);
            //更新当前时间段内的exp_num信息
            Map<String, Object>timeExpInfo=new HashMap<String, Object>();
            timeExpInfo.put("expTimeId",detailInfo.get("exp_time_id").toString());
            expDetailService.updateTimeExpNum(timeExpInfo);
            //更新活动详情参与人数exp_num数量
            expDetailService.updateExpDetailExpNum(ordreModel.getDetailId().toString());
            result="yprize";
        }else{

            //保存订单信息
            ordreModel.setStatus(2);
            ordreModel.setVoucherStatus(0);
            expOrderService.addExpOrder(ordreModel); 
            //更新活动详情参与人数exp_num数量
            expDetailService.updateExpDetailExpNum(ordreModel.getDetailId().toString());
            result="nprize";
        }

        return result;
    }



    /**
     * 金币兑换式 
     * @param exp
     * @param condition
     * @return 
     * @see
     */
    @Override
    public String inVolveExchange(Experience exp, Map<String, Object> condition)
    {
        String result="";
        Map<String, Object> resultExpInfo=new HashMap<String, Object>();
        //查询当前用户是否参与本活动，或是否中奖等操作       
        resultExpInfo=this.expDetailService.findIsUserExpStauts(condition);;
        if(null!=resultExpInfo){
            //已经参与过活动
            result="invalidDetailInfo";
        }else{
            //该用户在当前条件下查询，订单中不存在有关数据，允许下步操作。
            //查询当前时间段内的产品数量是否满足
            Map<String,Object>  detailInfo=expDetailService.findExpIsProNum(condition);
            if(null!=detailInfo){
                int proNum=Integer.parseInt(detailInfo.get("pro_num").toString());
                int expNum=Integer.parseInt(detailInfo.get("exp_num").toString());

                if(expNum>=proNum){
                    //产品数量不足
                    result="invlidProNum";
                }else{

                    //查询活动信息
                    exp=experienceService.findExpById(exp);
                    if(null!=exp){
                        if(1==exp.getIsBegin()){
                            //个人中心我的消息记录
                            Map<String, String> message=new HashMap<String, String>();
                            message.put("userId", condition.get("userId").toString());
                            message.put("expId", exp.getExpId().toString());
                            message.put("content", "您参加了"+exp.getTitle());
                            this.messageMapper.addMessage(message);
                            //计算积分活动参与
                            result=IntegralExchangeExpInfo(exp,condition,detailInfo);
                        }else{

                            //活动没有设置开始
                            result="started";
                        }
                    }else{

                        //活动相关信息查询有误
                        result="invalidExpInfo";
                    }

                }
            }else{

                //当前时间内无活动，活动已结束。
                result="invalidTimeExp";

            }
        }

        return result;
    }

    /**
     * 金币兑换式_相关逻辑数据添加
     * @param exp
     * @return 
     * @see
     */
    private String IntegralExchangeExpInfo(Experience exp,Map<String, Object> map,Map<String, Object> detailInfo){
        String result="";

        ExpOrder ordreModel=new ExpOrder();
        ExpVoucher voucherModel=new ExpVoucher();
        //装载订单记录信息
        ordreModel.setDetailId(Integer.parseInt(map.get("detailId").toString()));
        ordreModel.setExpId(exp.getExpId());
        ordreModel.setExpType(exp.getExpType());
        ordreModel.setMemberId(Integer.parseInt(map.get("memberId").toString()));
        ordreModel.setUserId(Integer.parseInt(map.get("userId").toString()));
        ordreModel.setRegType(Integer.parseInt(map.get("regtype").toString()));
        
        //查询用户金币相关信息
        Map<String,Object>userInfo=nouserService.findNouserMoneyInfo(map);
        if(null!=userInfo){
            int userIntegral=Integer.parseInt(userInfo.get("gold_coin").toString());//获取金币数量
            int expIntegral=Integer.parseInt(detailInfo.get("integral").toString());//获取活动所需参与金币数

            if(expIntegral<=userIntegral){
                //计算用户余下金币
                int updateIntegral=userIntegral-expIntegral;
                //生成券      
                String vouCode=ExpControllerTest.getRandomString(12);
                //保存订单信息
                ordreModel.setStatus(1);
                ordreModel.setVoucherStatus(1);
                expOrderService.addExpOrder(ordreModel);  
                //保存券的信息
                voucherModel.setVouCode(vouCode);
                voucherModel.setOrderId(ordreModel.getOrderId());
                voucherModel.setUserId(ordreModel.getUserId());
                if(null!=detailInfo.get("add_day").toString() && !"0".equals(detailInfo.get("add_day").toString())){
                    int addDay=Integer.parseInt(detailInfo.get("add_day").toString());
                    String validTime=Tools.getValidTime(addDay);
                    //几天后失效日期
                    voucherModel.setValidity(validTime);
                }else {
                    //自然失效日期
                    voucherModel.setValidity(detailInfo.get("validity").toString());
                }

                //短信发送 1手机号码，2券号 ，3产品名称 
                SendMessage.orderVoucherMessage(userInfo.get("user_phone").toString(),vouCode+"",detailInfo.get("pro_name").toString());
                expVoucherService.addExpVoucher(voucherModel);
                //更新当前时间段内的exp_num信息
                Map<String, Object>timeExpInfo=new HashMap<String, Object>();
                timeExpInfo.put("expTimeId",detailInfo.get("exp_time_id").toString());
                expDetailService.updateTimeExpNum(timeExpInfo);
                //更新活动详情参与人数exp_num数量
                expDetailService.updateExpDetailExpNum(ordreModel.getDetailId().toString());
                //用户现在的金币数
                userInfo.put("Integral", updateIntegral);
                //修改用户金币数
                nouserService.updateNouserMoneyInfo(userInfo);
                
                CoinLog log = new CoinLog();
                log.setUserId(ordreModel.getUserId());
                log.setContent("提取金币");
                log.setContent("您参加了"+exp.getTitle()+"_金币兑");
                log.setDirection("2");
                log.setGoldCoin(expIntegral);
                log.setStatus(0);
                this.coinLogMapper.addCoinLog(log);
                result="yprize";
            }else{

                //更新活动详情参与人数exp_num数量
                expDetailService.updateExpDetailExpNum(ordreModel.getDetailId().toString());
                //金币不足 
                result="invalidIntegral";
            }

        }else{

            //用户信息有误 
            result="invalidUser";
        }
        return result;
    }


    /**
     * 人气式_ 
     * @param exp
     * @param condition
     * @return 
     * @see
     */
    @Override
    public String inVolvePopularity(Experience exp, Map<String, Object> condition)
    {
        String result="";
        Map<String, Object> resultExpInfo=new HashMap<String, Object>();
        //查询当前用户是否参与本活动，或是否中奖等操作       
        resultExpInfo=this.expDetailService.findIsUserExpStauts(condition);;
        if(null!=resultExpInfo){
            //已经参与过活动 //提示查看记录
            result="invalidDetailInfo";
        }else{

            Map<String,Object>  detailInfo=expDetailService.findExpIsProNum(condition);
            if(null!=detailInfo){ 

                //查询活动相关
                exp=experienceService.findExpById(exp);
                if(null!=exp){
                    //查看活动是否开始
                    if(1==exp.getIsBegin()){
                        //个人中心我的消息记录
                        Map<String, String> message=new HashMap<String, String>();
                        message.put("userId", condition.get("userId").toString());
                        message.put("expId", exp.getExpId().toString());
                        message.put("content", "您参加了【"+exp.getTitle()+"】活动");
                        this.messageMapper.addMessage(message);

                        //添加订单 
                        result=PopularityExpInfo(exp, condition, detailInfo);
                        //添加首次分享记录
                    }else{

                        //活动没有设置开始
                        result="started";
                    }
                }else{

                    //活动相关信息查询有误
                    result="invalidExpInfo";
                }

            }else{
                //当前时间内无活动，活动已结束。
                result="invalidTimeExp";
            }
        }
        return result;
    }

    /**
     * 人气式数据_相关逻辑数据添加
     * @param exp
     * @return 
     * @see
     */
    private String PopularityExpInfo(Experience exp,Map<String, Object> map,Map<String, Object> detailInfo){
        String result="";
        ExpOrder ordreModel=new ExpOrder();
        ExpShare shareModel=new ExpShare();
        //装载订单记录信息
        ordreModel.setDetailId(Integer.parseInt(map.get("detailId").toString()));
        ordreModel.setExpId(exp.getExpId());
        ordreModel.setExpType(exp.getExpType());
        ordreModel.setMemberId(Integer.parseInt(map.get("memberId").toString()));
        ordreModel.setUserId(Integer.parseInt(map.get("userId").toString()));
        ordreModel.setRegType(Integer.parseInt(map.get("regtype").toString()));
        //查询用户相关相关信息
        Map<String,Object>userInfo=nouserService.findNouserMoneyInfo(map);

        if(null!=userInfo){
            ordreModel.setStatus(0);//申请中状态
            ordreModel.setVoucherStatus(0);//未发券状态
            //保存订单信息
            expOrderService.addExpOrder(ordreModel);

            //装载分享记录信息
            shareModel.setDetailId(ordreModel.getDetailId());
            shareModel.setExpType(ordreModel.getExpType());
            shareModel.setExpId(ordreModel.getExpId());
            shareModel.setUserId(ordreModel.getUserId());
            shareModel.setShareNum(1);//自己首次分享加的1次分享数 此分享数用作为最终发券的有效凭据
            shareModel.setOpenId(userInfo.get("openid").toString());//自己首次分享 全端此属性 允许不存在
            shareModel.setExpTimeId(detailInfo.get("exp_time_id").toString());
            //更新当前时间段内的exp_num信息
            Map<String, Object>timeExpInfo=new HashMap<String, Object>();
            timeExpInfo.put("expTimeId",detailInfo.get("exp_time_id").toString());
            expDetailService.updateTimeExpNum(timeExpInfo);
            //记录用户分享信息
            expShareService.addExpShare(shareModel);
            //更新活动详情参与人数exp_num数量
            expDetailService.updateExpDetailExpNum(ordreModel.getDetailId().toString());
            result="yprize";
        }else{

            //查询用户信息有误
            result="invalidUser";
        }

        return result;
    }

    /**
     * 申请试用式
     * @param exp
     * @param condition
     * @return 
     * @see
     */
    @Override
    public String inVolveTry(Experience exp, Map<String, Object> condition)
    {
        // TODO Auto-generated method stub

        String result="";
        Map<String, Object> resultExpInfo=new HashMap<String, Object>();
        //查询当前用户是否参与本活动，或是否中奖等操作       
        resultExpInfo=this.expDetailService.findIsUserExpStauts(condition);;
        if(null!=resultExpInfo){
            //已经参与过活动
            result="invalidDetailInfo";
        }else{

            //该用户在当前条件下查询，订单中不存在有关数据，允许下步操作。
            Map<String,Object>  detailInfo=expDetailService.findExpIsProNum(condition);
            if(null!=detailInfo){
                //查询活动相关信息
                exp=experienceService.findExpById(exp);
                if(null!=exp){
                    //活动是否开始
                    if(1==exp.getIsBegin()){                       
                        //订单操作
                        result=inVolveTryExpInfo(exp, condition, detailInfo);
                    }else{

                        //活动没有设置开始
                        result="started";
                    }
                }else{

                    //活动信息查询有误
                    result="invalidExpInfo";
                }

            }else{
                //当前时间内无活动，活动已结束。
                result="invalidTimeExp";
            }
        }

        return result;
    }


    /**
     * 问卷页面弹出是查看当前用户是否已经参与过活动了 
     * @param exp
     * @param condition
     * @return 
     * @see
     */
    @Override
    public String inVolveExpUserStauts(Experience exp, Map<String, Object> condition)
    {
        // TODO Auto-generated method stub

        String result="";
        Map<String, Object> resultExpInfo=new HashMap<String, Object>();
        //查询当前用户是否参与本活动，或是否中奖等操作       
        resultExpInfo=this.expDetailService.findIsUserExpStauts(condition);;
        if(null!=resultExpInfo){
            //已经参与过活动
            result="invalidDetailInfo";
        }else{

            //该用户在当前条件下查询，订单中不存在有关数据，允许下步操作。
            Map<String,Object>  detailInfo=expDetailService.findExpIsProNum(condition);
            if(null!=detailInfo){
                //查询活动相关信息
                exp=experienceService.findExpById(exp);
                if(null!=exp){
                    //活动是否开始
                    if(1==exp.getIsBegin()){                       
                        //允许弹出问题采集页面 
                        result="yprize";
                    }else{

                        //活动没有设置开始
                        result="started";
                    }
                }else{

                    //活动信息查询有误
                    result="invalidExpInfo";
                }

            }else{
                //当前时间内无活动，活动已结束。
                result="invalidTimeExp";
            }
        }

        return result;
    }

    /**
     * 试用式数据_相关逻辑数据添加
     * @param exp
     * @return 
     * @see
     */
    public String inVolveTryExpInfo(Experience exp,Map<String, Object> map,Map<String, Object> detailInfo){
        String result="";      
        ExpOrder ordreModel=new ExpOrder();
        //装载订单记录信息
        ordreModel.setDetailId(Integer.parseInt(map.get("detailId").toString()));
        ordreModel.setExpId(exp.getExpId());
        ordreModel.setExpType(exp.getExpType());
        ordreModel.setMemberId(Integer.parseInt(map.get("memberId").toString()));
        ordreModel.setUserId(Integer.parseInt(map.get("userId").toString()));
        ordreModel.setRegType(Integer.parseInt(map.get("regtype").toString()));
        //保存订单信息
        ordreModel.setStatus(0);//申请中状态
        ordreModel.setVoucherStatus(0);//未发券状态
        expOrderService.addExpOrder(ordreModel);

        result="yprize";

        return result;
    }


}
