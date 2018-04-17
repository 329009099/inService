package com.suyin.postion.service;

import java.util.List;
import java.util.Map;

import com.suyin.postion.model.City;

public interface CityService {

	/**
	 * 获取城市名
	 * @param map
	 * @return
	 */
	public String findCityName(Map<String,Object> map);
	
	/**
	 * 根据城市名获取对应的城市信息
	 * @param name
	 * @return
	 */
	public City findCityByName(String name);
	
	/**
	 * 根据id获取对应的城市信息
	 * @param id
	 */
	public City findCityById(String id);
	
	/**
	 * 获取热门城市信息
	 * @param entity
	 * @return
	 */
	public List<Map<String,Object>> findHotCity(City entity);
}
