package com.suyin.nouser.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suyin.nouser.service.NoUserPrototypeService;

@Controller
@RequestMapping("/noUserPrototype")
public class NoUserPrototypeController {

	@Autowired
	NoUserPrototypeService noUserPrototypeService;
	//获取并修改我的资料 开始
	@RequestMapping("/findUserPrototypeSelected")
	public @ResponseBody String findUserPrototypeSelected(@RequestParam("userId") String userId) {
		JSONObject jso=new JSONObject();
		Map<String,String> condition=new HashMap<String, String>();
		condition.put("userId", userId);
		
		try {
			jso.put("error", 0);
			JSONObject jso1=new JSONObject();
			jso.put("paramdata", this.noUserPrototypeService.findUserPrototypeSelected(condition));
			jso.put("userArea", this.noUserPrototypeService.findUserCitysByInface(userId));
		} catch (Exception e) {
			jso.put("error", 1);
		}
		return jso.toString();
	}
	
	
	@RequestMapping("/addUserPrototype2")
	public @ResponseBody String addUserPrototype2(@RequestParam("userId") int userId,HttpServletRequest request) {
		JSONObject result=new JSONObject();
		try {
			System.out.println("paramdata is "+request.getParameter("paramdata"));
			System.out.println("userArea is "+request.getParameter("userArea"));
			org.json.JSONArray j=new org.json.JSONArray(request.getParameter("paramdata"));
			org.json.JSONObject jo=new org.json.JSONObject(request.getParameter("userArea"));
			//JSONObject jso=JSONObject.fromObject(request.getParameter("userArea"));
			Map<String,Object> condition=new HashMap<String, Object>();
			condition.put("userId", userId);
			this.noUserPrototypeService.addUserPrototype2(j,jo,condition);
			result.put("error", "0");
		} catch (Exception e) {
			result.put("error", "1");
			e.printStackTrace();
		}
		
		return result.toString();
	}
	
	
	//获取并修改我的资料结束

