package com.suyin.actrecord.mapper;

import java.util.Map;

import com.suyin.actrecord.model.ActRecord;




public interface ActRecordMapper {
	
	/**
	 * 0:正常活动中 可以参与
	 * 1:开始时间没到
	 * 2:已经结束了
	 * 9:参与一场
	 * 查询状态
	 * @return
	 */
	public String findActstate();
	
	/**
	 * 根据openId查询用户今天是否参与过投票
	 * @param id
	 * @return
	 */
	public ActRecord findIsActRecord(Map<String,Object> params);
	
	/**
	 * 
	 * @param act
	 * @return
	 */
	public Integer insertJoinAct(ActRecord act);
	
	
   

}
