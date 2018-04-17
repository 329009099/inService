package com.suyin.common.model;

import java.math.BigDecimal;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.suyin.system.model.Page;
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CashLog {

	private int logId;
	private int userId;
	private String content;
	private BigDecimal money;
	private String direction;
	private String createTime;
    private int status;
	private Page page;
	public final Page getPage() {
		return page;
	}
	
	public final BigDecimal getMoney() {
		return money;
	}

	public final void setMoney(BigDecimal money) {
		this.money = money;
	}

	public final void setPage(Page page) {
		this.page = page;
	}
	public final int getLogId() {
		return logId;
	}
	public final CashLog setLogId(int logId) {
		this.logId = logId;
		return this;
	}
	public final int getUserId() {
		return userId;
	}
	public final CashLog setUserId(int userId) {
		this.userId = userId;
		return this;
	}
	public final String getContent() {
		return content;
	}
	public final CashLog setContent(String content) {
		this.content = content;
		return this;
	}
	
	public final String getDirection() {
		return direction;
	}
	public final CashLog setDirection(String direction) {
		this.direction = direction;
		return this;
	}
	public final String getCreateTime() {
		return createTime;
	}
	public final void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }
	
}
