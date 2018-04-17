package com.suyin.postion.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suyin.common.Utils;
import com.suyin.postion.model.City;
import com.suyin.postion.service.CityService;
import com.suyin.system.util.HttpPost;

/**
 * 该controller用于城市定位
 * 城市定位为两种：
 * 手动定位和自动定位
 * @author qz
 *
 */
@Controller
@RequestMapping("/city")
public class CityController {

	private final static Logger log=Logger.getLogger(CityController.class);

	@Autowired
	private CityService cityService;

	/**
	 * 根据城市名称获取对应的城市信息
	 * 城市名称来自于：请求的URL并解析
	 * @param name
	 * @return
	 */
	@RequestMapping(value="/findCity")
	public @ResponseBody ModelMap findCity(HttpServletRequest request)
	{
		ModelMap result=new ModelMap();
		//获取请求端城市名称
		try {
			String lat=request.getParameter("lat");//获取请求的纬度值
			String lng=request.getParameter("lng");//获取请求的经度值	
			if(null!=lat&&null!=lng)
			{				
				Map<String,Object> map=new HashMap<String,Object>();
				map.put("lat", lat);
				map.put("lng", lng);
				String cityName=this.cityService.findCityName(map);
				City cityInfo=this.cityService.findCityByName(cityName);
				if(null!=cityInfo){

					result.put("message", "success");
					result.put("id", cityInfo.getId());
					result.put("name", cityInfo.getName());				
				}else{

					result.put("message", "error");
				}
			}
			else{
				result.put("message", "error");
			}
		} catch (Exception e) {

			result.put("message", "error");
		}
		return result;

	}

	/**
	 * 通过id获取对应的城市信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/findForCityById")
	public @ResponseBody ModelMap findForCityById(@RequestParam("id") String id)
	{
		ModelMap result=new ModelMap();
		try {
			City city=this.cityService.findCityById(id);
			if(null!=city)
			{
				result.put("message", "success");
				result.put("id", city.getId());	
				result.put("name", city.getName());	
			}else{
				result.put("message", "error");
			}
		} catch (Exception e) {

			result.put("message", "error");
		}
		return result;

	}

	/**
	 * 获取热门城市信息 
	 * @param entity
	 * @return
	 */
	@RequestMapping(value="/findHotCity")
	public @ResponseBody ModelMap findHotCity()
	{
		ModelMap result=new ModelMap();			
		City city=new City();
		city.setHotCity(0);
		List<Map<String,Object>> list=this.cityService.findHotCity(city);
		if(list.size()>0)
		{
			result.put("message", "success");
			result.put("data", JSONArray.fromObject(list));			
		}
		else
		{
			result.put("message", "error");
		}
		return result;

	}
}
