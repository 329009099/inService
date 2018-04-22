package com.suyin.decoraterecord.mapper;


import java.util.*;

import com.suyin.decoraterecord.model.*;
import com.suyin.decoraterecord.service.*;




public interface ExpDecorateRecordMapper {
	
	/**
	 * 查询我的邀请
	 * @param entity
	 * @return
	 */
	public List<Map<String, Object>> findInviteDecorateByPage(Map<String,Object> condition);
	/**
	 * 根据发起者和接收者openid 查询是否存在记录
	 * @param params
	 * @return
	 */
	public ExpDecorateRecord findExpRecordByPublisAndReviewOpenid(Map<String,Object>parmas);
    /**
     * 新增信息
     */
    public Integer addExpDecorateRecord(ExpDecorateRecord entity);

    /**
     * 修改信息
     */
    public Integer updateExpDecorateRecord(ExpDecorateRecord entity);
    /**
     * 根据id删除单条信息
     * 
     */
    public Integer deleteExpDecorateRecord(String id);
    /**
     * 批量删除
     */
    public Integer deleteExpDecorateRecord(String[] id); 

    /**
     * 查询列表
     */
    public List<ExpDecorateRecord> findExpDecorateRecord(ExpDecorateRecord entity);

    /**
     * 查询列表分页  
     */
    public List<ExpDecorateRecord> findExpDecorateRecordByPage(ExpDecorateRecord entity);

}
