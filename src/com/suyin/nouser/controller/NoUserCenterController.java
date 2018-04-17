package com.suyin.nouser.controller;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suyin.common.model.CashLog;
import com.suyin.common.model.CoinLog;
import com.suyin.common.service.CoinLogService;
import com.suyin.common.service.MessageService;
import com.suyin.nouser.model.ExperienceOrder;
import com.suyin.nouser.model.ExperienceVouch;
import com.suyin.nouser.service.NoUserCenterService;
import com.suyin.system.model.Page;
import com.suyin.system.util.Md5Util;


/**
 * 个人中心里面有很多关于个人信息，券，钱，消息的查询，都在这个类里面
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/nouserCenter")
public class NoUserCenterController
{

    @Autowired
    private NoUserCenterService noUserCenterService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private CoinLogService coinLogService;

    //用户消息
    @RequestMapping("/findMessage")
    public @ResponseBody
    ModelMap findMessage(HttpServletRequest request)
    {
        ModelMap result = new ModelMap();
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userId", request.getParameter("userId"));
        if (StringUtils.isNotBlank(request.getParameter("page.currentPage")))
        {
            Page page = new Page();
            page.setCurrentPage(Integer.parseInt(request.getParameter("page.currentPage")));
            condition.put("page", page);
        }
        try
        {
            result.put("data", this.messageService.findMessageForUserByPage(condition));
            result.put("args", condition);
            result.put("message", "success");
        }
        catch (Exception e)
        {
            result.put("message", "error");
        }
        return result;
    }

    //用户删除消息
    @RequestMapping("/deleteMessage")
    public @ResponseBody
    ModelMap deleteMessage(@RequestParam("messageId")
    int messageId)
    {
        ModelMap result = new ModelMap();
        try
        {
            this.messageService.deleteMessage(messageId);
            result.put("message", "success");
        }
        catch (Exception e)
        {
            result.put("message", "error");
        }
        return result;
    }

    @RequestMapping("/userInfo")
    public @ResponseBody  Map getUserStaticInfo(@RequestParam("userId")   int userId)
    {
        return this.noUserCenterService.findUserInfo(userId);
    }

    //用户获取金币变化的log
    @RequestMapping("/findCoinLog")
    public @ResponseBody
    ModelMap findCoinLog(CoinLog log, HttpServletRequest request)
    {
        ModelMap result = new ModelMap();
        try
        {
            result.put("data", this.coinLogService.findCoinLogByUserByPage(log));
            result.put("args", log);
            result.put("message", "success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result.put("message", "error");
        }
        return result;
    }

    //用户获取金币变化的log
    @RequestMapping("/findCashLog")
    public @ResponseBody
    ModelMap findCashLog(CashLog log, HttpServletRequest request)
    {
        ModelMap result = new ModelMap();
        try
        {
            //result.put("coin", this.noUserCenterService.findUserInfo(log.getUserId()).get("gold_coin"));
            result.put("data", this.coinLogService.findCashLogByUserByPage(log));
            result.put("args", log);
            result.put("message", "success");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result.put("message", "error");
        }
        return result;
    }

    @RequestMapping("/signIn")
    public @ResponseBody
    ModelMap signIn(@RequestParam("userId")
    int userId)
    {
        ModelMap result = new ModelMap();
        result.put("message", this.noUserCenterService.signIn(userId));
        return result;
    }

    /**
     * 
     * 每次进入我的参与页面时触发该更新操作
     * 更新条件为 通过userId  更新对应订单
     * 以总资产，及活动结束时间为辅助条件进行更新      
     * @return 
     * @see
     */
    @RequestMapping(value = "/updateIsLoseTaskOrderInfo")
    public void updateIsLoseTaskOrderInfo(HttpServletRequest request)
    {
        String userId = request.getParameter("userId");
        this.noUserCenterService.updateIsLoseTaskOrderInfo(userId);

    }

    /**
     * 查询我的参与 所有活动
     * @param order
     * @return 
     * @see
     */
    @RequestMapping("/findInvolvement")
    public @ResponseBody
    ModelMap findInvolvement(ExperienceOrder order)
    {
        //更新齐心赚成功或失败状态
        this.noUserCenterService.updateIsLoseTaskOrderInfo(order.getUserId());
        ModelMap result = new ModelMap();
        result.put("data", this.noUserCenterService.findInvolvementByPage(order));
        result.put("args", order);
        result.put("message", "success");
        return result;
    }

    @RequestMapping("/findVouch")
    public @ResponseBody
    ModelMap findVouch(ExperienceVouch order)
    {
        ModelMap result = new ModelMap();
        result.put("data", this.noUserCenterService.findVouch(order));
        result.put("args", order);
        result.put("message", "success");
        return result;
    }

    /**
     * 提取金币
     * @param userId
     * @param coin
     * @return
     */
    @RequestMapping("/coinToCash")
    public @ResponseBody
    ModelMap coinToCash(@RequestParam("userId")
    int userId, @RequestParam("coin")
    int coin)
    {
        ModelMap result = new ModelMap();
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("userId", userId);
        condition.put("coin", coin);
//        if (coin < 20)
//        {
//            result.put("message", "lesscoin");
//            return result;
//        }
        try
        {
            result.put("message", this.noUserCenterService.coinToCash(condition));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result.put("message", "unkownE");
        }
        return result;
    }

    /***
     * 提现
     * @param userId
     * @param alipay
     * @param password
     * @param cash
     * @return
     */
    @RequestMapping("/cash")
    public @ResponseBody
    ModelMap cash(@RequestParam("userId")
    int userId, @RequestParam("alipay")
    String alipay, @RequestParam("password")
    String password, @RequestParam("name")
    String name, @RequestParam("cash")
    BigDecimal cash)
    {
        ModelMap result = new ModelMap();
        Map<String, Object> condition = new HashMap<String, Object>();
        condition.put("alipay", alipay);
        condition.put("password",Md5Util.toMD5(password));
        condition.put("cash", cash);
        condition.put("userId", userId);
        condition.put("content", "提现" + cash + "元审核中");
        condition.put("name", name);
        condition.put("log_status", "1");
        try
        {
            synchronized (NoUserCenterController.class)
            {

                result.put("message", this.noUserCenterService.cash(condition).getString("message"));
            }

        }
        catch (Exception e)
        {

            e.printStackTrace();
            result.put("message", "unkownE");
        }
        return result;

    }

    /**设置提现密码*/
    @RequestMapping(value = "/updatePF")
    public @ResponseBody
    ModelMap setTXP(@RequestParam("userId")
    String userId, @RequestParam("pf")
    String pf, HttpServletRequest request)
    {
        ModelMap result = new ModelMap();
        Map<String, String> condition = new HashMap<String, String>();

        condition.put("userId", userId);
        condition.put("pf", pf);
        condition.put("opf", request.getParameter("opf"));
        try
        {
            int k = this.noUserCenterService.updatePF(condition);
            if (k == 1)
                result.put("message", "success");
            else
                result.put("message", "wrongold");
        }
        catch (Exception e)
        {
            e.printStackTrace();
            result.put("message", "error");
        }
        return result;
    }

    /**
     * 
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value = "/uploadHead")
    @ResponseBody
    public ModelMap uploadHead(HttpServletRequest request)
    {
        ModelMap model = new ModelMap();
        try
        {
            String imgUrl = request.getParameter("url");
            String userId = request.getParameter("userId");
            Integer row = this.noUserCenterService.updateHead(imgUrl, userId);
            if (row > 0)
            {
                model.put("message", "success");
            }
            else
            {

                model.put("message", "error");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return model;
    }

    @RequestMapping("/userInfoAndSignIn")
    public @ResponseBody
    Map userInfoAndSignIn(@RequestParam("userId")
    int userId)
    {
        return this.noUserCenterService.findUserInfoAndSignIn(userId);
    }
    @RequestMapping("/readMsg")
    public @ResponseBody String readMsg(@RequestParam("userId") int userId) {
        this.noUserCenterService.readMsg(userId);
        return "{\"a\":\"1\"}";
    }
}
