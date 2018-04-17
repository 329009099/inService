
package com.suyin.nouser.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suyin.nouser.model.Nouser;
import com.suyin.nouser.service.NoUserCenterService;
import com.suyin.nouser.service.NouserService;
import com.suyin.system.util.Md5Util;


/**
 * 客户端用户注册信息操作
 * @author lz
 * @version 2015-8-28
 * @see NouserController
 * @since
 */
@Controller
@RequestMapping("/nouser")
public class NouserController{

    private final static Logger log=Logger.getLogger(NouserController.class);
    
    @Autowired
    private NouserService nouserService;

    /**
     * 跳转添加页面  测试使用 
     * @param request
     * @return
     */
    @RequestMapping(value = "/jumpAdd")
    public ModelAndView jumpNouserAdd(HttpServletRequest request) {
        ModelMap map=new ModelMap();

        return new ModelAndView("nouser/save",map);
    }

    /**
     * 根据手机号码查询用户信息
     * @return 
     * @see
     */
    @RequestMapping(value="/findUserByIphone")
    public @ResponseBody String findNouserWhereByIphone(HttpServletRequest request,Nouser entity){
        String result="";
        try
        {
            result= nouserService.findNouserWhereByIphone(entity);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            log.error("Controller Error NouserController-> findNouserWhereByIphone  " + e.getMessage());
        }
        return result;
    }


    /**
     * 
     *根据微信openid查询用户信息 
     * @return 
     * @see
     */
    @RequestMapping(value="/findUserByOpenId")
    public @ResponseBody String findNouserWhereByOpenId(HttpServletRequest request,Nouser entity){
        String result="";

        try
        {
            result= nouserService.findNouserWhereByOpenId(entity);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            log.error("Controller Error NouserController-> findNouserWhereByOpenId  " + e.getMessage());
        }


        return result;
    }


    /**
     * 
     *根据用户userId查询——个人中心 安全账户设置所需的基本信息 
     * @return 
     * @see
     */
    @RequestMapping(value="/findNouserStaticInfo")
    public @ResponseBody ModelMap findNouserStaticInfo(HttpServletRequest request){
        String result="";
        ModelMap model=new ModelMap();
        Map<String, Object>userInfo=new HashMap<String, Object>();
        String userId=request.getParameter("userId");
        userInfo.put("userId", userId);
        try
        {

            model.put("result",  nouserService.findNouserStaticInfo(userInfo));
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            log.error("Controller Error NouserController-> findNouserStaticInfo  " + e.getMessage());
        }


        return model;
    }


    /**
     * 根据手机验证是否存在
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value="/validUserInfo")
    public @ResponseBody String validUserInfo(HttpServletRequest request,Nouser entity){
        String result="";

        try
        {
            result = nouserService.validUserInfo(entity);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            log.error("Controller Error NouserController-> validUserInfo  " + e.getMessage());
        }


        return result;
    }

    /**
     * 
     * 用户登录 
     * @param request
     * @param entity
     * @return 
     * @see
     */
    @RequestMapping(value = "/login")
    public @ResponseBody String login(HttpServletRequest request,Nouser entity) {

        String result="";
        try
        {  

            result = nouserService.login(entity);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            log.error("Controller Error NouserController-> login " + e.getMessage());
        }

        return result;
    }

    @RequestMapping(value="/updateStatus")
    public @ResponseBody String updateStatus(HttpServletRequest request,Nouser entity){
        String result="";
        try
        {
            result=nouserService.updateUserOpenIdInfo(entity);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            log.error("Controller Error NouserController-> updateStatus " + e.getMessage());
        }
        return result;
    }



    /**
     * 
     *获取验证码  
     * @param request 
     * @see
     */
    @RequestMapping(value="/backCodeNumber")
    public  @ResponseBody String backCodeNumber(HttpServletRequest request){
        String result="";
        try
        {
            result=nouserService.backCodeNumber(request);

        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            log.error("Controller Error NouserController-> backCodeNumber  " + e.getMessage());
        }
        return result;
    }

    /**
     * 找回密码 
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value="/backpwd")
    public @ResponseBody String backPwd(HttpServletRequest request,Nouser entity){
        String result="";
        try
        {

            result = nouserService.backPwd(request,entity);

        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            log.error("Controller Error NouserController-> backPwd  " + e.getMessage());
        }

        return result;
    }
    
    /**
     * 忘记密码 
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value="/forgetPwd")
    @ResponseBody
    public  String forgetPwd(HttpServletRequest request,Nouser entity){
        String result="";
        try
        {
            result = nouserService.forgetPwd(request,entity);
        }
        catch (Exception e)
        {
            log.error("Controller Error NouserController-> forgetPwd  " + e.getMessage());
        }

        return result;
    }

    /**
     * 
     * 注册时获取验证码  
     * @param request 
     * @see
     */
    @RequestMapping(value="/requestNumber")
    public @ResponseBody String  requestNumber(HttpServletRequest request,HttpServletResponse response){
        String result="";
        try
        {
            result=nouserService.requestNumber(request);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            log.error("Controller Error NouserController-> requestNumber  " + e.getMessage());
        }

        return result;
    }

