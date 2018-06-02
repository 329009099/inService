package com.suyin.decorate.mapper;


import java.util.List;
import java.util.Map;

import com.suyin.decorate.model.ExpDecorateOrder;




public interface ExpDecorateOrderMapper {

	/**
	 * 根据openid查询用户提现记录
	 * @param params
	 * @return
	 */
	public List<ExpDecorateOrder>findUserOrderRecordByPage(Map<String,Object> params);

	/**
	 * 根据openid查询用户是否存在 还未审核通过的订单
	 * @param params
	 * @return
	 */
	public ExpDecorateOrder findUserIsReviewOrderInfo(Map<String,Object>params);
    /**
     * 新增信息
     */
    public Integer addExpDecorateOrder(ExpDecorateOrder entity);

    /**
     * 修改信息
     */
    public Integer updateExpDecorateOrder(ExpDecorateOrder entity);
    /**
     * 根据id删除单条信息
     * 
     */
    public Integer deleteExpDecorateOrder(String id);
    /**
     * 批量删除
     */
    public Integer deleteExpDecorateOrder(String[] id); 

    /**
     * 查询列表
     */
    public List<ExpDecorateOrder> findExpDecorateOrder(ExpDecorateOrder entity);

    /**
     * 查询列表分页  
     */
    public List<ExpDecorateOrder> findExpDecorateOrderByPage(ExpDecorateOrder entity);

}
