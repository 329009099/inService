package com.suyin.participator.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suyin.participator.mapper.ParticipatorMapper;
import com.suyin.participator.model.Participator;
import com.suyin.participator.service.ParticipatorService;

@Transactional
@Service("ParticipatorService")
public class ParticipatorServiceImpl implements ParticipatorService{
    
    @Autowired
    private ParticipatorMapper ParticipatorMapper; 

	@Override
	public List<Map<String, Object>> findParticipatorList(Map<String, Object> condition) {

		return this.ParticipatorMapper.findParticipatorListByPage(condition);
	}

	@Override
	public Participator findParticipatorDetailById(int id) {
		return this.ParticipatorMapper.findParticipatorDetailById(id);
	}
	
	@Override
	public List<Map<String, Object>> findRanKingList(Map<String, Object> condition) {

		return this.ParticipatorMapper.findRanKingListByPage(condition);
	}

	@Override
	public Participator getPoorVotes(int id) {
		Long votes = 0L;
		Participator participator = new Participator();
		Participator participator1=new Participator();
		Participator participator2=new Participator();
		List<Participator> ranKingList  = this.ParticipatorMapper.getRankingList();
			for (int i = 0; i < ranKingList.size(); i++) {	
				if (ranKingList.get(i).getId()==id) {				
					if((i-1)<0){
						participator.setRankingStatus("0");
						participator1 = ranKingList.get(i);
						participator.setPoorVotes(participator1.getVotesNumber());
					}else{
						participator1 = ranKingList.get(i);
					    participator2 = ranKingList.get(i-1);
						votes = (participator2.getVotesNumber() - participator1.getVotesNumber());
						participator.setPoorVotes(votes);
						participator.setRanking(i+1);
						participator.setRankingStatus("1");
					}
				}
			}
		return participator;
	}

	@Override
	public Integer getRanking(int id) {
		List<Participator> ranKingList  = this.ParticipatorMapper.getRankingList();
		for (int i = 0; i < ranKingList.size(); i++) {
			if (ranKingList.get(i).getId() == id) {
				return i+1;
			}
		}
		return 1;
	}

}
