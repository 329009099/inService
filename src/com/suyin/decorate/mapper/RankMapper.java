package com.suyin.decorate.mapper;

import java.util.List;
import java.util.Map;

public interface RankMapper {

/*	*//**
	 * 通过openid查询排名
	 * @param openId
	 * @return
	 *//*
	public Integer getMyRankInfo(String openId);*/
	
	/**
	 * 获取所有排名列表
	 * @param exp
	 * @return
	 */
    public List<Map<String,Object>> findAllRanListByPage(Map<String, Object> exp);

}
