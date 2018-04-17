package com.suyin.about.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;



import com.suyin.about.service.AboutService;


@Controller
@RequestMapping("/about")
public class AboutController {

	@Autowired
	AboutService aboutService;

	
	//返回数据库查找的合作商家数据
	@RequestMapping(value="/findMemberByAll",method=RequestMethod.GET)
	public @ResponseBody ModelMap findMemberByAll(HttpServletRequest request) {
		Map<String,Object> paramMap=new HashMap<String,Object>();	
		ModelMap result=new ModelMap();	
		
		List<Map<String,Object>> list=aboutService.findMemberByAll(paramMap);	
		if(list==null) {
			result.put("message", "error");
		}else {
			result.put("data", aboutService.findMemberByAll(paramMap));
			result.put("message", "success");
		}
		return result;
	}
	//返回体验店地址
	@RequestMapping(value="/findStoreByAll",method=RequestMethod.GET)
    public @ResponseBody ModelMap findStoreByAll(HttpServletRequest request){
		Map<String,Object> paramMap=new HashMap<String,Object>();
		ModelMap result =new ModelMap();
		List<Map<String,Object>> list=aboutService.findStoreByAll(paramMap);
		if(list==null){
			result.put("message", "error");
		}
		else{
			result.put("data", list);
			result.put("message", "success");
		}
		return result;
	}

}
