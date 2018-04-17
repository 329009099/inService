
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
 * 免费活动券处理
 * @author lz
 * @version 2015-9-14
 * @see ExpVoucherController
 * @since
 */
@Controller
@RequestMapping("/expvoucher")
public class ExpVoucherController{

    private final static Logger log=Logger.getLogger(ExpVoucherController.class);
    @Autowired
    private ExpVoucherService expVoucherService;




    /**
     * 信息保存
     * Description: <br>
     * @param 
     * @return 
     * @see
     */
    @RequestMapping(value = "/add")
    public @ResponseBody Map<String, Object> saveExpVoucherInfo(ExpVoucher entity) {
        ModelMap map=new ModelMap();
        try
        {

            map.put("result",expVoucherService.addExpVoucher(entity));
        }
        catch (Exception e)
        {
            log.error("Controller Error ExpVoucherController-> saveExpVoucherInfo " + e.getMessage());
        }
        return map;
    }  


}

