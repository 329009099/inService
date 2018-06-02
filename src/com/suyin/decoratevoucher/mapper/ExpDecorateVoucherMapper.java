package com.suyin.decoratevoucher.mapper;


import java.util.*;

import com.suyin.decoratevoucher.model.*;
import com.suyin.decoratevoucher.service.*;




public interface ExpDecorateVoucherMapper {
	/**
	 * key orderId
	 * key vourcheCode
	 * 根据券号修改支付状态
	 * @param voucheCode
	 */
	void updateVoucherPayState(Map<String,Object>params);
	/**
	 * 更新数量
	 * @param id
	 */
	public void updateRemNum(Integer id);
	/**
	 * 保存购买的券
	 * @param voucherUser
	 */
	void  saveUserVoucher(ExpDecorateUserVoucher voucherUser);
	
	/**
	 * 根据OPENID查询我的券
	 * @param decorateUserVoucher
	 * @return
	 */
	List<ExpDecorateUserVoucher>findExpDecorateUserVoucherByPage(Map<String,Object> condition);
	/**
	 * 根据ID查询对象
	 * @param id
	 * @return
	 */
	ExpDecorateVoucher findDetial(String id);
    /**
     * 查询列表
     */
    public List<ExpDecorateVoucher> findExpDecorateVoucher(ExpDecorateVoucher entity);

    /**
     * 查询列表分页  
     */
    public List<ExpDecorateVoucher> findExpDecorateVoucherByPage( Map<String,Object> condition);

}
