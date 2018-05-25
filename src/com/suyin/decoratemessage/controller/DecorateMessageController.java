
package com.suyin.decoratemessage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suyin.system.model.Page;
import com.suyin.system.util.Tools;

import java.util.*;

import com.suyin.decoratemessage.model.*;
import com.suyin.decoratemessage.service.*;
import com.suyin.decoratevoucher.model.ExpDecorateUserVoucher;


/**
 * 消息记录查询
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/indecoratemessage")
public class DecorateMessageController{

    private final static Logger log=Logger.getLogger(DecorateMessageController.class);
    @Autowired
    private DecorateMessageService decorateMessageService;


    /**
     * 读取列表
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value = "/findUseMessageList")
    public @ResponseBody ModelMap findForDecorateMessageAll(HttpServletRequest request) {
    	ModelMap result=new ModelMap();
        Map<String,Object> condition=new HashMap<String, Object>();
        String openId=request.getParameter("openid");
        Page page=new Page();
        if(StringUtils.isNotBlank(request.getParameter("page.showCount"))) 
            page.setShowCount(Integer.parseInt(request.getParameter("page.showCount")));
        if(StringUtils.isNotBlank(request.getParameter("page.currentPage")))
            page.setCurrentPage(Integer.parseInt(request.getParameter("page.currentPage")));
        condition.put("page", page); 
        condition.put("openid", openId);   
        result.put("args", condition);
 		List<DecorateMessage> list=decorateMessageService.findDecorateMessageByPage(condition);	
 		if(list.size()<1){
            result.put("message", "error");
        }else {
            result.put("data", list);
            result.put("message", "success");       
        }
        return result;
    }




    /**
     * 跳转添加页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/jumpAdd")
    public ModelAndView jumpDecorateMessageAdd(HttpServletRequest request) {
        ModelMap map=new ModelMap();

        return new ModelAndView("decoratemessage/save",map);
    }

    /**
     * 跳转修改页面 
     * @param request
     * @return
     */
    @RequestMapping(value = "/jumpEdit")
    public ModelAndView jumpDecorateMessageEdit(HttpServletRequest request) {
        ModelMap map=new ModelMap();
        try
        {

            if(Tools.notEmpty(request.getParameter("id"))){  
                
                DecorateMessage entity=new DecorateMessage();
                entity.setMessageId(Integer.parseInt(request.getParameter("id")));
                entity=decorateMessageService.findDecorateMessageById(entity);
                map.put("decoratemessage",entity);

            }
        }
        catch (Exception e)
        {

            log.error("Controller Error DecorateMessageController-> jumpDecorateMessageEdit  " + e.getMessage());
        }
        return new ModelAndView("decoratemessage/edit",map);
    }

    /**
     * 信息保存
     * Description: <br>
     * @param 
     * @return 
     * @see
     */
    @RequestMapping(value = "/add")
    public @ResponseBody Map<String, Object> saveDecorateMessageInfo(DecorateMessage entity) {
        ModelMap map=new ModelMap();
        try
        {
            
            map.put("result",decorateMessageService.addDecorateMessage(entity));
        }
        catch (Exception e)
        {
            log.error("Controller Error DecorateMessageController-> saveDecorateMessageInfo " + e.getMessage());
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
    public @ResponseBody Map<String, Object> updateDecorateMessageById(DecorateMessage entity) {
        ModelMap map=new ModelMap();
        try
        {            
            map.put("result",decorateMessageService.updateDecorateMessage(entity));
        }
        catch (Exception e)
        {
            log.error("Controller Error DecorateMessageController-> updateDecorateMessageById  " + e.getMessage());
        }
        return map;
    }

    /**
     * 信息删除
     * @param 
     * @return
     */
    @RequestMapping(value = "/delete")
    public @ResponseBody Map<String, Object> deleteDecorateMessageById(String id) {

        ModelMap map=new ModelMap();
        try
        {
            if(Tools.notEmpty(id)){
                
                map.put("result",decorateMessageService.deleteDecorateMessage(id));
            }  
        }
        catch (Exception e)
        {
            log.error("Controller Error DecorateMessageController-> deleteDecorateMessageById  " + e.getMessage());
        }
 
        return map;
    }


}

