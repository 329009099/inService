package com.suyin.decorate.service;

import java.util.List;

import com.suyin.decorate.model.ExpDecorateUser;




public interface ExpDecorateUserService{
	/**
	 * 通过用户ID和openId查询用户信息
	 * @param userId
	 * @param openId
	 * @return
	 */
	public ExpDecorateUser findUserInfoByUserIdOrOpenId(String userId,String openId);

	
	/**
	 * wxService 保存用户信息
	 * openid
	 * headimg
	 * nickName
	 * @param entity
	 * @return
	 */
	public Integer initSaveDecorateUser(ExpDecorateUser entity);
    /**
     * 新增信息
     * @param entity
     * @return
     */
    public Integer addExpDecorateUser(ExpDecorateUser entity);

    /**
     * 修改信息
     * @param entity
     * @return
     */
    public Integer updateExpDecorateUser(ExpDecorateUser entity);
    
    /**
     * 通过Openid修改余额
     * @param entity
     * @return
     */
    public Integer updateBalancePriceByOpendId(ExpDecorateUser entity);

    /**
     * 提现创建订单
     * @param entity
     * @return
     */
    public Integer withdrawCreateOrder(ExpDecorateUser entity,String withdrawPrice);

    /**
     * 删除信息
     * @param id
     * @return
     */
    public Integer deleteExpDecorateUser(String id);

    /**
     * 查找信息列表
     * @param entity
     * @return
     */
    public List<ExpDecorateUser> findExpDecorateUser(ExpDecorateUser entity);

    /**
     * 查找信息列表(分页)
     * @param entity
     * @return
     */
    public List<ExpDecorateUser> findExpDecorateUserByPage(ExpDecorateUser entity);

    /**
     * 根据id查询对应的信息
     * @param entity
     * @return
     */
    public ExpDecorateUser findExpDecorateUserById(ExpDecorateUser entity);
}
