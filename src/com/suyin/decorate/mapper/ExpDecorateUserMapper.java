package com.suyin.decorate.mapper;


import java.util.List;
import java.util.Map;

import com.suyin.decorate.model.ExpDecorateUser;




public interface ExpDecorateUserMapper {
	
	/**
	 * 
	 * 根据openID修改邀请数人气数
	 * 每个点击分享页面的都可以是人气数
	 * @param openId
	 * @return
	 */
    public Integer updateUserUseNum(String openId);

	/**
	 * 根据openId修改状态
	 * @param openId
	 * @return
	 */
    public Integer updateExpDecorateUserByOpenId(String openId);
	
	/**
	 * 通过用户ID和openId查询用户信息
	 * @param userId
	 * @param openId
	 * @return
	 */
	public ExpDecorateUser findUserInfoByUserIdOrOpenId(Map<String,Object> params);

	/**
	 * wxService初始化信息保存
	 * @param entity
	 * @return
	 */
    public Integer initSaveDecorateUser(ExpDecorateUser entity);

    /**
     * 新增信息
     */
    public Integer addExpDecorateUser(ExpDecorateUser entity);

    /**
     * 修改信息
     */
    public Integer updateExpDecorateUser(ExpDecorateUser entity);
    
    /**
     * 通过Openid修改余额
     * @param entity
     * @return
     */
    public Integer updateBalancePriceByOpendId(ExpDecorateUser entity);
    /**
     * 根据id删除单条信息
     * 
     */
    public Integer deleteExpDecorateUser(String id);
    /**
     * 批量删除
     */
    public Integer deleteExpDecorateUser(String[] id); 

    /**
     * 查询列表
     */
    public List<ExpDecorateUser> findExpDecorateUser(ExpDecorateUser entity);

    /**
     * 查询列表分页  
     */
    public List<ExpDecorateUser> findExpDecorateUserByPage(ExpDecorateUser entity);

}
