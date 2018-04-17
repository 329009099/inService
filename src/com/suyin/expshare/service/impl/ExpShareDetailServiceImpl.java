package com.suyin.expshare.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.suyin.expshare.mapper.ExpShareDetailMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.suyin.system.util.Md5Util;

import java.util.*;
import com.suyin.expshare.model.*;
import com.suyin.expshare.service.*;



@Transactional
@Service("ExpShareDetailService")
public class ExpShareDetailServiceImpl implements ExpShareDetailService{

    private final static Logger log=Logger.getLogger(ExpShareDetailServiceImpl.class);
    
    @Autowired
    private ExpShareDetailMapper ExpShareDetailMapper; 

    /**
     * 新增信息
     * @param entity
     * @return
     */
    @Override
    public Integer addExpShareDetail(ExpShareDetail entity){
        Integer result=0;
        try {

            if(entity==null){
                return result;
            }else{
                result = ExpShareDetailMapper.addExpShareDetail(entity);
            }

        } catch (Exception e) {

            new RuntimeException();
        }
        return result;

    }

   
}
