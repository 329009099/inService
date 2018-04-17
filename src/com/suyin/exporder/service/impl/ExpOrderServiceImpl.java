package com.suyin.exporder.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.suyin.exporder.mapper.ExpOrderMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.suyin.system.util.Md5Util;

import java.util.*;
import com.suyin.exporder.model.*;
import com.suyin.exporder.service.*;



@Transactional
@Service("ExpOrderService")
public class ExpOrderServiceImpl implements ExpOrderService{

    private final static Logger log=Logger.getLogger(ExpOrderServiceImpl.class);
    
    @Autowired
    private ExpOrderMapper ExpOrderMapper; 

    /**
     * 新增信息
     * @param entity
     * @return
     */
    @Override
    public Integer addExpOrder(ExpOrder entity){
        Integer result=0;
        try {

            if(entity==null){
                return result;
            }else{
                result = ExpOrderMapper.addExpOrder(entity);
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
    public Integer updateExpOrder(ExpOrder entity){

        Integer result=0;
        try {
            if(entity==null){

                return result;
            }else{

                result = ExpOrderMapper.updateExpOrder(entity);
            }
        } catch (Exception e) {
            
            log.error("ExpOrder信息修改异常"+e.getMessage());
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
    public Integer deleteExpOrder(String id){
        
        
        return ExpOrderMapper.deleteExpOrder(id);
    }

    /**
     * 查找信息列表
     * @param entity
     * @return
     */
    @Override
    public List<ExpOrder> findExpOrder(ExpOrder entity){
        
        
        return ExpOrderMapper.findExpOrder(entity);
    }

    /**
     * 查找信息列表(分页)
     * @param entity
     * @return
     */
    @Override
    public List<ExpOrder> findExpOrderByPage(ExpOrder entity){
        
        
        return ExpOrderMapper.findExpOrderByPage(entity);
    }

    /**
     * 根据id查询对应的信息
     * @param entity
     * @return
     */
    @Override
    public ExpOrder findExpOrderById(ExpOrder entity){
        
        List<ExpOrder> list=ExpOrderMapper.findExpOrder(entity);
        return list!=null&&!list.isEmpty()?list.get(0):null;
    }
}
