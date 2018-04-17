package com.suyin.exp.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suyin.exp.mapper.ExperienceMapper;
import com.suyin.exp.model.Experience;
import com.suyin.exp.service.ExperienceService;

@Service
public class ExperienceServiceImpl implements ExperienceService {
    private Logger log=Logger.getLogger(ExperienceServiceImpl.class);

    @Autowired
    private ExperienceMapper experienceMapper;


    /**
     * 根据id获取活动详情
     */
    @Override
    public Experience findExpById(Experience exp) {
        try {
            return this.experienceMapper.findExpById(exp);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }


    /**
     * 查询活动列表
     */
    @Override
    public List<Map<String, Object>> findExperienceByTypeByPage(Experience exp)
    {
        // TODO Auto-generated method stub
        return experienceMapper.findExperienceByTypeByPage(exp);
    }


    /**
     * 查询活动列表
     */
    @Override
    public List<Map<String, Object>> findExperienceByTypeByPageMap(Map<String, Object> exp)
    {
        // TODO Auto-generated method stub
        return experienceMapper.findExperienceByTypeByPageMap(exp);
    }

}
