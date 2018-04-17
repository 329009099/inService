/*
 * 文件名：ExpInvolvedController.java
 * 版权：Copyright by www.isure.net
 * 描述：
 * 修改人：windows7
 * 修改时间：2015-9-12
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.suyin.exp.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import test.ExpControllerTest;

import com.suyin.common.Utils;
import com.suyin.exp.model.ExpDetail;
import com.suyin.exp.model.Experience;
import com.suyin.exp.service.ExpDetailService;
import com.suyin.exp.service.ExpInvolvedService;
import com.suyin.exp.service.ExperienceService;
import com.suyin.exp.service.impl.ExpInvolvedServiceImpl;
import com.suyin.exporder.model.ExpOrder;
import com.suyin.exporder.model.ExpVoucher;
import com.suyin.exporder.service.ExpOrderService;
import com.suyin.exporder.service.ExpVoucherService;
import com.suyin.expzhuan.model.ExperienceTaskOrder;
import com.suyin.nouser.model.Nouser;
import com.suyin.nouser.service.NoUserPrototypeService;
import com.suyin.nouser.service.NouserService;
import com.suyin.system.util.SendMessage;
import com.suyin.system.util.Tools;

/**
 * 该类只负责活动参与 
 * 活动类型expType 为0 抽奖式,1人气式,2兑换式，3试用式
 * @author lz
 * @version 2015-9-12
 * @see ExpInvolvedController
 * @since
 */
@Controller
@RequestMapping("/inVolve")
public class ExpInvolvedController
{
    @Autowired
    private ExpInvolvedService expInvolvedService;//参与信息
    @Autowired
    private NoUserPrototypeService noUserPrototypeService;

    /**
     * 
     * 参与活动
     * @param expType detailId userId 
     * @param expId 
     * @return 
     * @see
     */
    @RequestMapping(value="/inVolvedExp")
    public @ResponseBody ModelMap  inVolvedExp(Experience exp,HttpServletRequest request){
        ModelMap result=new ModelMap();
        Map<String, Object> condition=new  HashMap<String, Object>();
        String detailId=request.getParameter("detailId");
        String userId=request.getParameter("userId"); 
        String memberId=request.getParameter("memberId"); 
        String regtype=request.getParameter("regtype");
        condition.put("detailId", detailId);
        condition.put("userId", userId);
        condition.put("memberId", memberId);
        condition.put("regtype", regtype);
        int count = noUserPrototypeService.queryUserPrototypeAll(userId);
        if(count==0) {

            result.put("message", "Notperfect"); //用户资料没完成
            return result;
        }else{

            ExperienceTaskOrder order=new ExperienceTaskOrder();
            order.setUserId(Integer.parseInt(userId));
            order.setExpId(exp.getExpId());
            int param=expInvolvedService.isQualified(order);

            if(0==param){

                result.put("message", "NotInfoExp"); //资料匹配失败没有权限参加
                return result;
            }else{

                if(0==exp.getExpType()){       //抽奖式
                    synchronized (ExpInvolvedServiceImpl.class)
                    {
                        result.put("message",expInvolvedService.inVolvePrize(exp,condition));
                    }
                }else if(1==exp.getExpType()){ //人气式
                    synchronized (ExpInvolvedServiceImpl.class)
                    {
                        result.put("message",expInvolvedService.inVolvePopularity(exp,condition));
                    }
                }else if(2==exp.getExpType()){ //兑换式
                    synchronized (ExpInvolvedServiceImpl.class)
                    {
                        result.put("message",expInvolvedService.inVolveExchange(exp,condition));
                    }
                }else if(3==exp.getExpType()){ //试用式
                    synchronized (ExpInvolvedServiceImpl.class)
                    {
                        result.put("message",expInvolvedService.inVolveTry(exp,condition));
                    }
                }
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
    @RequestMapping(value="/inVolveTryUserStauts")
    public @ResponseBody ModelMap inVolveTryUserStauts(Experience exp,HttpServletRequest request,HttpServletResponse response){
        ModelMap result=new ModelMap();
        Map<String, Object> condition=new  HashMap<String, Object>();
        String detailId=request.getParameter("detailId");
        String userId=request.getParameter("userId"); 
        String memberId=request.getParameter("memberId"); 
        condition.put("detailId", detailId);
        condition.put("userId", userId);
        condition.put("memberId", memberId);
        int count = noUserPrototypeService.queryUserPrototypeAll(userId);
        if(count==0) {
            result.put("message", "Notperfect"); //用户资料没完成
            return result;
        }

        ExperienceTaskOrder order=new ExperienceTaskOrder();
        order.setUserId(Integer.parseInt(userId));
        order.setExpId(exp.getExpId());
        int param=expInvolvedService.isQualified(order);
        if(0==param){

            result.put("message", "NotInfoExp"); //资料匹配失败没有权限参加
            return result;
        }

        result.put("message",expInvolvedService.inVolveExpUserStauts(exp,condition));
        return result;
    }

    /**
     * 试用式问题采集
     * 成功后转向 inVolvedExp 入订单
     * @return 
     * @see
     */
    @RequestMapping(value="/inVolveTry")
    public @ResponseBody ModelMap inVolveTry(HttpServletRequest request,HttpServletResponse response){
        ModelMap result=new ModelMap();
        Map<String, Object> condition=new  HashMap<String, Object>();
        String detailId=request.getParameter("detailId");
        String userId=request.getParameter("userId"); 
        String memberId=request.getParameter("memberId"); 
        condition.put("detailId", detailId);
        condition.put("userId", userId);
        condition.put("memberId", memberId);

        return result;
    }



    /**
     * 测试
     * @param args 
     * @see
     */
    public static void main(String[] args)
    {        


        System.out.println(Tools.getValidTime(5));
    }

}
