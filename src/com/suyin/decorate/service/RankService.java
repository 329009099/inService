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
	  /**
     * 根据类型查询排名信息TYPE 0人气 1体验2签单
     * @return
     */
    public List<Map<String,Object>>findByTypeAllRankList(Map<String,Object>params);
}
