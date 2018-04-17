package com.suyin.postion.service.impl;


import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suyin.postion.mapper.CityMapper;
import com.suyin.postion.model.City;
import com.suyin.postion.service.CityService;
import com.suyin.system.util.HttpPost;


@Transactional
@Service("cityService")
public class CityServiceImpl implements CityService{

	private final static Logger log=Logger.getLogger(CityServiceImpl.class);
	
	@Autowired
	private CityMapper cityMapper;
	
	/**
	 * 通过url解析获取的城市名称
	 * @param name
	 */
	@Override
	public String findCityName(Map<String,Object> map) {

		String requestUrl="http://api.map.baidu.com/geocoder/v2/?ak=E7a3b42cf91d8fcdee2a7592aed4da70&location="+map.get("lat")+","+map.get("lng")+"&output=json";		
		String urlstr=HttpPost.baiDuApiRequest(requestUrl);
		JSONObject json=JSONObject.fromObject(urlstr);
		JSONObject s=JSONObject.fromObject(json.get("result"));
		JSONObject o=JSONObject.fromObject(s.get("addressComponent"));  
		return o.get("city").toString();
				
	}

	/**
	 * 通过id获取对应的城市信息
	 * @param id
	 */
	@Override
	public City findCityById(String id) {
		
		return cityMapper.findCityById(id);
	}

	/**
	 * 通过城市名获取城市信息
	 * @param name
	 */
	@Override
	public City findCityByName(String name) {
		
		return cityMapper.findCityByName(name);
	}

	/**
	 * 获取热门城市信息
	 * @param entity
	 */
	@Override
	public List<Map<String,Object>> findHotCity(City entity) {
		return cityMapper.findHotCity(entity);
	}

}
