package com.suyin.nouser.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.suyin.system.model.Page;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ExperienceOrder {

	private Integer orderId;
	private Integer orderType; //两个赚是0，其他四个是1
	private Integer showType;  //区分两个赚，还有其他四个赚内部的内容
	private String image;  //图片地址
	private String title;    //活动显示的题图
	private String status;
	private String solidGold;
	private String maxGold;
	private String minGold;
	private String userId;
	private Integer expId;
	
	private String shareImgUrl;
	private String shareTitle;
	private String shareUrl;
	
	//产品详情id
	private String detaiId;
	
	public final String getShareImgUrl() {
		return shareImgUrl;
	}
	public final void setShareImgUrl(String shareImgUrl) {
		this.shareImgUrl = shareImgUrl;
	}
	public final String getShareTitle() {
		return shareTitle;
	}
	public final void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}
	public final String getShareUrl() {
		return shareUrl;
	}
	public final void setShareUrl(String shareUrl) {
		this.shareUrl = shareUrl;
	}
	private String qmType;
	
	public final String getQmType() {
		return qmType;
	}
	public final void setQmType(String qmType) {
		this.qmType = qmType;
	}
	public final Integer getExpId() {
		return expId;
	}
	public final void setExpId(Integer expId) {
		this.expId = expId;
	}
	private Page page;
	
	public final Integer getShowType() {
		return showType;
	}
	public final void setShowType(Integer showType) {
		this.showType = showType;
	}
	public final Page getPage() {
		return page;
	}
	public final void setPage(Page page) {
		this.page = page;
	}
	public final String getUserId() {
		return userId;
	}
	public final void setUserId(String userId) {
		this.userId = userId;
	}
	public final String getSolidGold() {
		return solidGold;
	}
	public final void setSolidGold(String solidGold) {
		this.solidGold = solidGold;
	}
	public final Integer getOrderId() {
		return orderId;
	}
	public final void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public final Integer getOrderType() {
		return orderType;
	}
	public final void setOrderType(Integer orderType) {
		this.orderType = orderType;
	}
	public final String getImage() {
		return image;
	}
	public final void setImage(String image) {
		this.image = image;
	}
	public final String getTitle() {
		return title;
	}
	public final void setTitle(String title) {
		this.title = title;
	}
	public final String getStatus() {
		return status;
	}
	public final void setStatus(String status) {
		this.status = status;
	}
	public final String getMaxGold() {
		return maxGold;
	}
	public final void setMaxGold(String maxGold) {
		this.maxGold = maxGold;
	}
	public final String getMinGold() {
		return minGold;
	}
	public final void setMinGold(String minGold) {
		this.minGold = minGold;
	}
    public String getDetaiId()
    {
        return detaiId;
    }
    public void setDetaiId(String detaiId)
    {
        this.detaiId = detaiId;
    }
	
}