	/**
	 * 个人中心动态属性加载(jsonp方式跨域调用)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findUserPrototype")
	@ResponseBody public JSONPObject findUserPrototype(HttpServletRequest request,String userId) throws Exception {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String jsonp = request.getParameter("callback");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userId", null==userId?"0":userId);
		map.put("parentId", "0");
		map.put("module_type", "1");
		list=noUserPrototypeService.findUserPrototype(map);
		
		return new JSONPObject(jsonp, list);
	}
	@RequestMapping(value = "/findUserPrototype1")
	@ResponseBody public String findUserPrototype1(HttpServletRequest request,String userId) throws Exception {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String jsonp = request.getParameter("callback");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userId", null==userId?"0":userId);
		map.put("parentId", "0");
		map.put("module_type", "1");
		list=noUserPrototypeService.findUserPrototype(map);
		JSONObject jso=new JSONObject();
		jso.put("data", list);
		return jso.toString();
	}
	
	
	
	/**
	 * 用户属性加载(jsonp方式跨域调用)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addUserPrototype")
	@ResponseBody public JSONPObject addUserPrototype(HttpServletRequest request,String userId,String code) throws Exception {
		System.out.println("userId is :"+userId);
		System.out.println("userId is :"+code);
		System.out.println("paramdata is "+request.getParameter("paramdata"));
		System.out.println("userArea is "+request.getParameter("userArea"));
		
		String jsonp = request.getParameter("callback");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userId", null==userId?"0":userId);
		map.put("code", code);
		noUserPrototypeService.deleteNoUserPrototypeByUserId(map);
		Integer i= null;//noUserPrototypeService.addNoUserPrototype(request.getParameter("paramdata"),request.getParameter("upFlag"));
		if("0".equals(request.getParameter("logFlg"))) {
			if(StringUtils.isNotBlank(request.getParameter("userArea")))
			{
				noUserPrototypeService.updateUserArea(request.getParameter("userArea"));
				i=1;
			}else {
				
				i= noUserPrototypeService.addNoUserPrototype(request.getParameter("paramdata"),request.getParameter("upFlag"));
			}
		}
		if("1".equals(request.getParameter("logFlg"))) {
			if(StringUtils.isNotBlank(request.getParameter("userArea")))
				noUserPrototypeService.updateUserArea(request.getParameter("userArea"));
			if(StringUtils.isNotBlank(request.getParameter("paramdata")))
				noUserPrototypeService.addNoUserPrototype(request.getParameter("paramdata"),request.getParameter("upFlag"));
			i=1;
		}
		if(request.getParameter("isLast") != null && "0".equals(request.getParameter("logFlg")))
		{
			String isLast = request.getParameter("isLast");
			if(isLast.equals("1"))
			{
				 //判断用户是否完善全部资料加金币否则无
				 int count = noUserPrototypeService.queryUserPrototypeAll(userId);
				 if(count == 1)
				 {
					 //添加积分
					 Map<String, Object> goldMap = new HashMap<String, Object>();
					 goldMap.put("user_id", userId);
					 goldMap.put("content", "完善资料");
					 goldMap.put("gold_coin", 10);
					 goldMap.put("direction", 1);
					 goldMap.put("create_time", new Date());
					 noUserPrototypeService.addNoUserCoinLog(goldMap);
					 noUserPrototypeService.updateUserGoldCoin(goldMap);
				 }
			}
		}
		return new JSONPObject(jsonp,i);
	}
	@RequestMapping(value = "/addUserPrototype1")
	@ResponseBody public String addUserPrototype1(HttpServletRequest request,String userId,String code) throws Exception {
		System.out.println("paramdata is "+request.getParameter("paramdata"));
		System.out.println("userArea is "+request.getParameter("userArea"));
		String jsonp = request.getParameter("callback");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userId", null==userId?"0":userId);
		map.put("code", code);
		noUserPrototypeService.deleteNoUserPrototypeByUserId(map);
		Integer i= null;//noUserPrototypeService.addNoUserPrototype(request.getParameter("paramdata"),request.getParameter("upFlag"));
		if("0".equals(request.getParameter("logFlg"))) {
			if(StringUtils.isNotBlank(request.getParameter("userArea")))
			{
				noUserPrototypeService.updateUserArea(request.getParameter("userArea"));
				i=1;
			}else {
				
				i= noUserPrototypeService.addNoUserPrototype(request.getParameter("paramdata"),request.getParameter("upFlag"));
			}
		}
		if("1".equals(request.getParameter("logFlg"))) {
			noUserPrototypeService.updateUserArea(request.getParameter("userArea"));
			noUserPrototypeService.addNoUserPrototype(request.getParameter("paramdata"),request.getParameter("upFlag"));
			i=1;
		}
		if(request.getParameter("isLast") != null && "0".equals(request.getParameter("logFlg")))
		{
			String isLast = request.getParameter("isLast");
			if(isLast.equals("1"))
			{
				//判断用户是否完善全部资料加金币否则无
				int count = noUserPrototypeService.queryUserPrototypeAll(userId);
				if(count == 1)
				{
					//添加积分
					Map<String, Object> goldMap = new HashMap<String, Object>();
					goldMap.put("user_id", userId);
					goldMap.put("content", "完善资料");
					goldMap.put("gold_coin", 10);
					goldMap.put("direction", 1);
					goldMap.put("create_time", new Date());
					noUserPrototypeService.addNoUserCoinLog(goldMap);
					noUserPrototypeService.updateUserGoldCoin(goldMap);
				}
			}
		}
		JSONObject jso=new JSONObject();
		jso.put("error", "0");
		return jso.toString();
	}

	
	@RequestMapping(value = "/findUserUpdateFlg")
	@ResponseBody
	public String findUserUpdateFlg(HttpServletRequest request)
	{
		int count = noUserPrototypeService.findUserUpdateFlg(request.getParameter("userId"));
		return String.valueOf(count);
	}
	@RequestMapping(value = "/findUserUpdateFlg1")
	@ResponseBody
	public String findUserUpdateFlg1(HttpServletRequest request)
	{
		int count = noUserPrototypeService.findUserUpdateFlg(request.getParameter("userId"));
		JSONObject jso=new JSONObject();
		jso.put("error", "0");
		jso.put("count", count);
		return jso.toString();
	}
	
	/**
	 * IOS判断用户是否完善资料
	 * 0 ： 未完成
	 * 1 ： 已完成
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryUserPrototypeAll")
	@ResponseBody
	public Map<String,Object> queryUserPrototypeAll(HttpServletRequest request)
	{
		int count = noUserPrototypeService.queryUserPrototypeAll(request.getParameter("userId"));
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("count", count);
		return map;	
	}
	
	/**
	 * weixin 判断用户是否完善资料
	 * 0 ： 未完成
	 * 1 ： 已完成
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/queryUserPrototypeAllByWx")
	@ResponseBody
	public String queryUserPrototypeAllByWx(HttpServletRequest request)
	{
		int count = noUserPrototypeService.queryUserPrototypeAll(request.getParameter("userId"));
		return String.valueOf(count);	
	}
	
	/**
	 * 完善资料属性加载(jsonp方式跨域调用)
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/findUserProblemPrototype")
	 public @ResponseBody JSONPObject findUserProblemPrototype(HttpServletRequest request,String userId) throws Exception {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String jsonp = request.getParameter("callback");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userId", null==userId?"0":userId);
		map.put("module_type", "1");
		if(null!=request.getParameter("code")){
			map.put("dictionary_code", request.getParameter("code"));
		}
		map.put("parentId", null==request.getParameter("dictionaryId")?"0":request.getParameter("dictionaryId"));
	
		list=noUserPrototypeService.findUserPrototype(map);
		
		return new JSONPObject(jsonp, list);
	}
	@RequestMapping(value = "/findUserProblemPrototype1")
	public @ResponseBody String findUserProblemPrototype1(HttpServletRequest request,String userId) throws Exception {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String jsonp = request.getParameter("callback");
		Map<String,Object> map=new HashMap<String,Object>();
		map.put("userId", null==userId?"0":userId);
		map.put("module_type", "1");
		if(null!=request.getParameter("code")){
			map.put("dictionary_code", request.getParameter("code"));
		}
		map.put("parentId", null==request.getParameter("dictionaryId")?"0":request.getParameter("dictionaryId"));
		
		list=noUserPrototypeService.findUserPrototype(map);
		JSONObject jso=new JSONObject();
		jso.put("data", list);
		return jso.toString();
	}
	
	@RequestMapping(value = "/findCodeByMoudleType")
	public @ResponseBody JSONPObject  findCodeByMoudleType(HttpServletRequest request){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		String jsonp = request.getParameter("callback");
		try {
			 list=noUserPrototypeService.findCodeByMoudleType("1");
			
		} catch (Exception e) {
			
		}
		return new JSONPObject(jsonp, list);
	}
	
	
	@RequestMapping(value = "/findCodeByMoudleTypeByInface")
	public @ResponseBody String findCodeByMoudleTypeByInface(HttpServletRequest request){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			 list=noUserPrototypeService.findCodeByMoudleType("1");
		} catch (Exception e) {
			
		}
		return list.toString();
	}
	
	@RequestMapping(value = "/findCitysByInface",produces = "application/json; charset=utf-8")
	public @ResponseBody String findCitysByInface(HttpServletRequest request)
	{
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			//获取省市
			list = noUserPrototypeService.findCitysByInface();
		} catch (Exception e) {
		}
		return JSONArray.fromObject(list).toString();
	}
	
	@RequestMapping(value = "/findUserCitysByInface",produces = "application/json; charset=utf-8")
	public @ResponseBody String findUserCitysByInface(HttpServletRequest request)
	{
		JSONObject jsonObject = noUserPrototypeService.findUserCitysByInface(request.getParameter("userId"));
		return jsonObject.toString();
	}
}
