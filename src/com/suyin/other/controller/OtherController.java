package com.suyin.other.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suyin.common.Utils;
import com.suyin.other.service.OtherService;

/**
 * 该controller处理与整个平台业务没有任何关系的请求，如首页广告，个人主页的新手指南啊，关于我们啊，用户协议啊什么的，
 * 并且只是显示，没有其他的什么东西
 * 
 * @author Administrator
 * 
 */
@Controller
@RequestMapping("/")
public class OtherController {

    @Autowired
    private OtherService otherService;

    /**
     * 查找广告，有两种广告：首页广告和发现广告： 0表示首页广告； 1表示发现广告
     * 
     * @param type
     * @return
     */
    @RequestMapping(value = "/advs/find")
    public @ResponseBody
    ModelMap findAdvs(@RequestParam("type") String type,HttpServletRequest request) {
        ModelMap result = new ModelMap();
        Utils.fillResult(request, result);
        //获取请求端平台类型
        Map<String,Object> params=new HashMap<String, Object>();
        params.put("type", type);
        params.put("regtype",  result.get("regtype"));
        
        try {
            List<Map<String, String>> list = this.otherService.findAdvs(params);
            result.put("message", "success");
            result.put("data", list);
        } catch (Exception e) {
            result.put("message", "error");
        }
        return result;

    }


    @RequestMapping(value="/advs/findById")
    public @ResponseBody ModelMap findAdvsById(@RequestParam("id") String id,HttpServletRequest request) {
        ModelMap result=new ModelMap();
        Utils.fillResult(request, result);
        try {
            Map<String,String> list=this.otherService.findAdvsById(id);
            result.put("message", "success");
            result.put("data", list);
        } catch (Exception e) {
            result.put("message", "error");
        }
        return result;
    }
    @RequestMapping("/no/about")
    public @ResponseBody
    ModelMap findInfo(@RequestParam("type") String type) {
        ModelMap result = new ModelMap();
        result.put("message", "success");
        result.put("data", this.otherService.findAbout(type));
        return result;
    }

    @RequestMapping("/no/actIntro")
    public @ResponseBody
    ModelMap findActIntoDetail(@RequestParam("type") String type) {
        ModelMap result = new ModelMap();
        result.put("message", "success");
        result.put("data", this.otherService.findActIntoDetail(type));
        return result;
    }
}
