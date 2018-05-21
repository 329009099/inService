package com.suyin.decoratevoucher.mapper;


import java.util.*;

import com.suyin.decoratevoucher.model.*;
import com.suyin.decoratevoucher.service.*;




public interface ExpDecorateVoucherMapper {
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
