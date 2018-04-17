package com.suyin.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suyin.common.mapper.CoinLogMapper;
import com.suyin.common.model.CashLog;
import com.suyin.common.model.CoinLog;
import com.suyin.common.service.CoinLogService;

@Service
public class CoinLogServiceImpl implements CoinLogService {

	@Autowired
	private CoinLogMapper mapper;
	@Override
	public int addCoinLog(CoinLog entity) {
		return this.mapper.addCoinLog(entity);
	}

	@Override
	public List<CoinLog> findCoinLogByUserByPage(
			CoinLog condition) {
		return this.mapper.findCoinLogByUserByPage(condition);
	}

	@Override
	public List<CashLog> findCashLogByUserByPage(CashLog log) {
		return this.mapper.findCashLogByUserByPage(log);
	}

}
