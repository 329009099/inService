
package com.suyin.thememonth.controller;

import java.util.HashMap;
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
import com.suyin.thememonth.model.*;
import com.suyin.thememonth.service.*;


/**
 * 
 * 主题月数据查询 
 * @author lz
 * @version 2015-9-15
 * @see ThemeMonthController
 * @since
 */
@Controller
@RequestMapping("/thememonth")
public class ThemeMonthController{

    private final static Logger log=Logger.getLogger(ThemeMonthController.class);
    @Autowired
    private ThemeMonthService themeMonthService;

    /**
     * 首页
     * @return 
     * @see
     */
    @RequestMapping(value="/index")
    public ModelAndView index() {

        return new ModelAndView("thememonth/index");
    }

    /**
     * 
     * 查询主题月信息
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value="/findThemeMonthInfo")
    public @ResponseBody ModelMap findThemeMonthInfo(HttpServletRequest request){
        ModelMap result=new ModelMap();
        Map<String, Object>themeInfo=new HashMap<String, Object>();
        result.put("message", "success");
        result.put("data",themeMonthService.findThemeMonth(themeInfo));
        return result;
    }


}

