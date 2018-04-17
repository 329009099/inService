package com.suyin.nouser.model;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.suyin.system.model.Page;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class ExperienceVouch {

    private String vouCode;
    private Integer orderId;
    private Integer userId;
    private String status;  //0:未消费 1：已消费,2:过期
    private String validity;
    private String title;	
    private String detailId;
    private String proId;
    private String proName;
    private String memberId;
    private String type;//活动类型
    private String isDiscuss;//是否评价
    private Page page;

    
    public String getIsDiscuss() {
		return isDiscuss;
	}
	public void setIsDiscuss(String isDiscuss) {
		this.isDiscuss = isDiscuss;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public final Page getPage() {
        return page;
    }
    public final void setPage(Page page) {
        this.page = page;
    }
    public final String getVouCode() {
        return vouCode;
    }
    public final void setVouCode(String vouCode) {
        this.vouCode = vouCode;
    }
    public final Integer getOrderId() {
        return orderId;
    }
    public final void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public final Integer getUserId() {
        return userId;
    }
    public final void setUserId(Integer userId) {
        this.userId = userId;
    }
    public final String getStatus() {
        return status;
    }
    public final void setStatus(String status) {
        this.status = status;
    }
    public final String getValidity() {
        return validity;
    }
    public final void setValidity(String validity) {
        this.validity = validity;
    }
    public final String getTitle() {
        return title;
    }
    public final void setTitle(String title) {
        this.title = title;
    }
    public String getDetailId()
    {
        return detailId;
    }
    public void setDetailId(String detailId)
    {
        this.detailId = detailId;
    }
    public String getProId()
    {
        return proId;
    }
    public void setProId(String proId)
    {
        this.proId = proId;
    }
    public String getProName()
    {
        return proName;
    }
    public void setProName(String proName)
    {
        this.proName = proName;
    }
    public String getMemberId()
    {
        return memberId;
    }
    public void setMemberId(String memberId)
    {
        this.memberId = memberId;
    }


}