    /**
     * 
     * 用户注册 
     * @param request
     * @param entity
     * @return 
     * @see
     */

    @RequestMapping(value = "/regNouser")
    public @ResponseBody String regNouserInfo(HttpServletRequest request,Nouser entity) {

        String result="";
        try
        {
            result = nouserService.regNouserInfo(request, entity);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            log.error("Controller Error NouserController-> regNouserInfo " + e.getMessage());
        }

        return result;
    }



    /************************************************************************************************/
    /*****************************************账户安全修改密码********************************************/
    /*****************************************二级密码修改***********************************************/
    /****************************************支付宝信息修改***********************************************/
    /************************************************************************************************/


    /**
     * 根据用户userId查询该用户是否设置安全账户
     * @param request
     * @return success 已设置 error 未设置
     * @see
     */
    @RequestMapping(value="/findSecurityUserInfo")
    public @ResponseBody ModelMap findSecurityUserInfo(HttpServletRequest request){

        ModelMap map=new ModelMap();
        String userId=request.getParameter("userId");
        Map<String, Object>mapInfo=new HashMap<String, Object>();
        mapInfo.put("userId", userId);
        map.put("message",nouserService.findSecurityUserInfo(mapInfo));
        return map;

    }

    /**
     * 个人中心_修改安全账户信息
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value="/updateSecurityInfo")
    public @ResponseBody String updateSecurityInfo(HttpServletRequest request){
        String result="";
        Map<String, Object>mapInfo=new HashMap<String, Object>();

        String ali_pay=request.getParameter("ali_pay");//支付宝邮箱或账户
        String withdrawals_password=request.getParameter("wpassword");//二级提现密码
        String login_password=request.getParameter("login_password"); //登录密码
        String user_phone=request.getParameter("user_phone");//当前用户手机号码
        String ali_user_name=request.getParameter("ali_user_name");//支付宝所有者姓名
        String userId=request.getParameter("userId");

        try
        {     

            mapInfo.put("ali_pay", ali_pay);
            if(!StringUtils.isBlank(withdrawals_password))
                mapInfo.put("withdrawals_password", Md5Util.toMD5(withdrawals_password));
            if(!StringUtils.isBlank(login_password))
                mapInfo.put("login_password", Md5Util.toMD5(login_password));
            mapInfo.put("user_phone", user_phone);
            mapInfo.put("ali_user_name", ali_user_name);
            mapInfo.put("userId", userId);

            result=this.nouserService.updateSecurityInfo(mapInfo);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 个人中心_修改提现密码
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value="/modifyWithdrawalsPassword")
    @ResponseBody
    public String modifyWithdrawalsPassword(HttpServletRequest request){
    	String result="";

        // 用户ID
        String userId = request.getParameter("userId");
        //当前用户手机号码
        String userPhone = request.getParameter("userPhone");
        //提现密码
        String withdrawalsPassword = request.getParameter("wPwd");
        //验证码
        String code = request.getParameter("code");
        try
        {     
            result = this.nouserService.modifyWithdrawalsPassword(request, userId, userPhone, withdrawalsPassword, code);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return result;
    }
    
    /**
     * 
     * 修改提现密码获取验证码  
     * @param request 
     * @see
     */
    @RequestMapping(value="/withdrawalsCode")
    public @ResponseBody String  withdrawalsCode(HttpServletRequest request){
        String result="";
        try
        {
            result = nouserService.withdrawalsCode(request);
        }
        catch (Exception e)
        {
            log.error("Controller Error NouserController-> withdrawalsCode  " + e.getMessage());
        }

        return result;
    }


    /**
     * 修改登录密码
     * @param request
     * @return 
     * @see
     */
    @RequestMapping("/changePW")
    public @ResponseBody ModelMap changePassword(HttpServletRequest request) {
        ModelMap result=new ModelMap();
        Map<String,String> map=new HashMap<String,String>();
        map.put("userId", request.getParameter("userId"));
        map.put("oldP", request.getParameter("oldP"));
        map.put("newP", request.getParameter("newP"));
        String s=this.nouserService.updatePW(map);
        return result;
    }

    /**
     *提现密码修改
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value="/aliPayPassword")
    public @ResponseBody String aliPayPassword(HttpServletRequest request){
        Map<String,String> map=new HashMap<String,String>();
        map.put("userId", request.getParameter("userId"));
        map.put("oldP", request.getParameter("oldP"));
        map.put("newP", request.getParameter("newP"));

        return "";
    }


    /**
     * 
     * 更新用户支付宝账户信息
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value="/aliPayUserNumber")
    public @ResponseBody String aliPayUserNumber(HttpServletRequest request){

        return "";
    }    

    /**
     * 账户安全设置，验证码
     * @param request 
     * @see
     */
    @RequestMapping(value="/securityCode")
    public void securityCode(HttpServletRequest request){


    }

}

