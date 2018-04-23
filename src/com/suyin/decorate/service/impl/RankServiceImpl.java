package com.suyin.decorate.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suyin.decorate.mapper.RankMapper;
import com.suyin.decorate.service.RankService;

@Service
public class RankServiceImpl implements RankService {
	
	@Autowired
	private RankMapper rankMapper;

	@Override
	public Integer getMyRankInfo(String openId) {
		return rankMapper.getMyRankInfo(openId);
	}

	@Override
	public List<Map<String, Object>> findAllRankInfo(Map<String, Object> condition) {
		return this.rankMapper.findAllRanListByPage(condition);
	}

}
