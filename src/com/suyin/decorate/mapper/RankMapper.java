package com.suyin.decorate.mapper;

import java.util.List;
import java.util.Map;

public interface RankMapper {

    /**
	 * 通过openid查询排名
	 * @param openId
	 * @return
	 */
	public Integer getMyRankInfo(String openId);
	
	/**
	 * 获取所有排名列表
	 * @param exp
	 * @return
	 */
    public List<Map<String,Object>> findAllRanListByPage(Map<String, Object> exp);
    /**
     * 根据类型查询排名信息TYPE 0人气 1体验2签单
     * @return
     */
    public List<Map<String,Object>>findByTypeAllRankList(Map<String,Object>params);

}
