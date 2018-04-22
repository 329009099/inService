
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
     * 首页
     * @return 
     * @see
     */
    @RequestMapping(value="/index")
    public ModelAndView index() {

        return new ModelAndView("expdecorateuser/index");
    }


    /**
     * 读取列表
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value = "/list")
    public @ResponseBody Map<String, Object> findForExpDecorateUserAll(HttpServletRequest request) {
        ModelMap map=new ModelMap();

        String pag = request.getParameter("page");
        String showCount = request.getParameter("rows");
        Page page = new Page();
        try
        {      
            if (null != pag && null != showCount) {
                page.setCurrentPage(Integer.parseInt(pag));
                page.setShowCount(Integer.parseInt(showCount));
            }

            ExpDecorateUser  entityInfo=new ExpDecorateUser ();
            entityInfo.setPage(page);
            List<ExpDecorateUser > list=expDecorateUserService.findExpDecorateUserByPage(entityInfo);
            map.put("rows",list); 
            map.put("total",entityInfo.getPage().getTotalResult()); 

        }
        catch (Exception e)
        {
            log.error("Controller Error ExpDecorateUserController-> findExpDecorateUserByWhere  " + e.getMessage());
        }

        return map;
    }




    /**
     * 跳转添加页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/jumpAdd")
    public ModelAndView jumpExpDecorateUserAdd(HttpServletRequest request) {
        ModelMap map=new ModelMap();

        return new ModelAndView("expdecorateuser/save",map);
    }

    /**
     * 跳转修改页面 
     * @param request
     * @return
     */
    @RequestMapping(value = "/jumpEdit")
    public ModelAndView jumpExpDecorateUserEdit(HttpServletRequest request) {
        ModelMap map=new ModelMap();
        try
        {

            if(Tools.notEmpty(request.getParameter("id"))){  
                
                ExpDecorateUser entity=new ExpDecorateUser();
                entity.setUserId(Integer.parseInt(request.getParameter("id")));
                entity=expDecorateUserService.findExpDecorateUserById(entity);
                map.put("expdecorateuser",entity);

            }
        }
        catch (Exception e)
        {

            log.error("Controller Error ExpDecorateUserController-> jumpExpDecorateUserEdit  " + e.getMessage());
        }
        return new ModelAndView("expdecorateuser/edit",map);
    }

    /**
     * 信息保存
     * Description: <br>
     * @param 
     * @return 
     * @see
     */
    @RequestMapping(value = "/add")
    public @ResponseBody Map<String, Object> saveExpDecorateUserInfo(ExpDecorateUser entity) {
        ModelMap map=new ModelMap();
        try
        {
            
            map.put("result",expDecorateUserService.addExpDecorateUser(entity));
        }
        catch (Exception e)
        {
            log.error("Controller Error ExpDecorateUserController-> saveExpDecorateUserInfo " + e.getMessage());
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

