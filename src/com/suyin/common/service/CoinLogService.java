package com.suyin.common.service;

import java.util.List;

import com.suyin.common.model.CashLog;
import com.suyin.common.model.CoinLog;

public interface CoinLogService {


	/**添加一条记录，需要 content，userId,goldCoin,direction*/
	public int addCoinLog(CoinLog log);
	
	/**查找用户的金币变化的log*/
	public List<CoinLog> findCoinLogByUserByPage(CoinLog log);

	public List<CashLog> findCashLogByUserByPage(CashLog log);

}
