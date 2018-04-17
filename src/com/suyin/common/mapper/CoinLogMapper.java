package com.suyin.common.mapper;

import java.util.List;
import java.util.Map;

import com.suyin.common.model.CashLog;
import com.suyin.common.model.CoinLog;
import com.suyin.common.model.CoinTeller;

/**
 * 金币变化的log
 * @author Administrator
 *
 */
public interface CoinLogMapper {

	/**添加一条记录，需要 content，userId,goldCoin,direction*/
	public int addCoinLog(CoinLog log);
	
	/**查找用户的金币变化的log*/
	public List<CoinLog> findCoinLogByUserByPage(CoinLog log);
	
	/**用户提取金币的申请记录 */
	public int addCoinTeller(CoinTeller teller);

	public List<CashLog> findCashLogByUserByPage(CashLog log);
	
	/**
	 * 添加钱包记录
	 */
	public int addCashLog(CashLog cashLog);
	
	
}
