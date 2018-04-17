package com.suyin.common.model;

import java.math.BigDecimal;

import com.suyin.system.model.Page;

/**
 * 提现的申请记录
 * @author Administrator
 *
 */
public class CoinTeller {

	private int logId;
	private int userId;
	private int goldCoin;
	private int status;
	private BigDecimal money;
	private String createTime;
	private String alipay;
	private String name;
	private Page page;
	public final Page getPage() {
		return page;
	}
	public final void setPage(Page page) {
		this.page = page;
	}
	
	public final BigDecimal getMoney() {
		return money;
	}
	public final CoinTeller setMoney(BigDecimal money) {
		this.money = money;
		return this;
	}
	public final int getLogId() {
		return logId;
	}
	public final CoinTeller setLogId(int logId) {
		this.logId = logId;
		return this;
	}
	public final int getUserId() {
		return userId;
	}
	public final CoinTeller setUserId(int userId) {
		this.userId = userId;
		return this;
	}
	public final int getGoldCoin() {
		return goldCoin;
	}
	public final CoinTeller setGoldCoin(int goldCoin) {
		this.goldCoin = goldCoin;
		return this;
	}
	public final int getStatus() {
		return status;
	}
	public final CoinTeller setStatus(int status) {
		this.status = status;
		return this;
	}
	public final String getCreateTime() {
		return createTime;
	}
	public final void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
    public String getAlipay()
    {
        return alipay;
    }
    public void setAlipay(String alipay)
    {
        this.alipay = alipay;
    }
    public String getName()
    {
        return name;
    }
    public void setName(String name)
    {
        this.name = name;
    }
	
	
}
