
package com.suyin.decorate.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suyin.decorate.model.ExpDecorateUser;
import com.suyin.decorate.service.ExpDecorateUserService;
import com.suyin.system.model.Page;
import com.suyin.system.util.Tools;



@Controller
@RequestMapping("/expdecorateuser")
public class ExpDecorateUserController{

    private final static Logger log=Logger.getLogger(ExpDecorateUserController.class);
    @Autowired
    private ExpDecorateUserService expDecorateUserService;
    
    /**
     * 新增或修改信息
     * @param entity
     * @return
     */
    @RequestMapping(value = "/initSaveDecorateUser")
    public @ResponseBody Map<String, Object> initSaveDecorateUser(HttpServletRequest request,ExpDecorateUser entity) {
        ModelMap map=new ModelMap(); 
        try
        {
        	ExpDecorateUser user=expDecorateUserService.findUserInfoByUserIdOrOpenId("", entity.getOpenid());
        	if(null!=user){
        		map.put("result", "success");
        	}else{
        		map.put("result",expDecorateUserService.initSaveDecorateUser(entity));

        	}
        }
        catch (Exception e)
        {
            log.error("Controller Error ExpDecorateUserController-> saveExpDecorateUserInfo " + e.getMessage());
        }
        return map;
    } 
    /**
     * 通过用户ID和openId查询用户信息
     * @param userId
     * @param openId
     * @return
     */
    @RequestMapping(value="/findUserInfoByUserIdOrOpenId")
    public @ResponseBody ExpDecorateUser findUserInfoByUserIdOrOpenId(String userId,String openId) {
		
    	ExpDecorateUser expDecorateUser =  this.expDecorateUserService.findUserInfoByUserIdOrOpenId(userId, openId);
    	return expDecorateUser;
    }
    
    /**
     * 新增或修改信息
     * @param entity
     * @return
     */
    @RequestMapping(value = "/saveOrUptateExpDecorateUserInfo")
    public @ResponseBody Map<String, Object> saveOrUptateExpDecorateUserInfo(HttpServletRequest request,ExpDecorateUser entity) {
        ModelMap map=new ModelMap(); 
        try
        {
            map.put("result",expDecorateUserService.updateExpDecorateUser(entity));
        }
        catch (Exception e)
        {
            log.error("Controller Error ExpDecorateUserController-> saveExpDecorateUserInfo " + e.getMessage());
        }
        return map;
    }
    
    /**
     * 通过Openid修改余额
     * @param request
     * @param entity
     * @return
     */
    @RequestMapping(value = "/updateBalancePriceByOpendId")
    public @ResponseBody Map<String, Object> updateBalancePriceByOpendId(HttpServletRequest request,ExpDecorateUser entity) {
    	ModelMap map=new ModelMap(); 
    	try
    	{
    		map.put("result",expDecorateUserService.updateBalancePriceByOpendId(entity));
    	}
    	catch (Exception e)
    	{
    		log.error("Controller Error ExpDecorateUserController-> updateBalancePriceByOpendId " + e.getMessage());
    	}
    	return map;
    }

    /**
        * 提现创建订单
        * @param request
        * @param entity
        * @return
        */
       @RequestMapping(value = "/withdrawCreateOrder")
       public @ResponseBody Map<String, Object> withdrawCreateOrder(HttpServletRequest request,ExpDecorateUser entity) {
       	ModelMap map=new ModelMap(); 
       	try
       	{
       		String withdrawPrice =request.getParameter("withdrawPrice");//提现金额

       		map.put("result",expDecorateUserService.withdrawCreateOrder(entity,withdrawPrice));
       	}
       	catch (Exception e)
       	{
       		log.error("Controller Error ExpDecorateUserController-> withdrawCreateOrder " + e.getMessage());
       	}
       	return map;
       }

     
  
    /**
     * 信息修改
     * Description: <br>
     * @param 
     * @return 
     * @see
     */
    @RequestMapping(value = "/update")
    public @ResponseBody Map<String, Object> updateExpDecorateUserById(ExpDecorateUser entity) {
        ModelMap map=new ModelMap();
        try
        {            
            map.put("result",expDecorateUserService.updateExpDecorateUser(entity));
        }
        catch (Exception e)
        {
            log.error("Controller Error ExpDecorateUserController-> updateExpDecorateUserById  " + e.getMessage());
        }
        return map;
    }

    /**
     * 信息删除
     * @param 
     * @return
     */
    @RequestMapping(value = "/delete")
    public @ResponseBody Map<String, Object> deleteExpDecorateUserById(String id) {

        ModelMap map=new ModelMap();
        try
        {
            if(Tools.notEmpty(id)){
                
                map.put("result",expDecorateUserService.deleteExpDecorateUser(id));
            }  
        }
        catch (Exception e)
        {
            log.error("Controller Error ExpDecorateUserController-> deleteExpDecorateUserById  " + e.getMessage());
        }
 
        return map;
    }


}

