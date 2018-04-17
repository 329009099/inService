package com.suyin.nouser.mapper;


import java.util.List;

import java.util.*;
import com.suyin.nouser.model.*;
import com.suyin.nouser.service.*;




public interface RelationNouserMapper {

    /**
     * 新增信息
     */
    public Integer addRelationNouser(RelationNouser entity);

    /**
     * 修改信息
     */
    public Integer updateRelationNouser(RelationNouser entity);
    /**
     * 根据id删除单条信息
     * 
     */
    public Integer deleteRelationNouser(String id);
    /**
     * 批量删除
     */
    public Integer deleteRelationNouser(String[] id); 

    /**
     * 查询列表
     */
    public List<RelationNouser> findRelationNouser(RelationNouser entity);

    /**
     * 查询列表分页  
     */
    public List<RelationNouser> findRelationNouserByPage(RelationNouser entity);

}
