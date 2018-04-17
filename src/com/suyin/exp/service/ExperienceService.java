package com.suyin.exp.service;

import java.util.List;
import java.util.Map;

import com.suyin.exp.model.Experience;

public interface ExperienceService {


    public List<Map<String,Object>> findExperienceByTypeByPage(Experience exp);

    /**
     * 查询列表Map形式
     * @param exp
     * @return 
     * @see
     */
    public List<Map<String,Object>> findExperienceByTypeByPageMap(Map<String, Object> exp);

    /**
     * 根据id获取活动信息
     * @param exp
     * @return
     */
    public Experience findExpById(Experience exp);
    
 
}
