package com.suyin.activity.service;

import java.util.Map;

import com.suyin.activity.model.Activity;
import com.suyin.activity.model.PartDTO;




public interface ActivityService{
	
	
	/**
	 * 访问数++
	 * @return
	 */
	public Integer numberplus(Map<String,Object>params);

    /**
     * 根据id查询对应的信息
     * @param entity
     * @return
     */
    public Activity findActivityById(Integer id);
    
    public PartDTO getStatistics();
}
