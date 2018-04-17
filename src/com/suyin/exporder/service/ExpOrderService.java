package com.suyin.exporder.service;

import java.util.List;

import java.util.*;
import com.suyin.exporder.model.*;
import com.suyin.exporder.service.*;




public interface ExpOrderService{

    /**
     * 新增信息
     * @param entity
     * @return
     */
    public Integer addExpOrder(ExpOrder entity);

    /**
     * 修改信息
     * @param entity
     * @return
     */
    public Integer updateExpOrder(ExpOrder entity);

    /**
     * 删除信息
     * @param id
     * @return
     */
    public Integer deleteExpOrder(String id);

    /**
     * 查找信息列表
     * @param entity
     * @return
     */
    public List<ExpOrder> findExpOrder(ExpOrder entity);

    /**
     * 查找信息列表(分页)
     * @param entity
     * @return
     */
    public List<ExpOrder> findExpOrderByPage(ExpOrder entity);

    /**
     * 根据id查询对应的信息
     * @param entity
     * @return
     */
    public ExpOrder findExpOrderById(ExpOrder entity);
}
