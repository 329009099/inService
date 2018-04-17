package com.suyin.exporder.mapper;


import java.util.List;

import java.util.*;
import com.suyin.exporder.model.*;
import com.suyin.exporder.service.*;




public interface ExpOrderMapper {

    /**
     * 新增信息
     */
    public Integer addExpOrder(ExpOrder entity);

    /**
     * 修改信息
     */
    public Integer updateExpOrder(ExpOrder entity);
    /**
     * 根据id删除单条信息
     * 
     */
    public Integer deleteExpOrder(String id);
    /**
     * 批量删除
     */
    public Integer deleteExpOrder(String[] id); 

    /**
     * 查询列表
     */
    public List<ExpOrder> findExpOrder(ExpOrder entity);

    /**
     * 查询列表分页  
     */
    public List<ExpOrder> findExpOrderByPage(ExpOrder entity);

}
