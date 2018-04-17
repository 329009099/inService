
package com.suyin.expshare.controller;

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

import com.suyin.expshare.model.*;
import com.suyin.expshare.service.*;


/**
 * 人气式用户分享专用
 * @author lz
 * @version 2015-9-15
 * @see ExpShareController
 * @since
 */
@Controller
@RequestMapping("/expshare")
public class ExpShareController{

    private final static Logger log=Logger.getLogger(ExpShareController.class);
    @Autowired
    private ExpShareService expShareService;

    /**
     * 保存用户分享信息
     * @param entity
     * @return 
     * @see
     */
    @RequestMapping(value = "/add")
    public @ResponseBody Map<String, Object> saveExpShareInfo(ExpShare entity) {
        ModelMap map=new ModelMap();
        try
        {

            map.put("result",expShareService.addExpShare(entity));
        }
        catch (Exception e)
        {
            log.error("Controller Error ExpShareController-> saveExpShareInfo " + e.getMessage());
        }
        return map;
    }

    /**
     * 保存用户分享信息
     * @param entity
     * @return 
     * @see
     */
    @RequestMapping(value = "/editExpShareInfo")
    public @ResponseBody void editExpShareInfo(ExpShare entity) {
        ModelMap map=new ModelMap();
        try
        {
            entity.setShareNum(1);
            map.put("result",expShareService.updateExpShare(entity));
        }
        catch (Exception e)
        {
            log.error("Controller Error ExpShareController-> updateExpShareInfo " + e.getMessage());
        }

    }

    @RequestMapping("/findRankForShare")
    public @ResponseBody ModelMap findRankForShare(HttpServletRequest request) {
        Map<String,Object> condition=new HashMap<String, Object>();
        condition.put("userId", request.getParameter("userId"));
        condition.put("detailId", request.getParameter("detailId"));
        Page page=new Page();
        if(StringUtils.isNotBlank(request.getParameter("page.showCount"))) 
            page.setShowCount(Integer.parseInt(request.getParameter("page.showCount")));
        if(StringUtils.isNotBlank(request.getParameter("page.currentPage")))
            page.setCurrentPage(Integer.parseInt(request.getParameter("page.currentPage")));
        condition.put("page", page);
        ModelMap result=new ModelMap();
        /***可根据逻辑动态选择性查询真实或是虚拟的数据****/
        result.put("data", this.expShareService.findRankForShareByPage(condition)); //真实数据查询
//        result.put("data", this.expShareService.findFalseRankForShareByPage(condition));//混合虚拟数据
        result.put("args", condition);
        result.put("message", "success");
        return result;
    }
    @RequestMapping("/findRankForMySelf")
    public @ResponseBody ModelMap findRankForMySelf(HttpServletRequest request) {
        Map<String,Object> condition=new HashMap<String, Object>();
        condition.put("userId", request.getParameter("userId"));
        condition.put("detailId", request.getParameter("detailId"));
        ModelMap result=new ModelMap();
        /***可根据逻辑动态选择性查询真实或是虚拟的数据****/
        result.put("data", this.expShareService.findRankForMySelf(condition));//真实数据查询
//        result.put("data", this.expShareService.findFalseRankForMySelf(condition));//混合虚拟数据
        result.put("args", condition);
        result.put("message", "success");
        return result;
    }


}

