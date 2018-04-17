package com.suyin.expshare.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.suyin.expshare.mapper.ExpShareMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suyin.system.util.Md5Util;

import java.util.*;

import com.suyin.expshare.model.*;
import com.suyin.expshare.service.*;



@Transactional
@Service("ExpShareService")
public class ExpShareServiceImpl implements ExpShareService{

    private final static Logger log=Logger.getLogger(ExpShareServiceImpl.class);

    @Autowired
    private ExpShareMapper ExpShareMapper; 

    /**
     * 新增信息
     * @param entity
     * @return
     */
    @Override
    public Integer addExpShare(ExpShare entity){
        Integer result=0;
        try {

            if(entity==null){
                return result;
            }else{
                result = ExpShareMapper.addExpShare(entity);
            }

        } catch (Exception e) {

            new RuntimeException();
        }
        return result;

    }

    /**
     * 修改信息
     */
    @Override
    public Integer updateExpShare(ExpShare entity)
    {       
        Integer result=0;
        // TODO Auto-generated method stub
        try
        {   
            if(entity==null){
                return result;
            }else{
                return ExpShareMapper.updateExpShare(entity);
            }
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            new RuntimeException();
        }
        return result;
    }

	@Override
	public List<Map<String, Object>> findRankForShareByPage(
			Map<String, Object> condition) {
		
		return this.ExpShareMapper.findRankForShareByPage(condition);
	}

	@Override
	public Map<String, Object> findRankForMySelf(Map<String, Object> condition) {
		return  this.ExpShareMapper.findRankForMySelf(condition);
	}

    /* (non-Javadoc)
     * @see com.suyin.expshare.service.ExpShareService#findFalseRankForShareByPage(java.util.Map)
     */
    @Override
    public List<Map<String, Object>> findFalseRankForShareByPage(Map<String, Object> condition)
    {
        // TODO Auto-generated method stub
        return this.ExpShareMapper.findFalseRankForShareByPage(condition);
    }

    /* (non-Javadoc)
     * @see com.suyin.expshare.service.ExpShareService#findFalseRankForMySelf(java.util.Map)
     */
    @Override
    public Map<String, Object> findFalseRankForMySelf(Map<String, Object> condition)
    {
        // TODO Auto-generated method stub
        return this.ExpShareMapper.findFalseRankForMySelf(condition);
    }


}
