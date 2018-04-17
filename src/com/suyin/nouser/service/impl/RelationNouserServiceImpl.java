package com.suyin.nouser.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.suyin.nouser.mapper.RelationNouserMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.suyin.system.util.Md5Util;

import java.util.*;
import com.suyin.nouser.model.*;
import com.suyin.nouser.service.*;



@Transactional
@Service("RelationNouserService")
public class RelationNouserServiceImpl implements RelationNouserService{

    private final static Logger log=Logger.getLogger(RelationNouserServiceImpl.class);
    
    @Autowired
    private RelationNouserMapper RelationNouserMapper; 

    /**
     * 新增信息
     * @param entity
     * @return
     */
    @Override
    public Integer addRelationNouser(RelationNouser entity){
        Integer result=0;
        try {

            if(entity==null){
                return result;
            }else{
                result = RelationNouserMapper.addRelationNouser(entity);
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
    public Integer updateRelationNouser(RelationNouser entity){

        Integer result=0;
        try {
            if(entity==null){

                return result;
            }else{

                result = RelationNouserMapper.updateRelationNouser(entity);
            }
        } catch (Exception e) {
            
            log.error("RelationNouser信息修改异常"+e.getMessage());
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
    public Integer deleteRelationNouser(String id){
        
        
        return RelationNouserMapper.deleteRelationNouser(id);
    }

    /**
     * 查找信息列表
     * @param entity
     * @return
     */
    @Override
    public List<RelationNouser> findRelationNouser(RelationNouser entity){
        
        
        return RelationNouserMapper.findRelationNouser(entity);
    }

    /**
     * 查找信息列表(分页)
     * @param entity
     * @return
     */
    @Override
    public List<RelationNouser> findRelationNouserByPage(RelationNouser entity){
        
        
        return RelationNouserMapper.findRelationNouserByPage(entity);
    }

    /**
     * 根据id查询对应的信息
     * @param entity
     * @return
     */
    @Override
    public RelationNouser findRelationNouserById(RelationNouser entity){
        
        List<RelationNouser> list=RelationNouserMapper.findRelationNouser(entity);
        return list!=null&&!list.isEmpty()?list.get(0):null;
    }
}
