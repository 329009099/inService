package com.suyin.decorate.service;

import java.util.List;
import java.util.Map;

public interface RankService {

	/**
	 * 通过openid查询名次
	 * @param openId
	 * @return
	 */
	public Integer getMyRankInfo(String openId);
	
	/**
	 * 
	 * @param condition
	 * @return
	 */
	List<Map<String,Object>> findAllRankInfo( Map<String,Object> condition);
}
