
package com.suyin.exporder.controller;

import java.util.List;
import java.util.Map;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.suyin.exporder.model.*;
import com.suyin.exporder.service.*;


/**
 * 
 * 免费活动订单处理 
 * @author lz
 * @version 2015-9-14
 * @see ExpOrderController
 * @since
 */
@Controller
@RequestMapping("/exporder")
public class ExpOrderController{

    private final static Logger log=Logger.getLogger(ExpOrderController.class);
    @Autowired
    private ExpOrderService expOrderService;

    /**
     * 首页
     * @return 
     * @see
     */
    @RequestMapping(value="/index")
    public ModelAndView index() {

        return new ModelAndView("exporder/index");
    }


    /**
     * 读取列表
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value = "/list")
    public @ResponseBody Map<String, Object> findForExpOrderAll(HttpServletRequest request) {
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

            ExpOrder  entityInfo=new ExpOrder ();
            entityInfo.setPage(page);
            List<ExpOrder > list=expOrderService.findExpOrderByPage(entityInfo);
            map.put("rows",list); 
            map.put("total",entityInfo.getPage().getTotalResult()); 

        }
        catch (Exception e)
        {
            log.error("Controller Error ExpOrderController-> findExpOrderByWhere  " + e.getMessage());
        }

        return map;
    }




    /**
     * 跳转添加页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/jumpAdd")
    public ModelAndView jumpExpOrderAdd(HttpServletRequest request) {
        ModelMap map=new ModelMap();

        return new ModelAndView("exporder/save",map);
    }

    /**
     * 跳转修改页面 
     * @param request
     * @return
     */
    @RequestMapping(value = "/jumpEdit")
    public ModelAndView jumpExpOrderEdit(HttpServletRequest request) {
        ModelMap map=new ModelMap();
        try
        {

            if(Tools.notEmpty(request.getParameter("id"))){  
                
                ExpOrder entity=new ExpOrder();
                entity.setOrderId(Integer.parseInt(request.getParameter("id")));
                entity=expOrderService.findExpOrderById(entity);
                map.put("exporder",entity);

            }
        }
        catch (Exception e)
        {

            log.error("Controller Error ExpOrderController-> jumpExpOrderEdit  " + e.getMessage());
        }
        return new ModelAndView("exporder/edit",map);
    }

    /**
     * 信息保存
     * Description: <br>
     * @param 
     * @return 
     * @see
     */
    @RequestMapping(value = "/add")
    public @ResponseBody Map<String, Object> saveExpOrderInfo(ExpOrder entity) {
        ModelMap map=new ModelMap();
        try
        {
            
            map.put("result",expOrderService.addExpOrder(entity));
        }
        catch (Exception e)
        {
            log.error("Controller Error ExpOrderController-> saveExpOrderInfo " + e.getMessage());
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
    public @ResponseBody Map<String, Object> updateExpOrderById(ExpOrder entity) {
        ModelMap map=new ModelMap();
        try
        {            
            map.put("result",expOrderService.updateExpOrder(entity));
        }
        catch (Exception e)
        {
            log.error("Controller Error ExpOrderController-> updateExpOrderById  " + e.getMessage());
        }
        return map;
    }

    /**
     * 信息删除
     * @param 
     * @return
     */
    @RequestMapping(value = "/delete")
    public @ResponseBody Map<String, Object> deleteExpOrderById(String id) {

        ModelMap map=new ModelMap();
        try
        {
            if(Tools.notEmpty(id)){
                
                map.put("result",expOrderService.deleteExpOrder(id));
            }  
        }
        catch (Exception e)
        {
            log.error("Controller Error ExpOrderController-> deleteExpOrderById  " + e.getMessage());
        }
 
        return map;
    }


}

