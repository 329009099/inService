package com.suyin.exp.mapper;

import java.util.List;
import java.util.Map;

import com.suyin.exp.model.Experience;

public interface ExperienceMapper {


	/**
	 * 根据id获取活动详情
	 * @param exp
	 * @return
	 */
	public Experience findExpById(Experience exp);
	   /**
     * 根据类别各种姿势查找活动（0元试，人气王，金币兑换，抽奖乐）
     * @param exp
     * @return
     */
    public List<Map<String,Object>> findExperienceByTypeByPage(Experience exp);
    
    
    /**
     * map传值 查询（0元试，人气王，金币兑换，抽奖乐）信息 
     * @param exp
     * @return 
     * @see
     */
    public List<Map<String,Object>> findExperienceByTypeByPageMap(Map<String, Object> exp);
}
