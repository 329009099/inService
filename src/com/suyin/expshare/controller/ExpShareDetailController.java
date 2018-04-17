
package com.suyin.expshare.controller;

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
import com.suyin.expshare.model.*;
import com.suyin.expshare.service.*;


/**
 * 
 * 用户分享——朋友帮助分享详情类
 * 记录有哪些好友帮助分享的好友信息
 * @author lz
 * @version 2015-9-15
 * @see ExpShareDetailController
 * @since
 */
@Controller
@RequestMapping("/expsharedetail")
public class ExpShareDetailController{

    private final static Logger log=Logger.getLogger(ExpShareDetailController.class);
    @Autowired
    private ExpShareDetailService expShareDetailService;


    /**
     * 信息保存
     * Description: <br>
     * @param 
     * @return 
     * @see
     */
    @RequestMapping(value = "/add")
    public @ResponseBody void saveExpShareDetailInfo(ExpShareDetail entity) {


        expShareDetailService.addExpShareDetail(entity);

    }


}

