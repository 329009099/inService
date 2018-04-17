package com.suyin.nouser.service;

import java.util.List;

import java.util.*;
import com.suyin.nouser.model.*;
import com.suyin.nouser.service.*;




public interface RelationNouserService{

    /**
     * 新增信息
     * @param entity
     * @return
     */
    public Integer addRelationNouser(RelationNouser entity);

    /**
     * 修改信息
     * @param entity
     * @return
     */
    public Integer updateRelationNouser(RelationNouser entity);

    /**
     * 删除信息
     * @param id
     * @return
     */
    public Integer deleteRelationNouser(String id);

    /**
     * 查找信息列表
     * @param entity
     * @return
     */
    public List<RelationNouser> findRelationNouser(RelationNouser entity);

    /**
     * 查找信息列表(分页)
     * @param entity
     * @return
     */
    public List<RelationNouser> findRelationNouserByPage(RelationNouser entity);

    /**
     * 根据id查询对应的信息
     * @param entity
     * @return
     */
    public RelationNouser findRelationNouserById(RelationNouser entity);
}
