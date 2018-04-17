package com.suyin.server.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suyin.server.redis.JedisUtil;
import com.suyin.simple.service.SyncDataService;

/**
 * no团登录接口
 * 
 * @author madara
 */
@Controller
@RequestMapping(value = "/unifiedLogin")
public class UnifiedLoginController {

	@Autowired
	private JedisUtil redisTemplate;

	@Autowired
	private SyncDataService syncDataService;
	
	@RequestMapping(value = "/setJedis")
	public Map<String, Object> setJedis(HttpServletRequest request,
			HttpServletResponse response) throws JSONException {

		/*// 数据放入redis缓存
		JSONObject j=new JSONObject();
		j.put("token", UUID.randomUUID().toString());
		
		j.put("dateTime", new Date().getTime()+2000000);
		
		redisTemplate.setRedisStrValue("1384163413", j.toString());*/
		syncDataService.syncData();
		return null;
	}
	@RequestMapping(value = "/getJedis")
	public Map<String, Object> getJedis(HttpServletRequest request,
			HttpServletResponse response) throws JSONException {

		// 数据放入redis缓存
		
		String jtoken=redisTemplate.getRedisStrValue("1384163413");
		JSONObject j=new JSONObject(jtoken);
		System.out.println(j.get("token"));
		Date date=new Date();
		if(date.getTime()>j.getLong("dateTime")){
			
		}
		//redisTemplate.delRedisStrValue("name");
		return null;
	}
	
	@RequestMapping(value = "/sendClientMessage")
	@ResponseBody
	public Map<String,Object>  sendClientMessage(HttpServletRequest request,
			HttpServletResponse response){
		Map<String,Object> map=new HashMap<String,Object>();
		System.out.println(redisTemplate.getRedisStrValue("name"));
		map.put("message", "sucess");
		List<String> list=new ArrayList<String>();
		list.add("接口实例规范");
		list.add("接口实例规范");
		map.put("data", list);
		return map;
	}
}
