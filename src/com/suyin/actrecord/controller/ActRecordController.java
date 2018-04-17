
package com.suyin.actrecord.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suyin.actrecord.model.ActRecord;
import com.suyin.actrecord.service.*;



@Controller
@RequestMapping("/actrecord")
public class ActRecordController{

    private final static Logger log=Logger.getLogger(ActRecordController.class);
    @Autowired
    private ActRecordService actRecordService;
    
    

    /**
     * 参与活动
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value = "/joinAct")
    public @ResponseBody Map<String, Object> joinAct(ActRecord act,HttpServletRequest request) {
        Map<String,Object> map=new HashMap<String,Object>();

        map=actRecordService.insertJoinAct(act);

        return map;
    }


  


}

