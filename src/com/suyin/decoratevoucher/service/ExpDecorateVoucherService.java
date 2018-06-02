package com.suyin.decoratevoucher.service;

import java.util.*;

import com.suyin.decoratevoucher.model.*;
import com.suyin.decoratevoucher.service.*;




public interface ExpDecorateVoucherService{
	
	/**
	 * key orderId
	 * key vourcheCode
	 * 根据券号修改支付状态
	 * @param voucheCode
	 */
	void updateVoucherPayState(Map<String,Object> params);
	/**
	 * 保存券信息
	 * @param voucherUser
	 */
	void saveUserVoucher(ExpDecorateUserVoucher voucherUser);
	/**
	 * 根据OPENID查询我的券
	 * @param decorateUserVoucher
	 * @return
	 */
	List<ExpDecorateUserVoucher>findExpDecorateUserVoucherByPage(Map<String,Object> condition);
	/**
	 * 根据ID查询对象信息
	 * @param id
	 * @return
	 */
	 public ExpDecorateVoucher findDetial(String id);
	 /**
	  * 根据ID更新券的数量
	  * @param id
	  */
	 public void updateRemNum(Integer id);
    /**
     * 查找信息列表(分页)
     * @param entity
     * @return
     */
    public List<ExpDecorateVoucher> findExpDecorateVoucherByPage( Map<String,Object> condition);

    /**
     * 根据id查询对应的信息
     * @param entity
     * @return
     */
    public ExpDecorateVoucher findExpDecorateVoucherById(ExpDecorateVoucher entity);
}
