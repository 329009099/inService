
package com.suyin.activity.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suyin.activity.model.Activity;
import com.suyin.activity.model.PartDTO;
import com.suyin.activity.service.ActivityService;



@Controller
@RequestMapping("/activity")
public class ActivityController{

    @Autowired
    private ActivityService activityService;

    /**
     * 查询活动详情
     * @param request
     * @return
     */
    @RequestMapping(value = "/findActivityDetailById")
    public @ResponseBody  Activity findActivityDetailById(HttpServletRequest request) { 
    	
    	return activityService.findActivityById(1);
    }

    @RequestMapping(value = "/getStatistics")
    public @ResponseBody  PartDTO getStatistics() { 
    	
    	return activityService.getStatistics();
    }
    
    /**
     * 访问数+1
     * @return
     */
    @RequestMapping(value = "/numberplus")
    public @ResponseBody  ModelMap numberplus(HttpServletRequest request) { 
    	ModelMap result=new ModelMap();
    	Integer n=this.activityService.numberplus(null);
    	if(n>0){
    		result.put("status", "success");
    	}else{
    		result.put("status","error");
    	}
    	return result;
    }

}

