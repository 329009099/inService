package com.suyin.simple.model;

import java.io.Serializable;
import java.util.Map;

public class DataModel implements Serializable{
	
	private static final long serialVersionUID = 5756712793481918631L;
	
	private Integer id;
	private Map<String,Object> data;
	
	private String userPhone;
	private Integer coin;
	
	
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public Integer getCoin() {
		return coin;
	}
	public void setCoin(Integer coin) {
		this.coin = coin;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
}
