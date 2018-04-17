package com.suyin.activity.mapper;


import java.util.List;
import java.util.Map;

import com.suyin.activity.model.Activity;
import com.suyin.activity.model.PartDTO;

public interface ActivityMapper {

	/**
	 * 访问次数++
	 * @return
	 */
	public Integer updateNumberplus(Map<String,Object>params);
    /**
     * 新增信息
     */
    public Integer addActivity(Activity entity);

    /**
     * 修改信息
     */
    public Integer updateActivity(Activity entity);
    /**
     * 根据id删除单条信息
     * 
     */
    public Integer deleteActivity(String id);
    /**
     * 批量删除
     */
    public Integer deleteActivity(String[] id); 

    /**
     * 查询列表
     */
    public List<Activity> findActivity(Activity entity);

    /**
     * 查询列表分页  
     */
    public List<Activity> findActivityByPage(Activity entity);
    
    public Activity findActivityById(Activity entity);
    
    public PartDTO getStatistics();
}
