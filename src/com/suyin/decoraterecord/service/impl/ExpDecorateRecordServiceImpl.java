package com.suyin.decoraterecord.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.suyin.decoraterecord.mapper.ExpDecorateRecordMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.suyin.system.util.Md5Util;

import java.util.*;
import com.suyin.decoraterecord.model.*;
import com.suyin.decoraterecord.service.*;



@Transactional
@Service("expDecorateRecordService")
public class ExpDecorateRecordServiceImpl implements ExpDecorateRecordService{

    private final static Logger log=Logger.getLogger(ExpDecorateRecordServiceImpl.class);
    
    @Autowired
    private ExpDecorateRecordMapper expDecorateRecordMapper; 

    /**
     * 新增信息
     * @param entity
     * @return
     */
    @Override
    public Integer addExpDecorateRecord(ExpDecorateRecord entity){
        Integer result=0;
        try {

            if(entity==null){
                return result;
            }else{
                result = expDecorateRecordMapper.addExpDecorateRecord(entity);
            }

        } catch (Exception e) {

            new RuntimeException();
        }
        return result;

    }

    /**
     * 修改信息
     * @param entity
     * @return
     */
    @Override
    public Integer updateExpDecorateRecord(ExpDecorateRecord entity){

        Integer result=0;
        try {
            if(entity==null){

                return result;
            }else{

                result = expDecorateRecordMapper.updateExpDecorateRecord(entity);
            }
        } catch (Exception e) {
            
            log.error("ExpDecorateRecord信息修改异常"+e.getMessage());
            new RuntimeException();
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 删除信息
     * @param id
     * @return
     */
    @Override
    public Integer deleteExpDecorateRecord(String id){
        
        
        return expDecorateRecordMapper.deleteExpDecorateRecord(id);
    }

    /**
     * 查找信息列表
     * @param entity
     * @return
     */
    @Override
    public List<ExpDecorateRecord> findExpDecorateRecord(ExpDecorateRecord entity){
        
        
        return expDecorateRecordMapper.findExpDecorateRecord(entity);
    }

    /**
     * 查找信息列表(分页)
     * @param entity
     * @return
     */
    @Override
    public List<ExpDecorateRecord> findExpDecorateRecordByPage(ExpDecorateRecord entity){
        
        
        return expDecorateRecordMapper.findExpDecorateRecordByPage(entity);
    }

    /**
     * 根据id查询对应的信息
     * @param entity
     * @return
     */
    @Override
    public ExpDecorateRecord findExpDecorateRecordById(ExpDecorateRecord entity){
        
        List<ExpDecorateRecord> list=expDecorateRecordMapper.findExpDecorateRecord(entity);
        return list!=null&&!list.isEmpty()?list.get(0):null;
    }
}
