package com.suyin.postion.mapper;


import java.util.List;
import java.util.Map;

import com.suyin.postion.model.City;

public interface CityMapper {

	/**
	 * 根据城市名获取对应信息
	 */
	public City findCityByName(String name);
	
	/**
	 * 通过id获取对应的信息
	 */
	public City findCityById(String id);
	
	/**
	 * 获取热门城市信息
	 */
	public List<Map<String,Object>> findHotCity(City entity);
}
