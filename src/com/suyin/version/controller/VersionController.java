
package com.suyin.version.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

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
import com.suyin.version.service.VersionService;

import java.util.*;

import com.suyin.expshare.model.*;
import com.suyin.expshare.service.*;


/**
 * 版本管理
 * @author shanghailang
 * @version 2015-12-17
 * @see VersionController
 * @since
 */
@Controller
@RequestMapping("/version")
public class VersionController{

    private final static Logger log=Logger.getLogger(VersionController.class);
    @Autowired
    private VersionService versionService;

    /**
     * 查询最新版本信息
     * @param entity
     * @return 
     * @see
     */
    @RequestMapping(value = "/findVersionInfo")
    public @ResponseBody JSONObject findVersionInfo(HttpServletRequest request,HttpServletResponse response) throws Exception{
	    response.setContentType("text/html");
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8"); 
        JSONObject jsonObject = new JSONObject();
        //版本号
        String versionName=request.getParameter("versionName");
        //客户端类型
        String clientType=request.getParameter("clientType");
        try
        {
        	Map<String,String> map = new HashMap<String, String>();
        	map.put("versionName", versionName);
        	map.put("clientType", clientType);
        	
        	jsonObject=	versionService.findVersionInfo(map);
        }
        catch (Exception e)
        {
        	e.printStackTrace();
            log.error("Controller Error VersionController-> findVersionInfo " + e.getMessage());
        }
        return jsonObject;
    }

   


}

