package com.suyin.decoratebuyorder.mapper;


import java.util.*;

import com.suyin.decoratebuyorder.model.*;
import com.suyin.decoratebuyorder.service.*;




public interface DecorateBuyOrderMapper {
	/**
	 * 根据订单ID查询信息
	 * @param id
	 * @return
	 */
	DecorateBuyOrder findOrderByIdInfo(String id);
	/**
	 * 根据订单编号查询
	 * @param tradeNo
	 * @return
	 */
	 DecorateBuyOrder findByOrderNumInfo(String tradeNo);
	/**
	 * 更新订单状态
	 * @param tradeNo
	 */
	public void orderUpdateState(String tradeNo);
    /**
     * 新增信息
     */
    public Integer addDecorateBuyOrder(DecorateBuyOrder entity);

    /**
     * 修改信息
     */
    public Integer updateDecorateBuyOrder(DecorateBuyOrder entity);
    /**
     * 根据id删除单条信息
     * 
     */
    public Integer deleteDecorateBuyOrder(String id);
    /**
     * 批量删除
     */
    public Integer deleteDecorateBuyOrder(String[] id); 

    /**
     * 查询列表
     */
    public List<DecorateBuyOrder> findDecorateBuyOrder(DecorateBuyOrder entity);

    /**
     * 查询列表分页  
     */
    public List<DecorateBuyOrder> findDecorateBuyOrderByPage(Map<String,Object> entity);

}
