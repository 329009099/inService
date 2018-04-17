package com.suyin.participator.mapper;


import java.util.List;
import java.util.Map;

import com.suyin.participator.model.Participator;

public interface ParticipatorMapper {
	
	/**
	 * 参与后更新当前选手的票数
	 * @param id
	 * @return
	 */
	public Integer updatePartNumber(int id);
 
    public List<Map<String,Object>> findParticipatorListByPage(Map<String, Object> exp);
    
  
    public Participator findParticipatorDetailById(Integer id);

    public List<Map<String,Object>> findRanKingListByPage(Map<String, Object> exp);
    
    public List<Participator> getRankingList();
  
}
