package com.suyin.expzhuan.model;

public class ExperienceTaskOrder {

	private java.lang.Integer orderId;
	private java.lang.Integer userId;
	private Integer expId;
	private Integer regtype;
	private Integer moduleType;
	private Integer status;
	private Integer totalGold;
	private Integer browseCount;
	private String imageUrl;
	private String userPhone;
	
	
	public final String getUserPhone() {
		return userPhone;
	}
	public final void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public java.lang.Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(java.lang.Integer orderId) {
		this.orderId = orderId;
	}
	public java.lang.Integer getUserId() {
		return userId;
	}
	public ExperienceTaskOrder setUserId(java.lang.Integer userId) {
		this.userId = userId;
		return this;
	}
	public Integer getExpId() {
		return expId;
	}
	public ExperienceTaskOrder setExpId(Integer expId) {
		this.expId = expId;
		return this;
	}
	public Integer getRegtype() {
		return regtype;
	}
	public void setRegtype(Integer regtype) {
		this.regtype = regtype;
	}
	public Integer getModuleType() {
		return moduleType;
	}
	public ExperienceTaskOrder setModuleType(Integer moduleType) {
		this.moduleType = moduleType;
		return this;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getTotalGold() {
		return totalGold;
	}
	public void setTotalGold(Integer totalGold) {
		this.totalGold = totalGold;
	}
	public Integer getBrowseCount() {
		return browseCount;
	}
	public void setBrowseCount(Integer browseCount) {
		this.browseCount = browseCount;
	}
	
}
