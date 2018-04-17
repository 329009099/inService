package com.suyin.activity.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suyin.activity.mapper.ActivityMapper;
import com.suyin.activity.model.Activity;
import com.suyin.activity.model.PartDTO;
import com.suyin.activity.service.ActivityService;



@Transactional
@Service("activityService")
public class ActivityServiceImpl implements ActivityService{

    @Autowired
   private ActivityMapper ActivityMapper;
    /**
     * 根据id查询对应的信息
     * @param entity
     * @return
     */
    @Override
    public Activity findActivityById(Integer id){
    	Activity entity = new Activity();
    	entity.setId(id);
        Activity activity=ActivityMapper.findActivityById(entity);
        return activity;
    }
	@Override
	public PartDTO getStatistics() {
		
		return this.ActivityMapper.getStatistics();
	}
	@Override
	public Integer numberplus(Map<String,Object>params) {
		
		return this.ActivityMapper.updateNumberplus(params);				
	}
}
