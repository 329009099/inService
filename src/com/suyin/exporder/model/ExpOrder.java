package com.suyin.exporder.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.suyin.system.model.Page;

public class ExpOrder  implements java.io.Serializable{
    
	private static final long serialVersionUID = 5454155825314635342L;
	public static final String TABLE_ALIAS = "ExpOrder";
	public static final String ALIAS_ORDER_ID = "订单id";
	public static final String ALIAS_MEMBER_ID = "商家id";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_DETAIL_ID = "入单：产品的详情id";
	public static final String ALIAS_EXP_ID = "活动id";
	public static final String ALIAS_EXP_TYPE = "活动类型：0 抽奖式,1人气式,2兑换式，3试用式";
	public static final String ALIAS_STATUS = "订单状态：0申请中，1申请成功，2申请失败";
	public static final String ALIAS_IS_DISCUSS = "是否评价：0未评价，1评价";
	public static final String ALIAS_VOUCHER_STATUS = "是否发券：0未发券，1已发券";
	public static final String ALIAS_CREATE_TIME = "createTime";
	public static final String ALIAS_UPDATE_TIME = "updateTime";

	private Page page;//分页插件


	//columns start
		
    /**
     * 订单id       db_column: order_id 
     */ 	
	private java.lang.Integer orderId;
    /**
     * 商家id       db_column: member_id 
     */ 	
	private java.lang.Integer memberId;
    /**
     * 用户id       db_column: user_id 
     */ 	
	private java.lang.Integer userId;
    /**
     * 入单：产品的详情id       db_column: detail_id 
     */ 	
	private java.lang.Integer detailId;
    /**
     * 活动id       db_column: exp_id 
     */ 	
	private java.lang.Integer expId;
    /**
     * 活动类型：0 抽奖式,1人气式,2兑换式，3试用式       db_column: exp_type 
     */ 	
	private java.lang.Integer expType;
    /**
     * 订单状态：0申请中，1申请成功，2申请失败       db_column: status 
     */ 	
	private java.lang.Integer status;
    /**
     * 是否评价：0未评价，1评价       db_column: is_discuss 
     */ 	
	private java.lang.Integer isDiscuss;
    /**
     * 是否发券：0未发券，1已发券       db_column: voucher_status 
     */ 	
	private java.lang.Integer voucherStatus;
    /**
     * createTime       db_column: create_time 
     */ 	
	private java.util.Date createTime;
    /**
     * updateTime       db_column: update_time 
     */ 	
	private java.util.Date updateTime;
	
	//columns end
	private java.lang.Integer regType;


	public ExpOrder(){
	}

	public ExpOrder(
		java.lang.Integer orderId
	){
		this.orderId = orderId;
	}

	

	public void setOrderId(java.lang.Integer value) {
		this.orderId = value;
	}
	
	
	public java.lang.Integer getOrderId() {
		return this.orderId;
	}
	
	public java.lang.Integer getMemberId() {
		return this.memberId;
	}
	
	public void setMemberId(java.lang.Integer value) {
		this.memberId = value;
	}
	
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}
	
	public java.lang.Integer getDetailId() {
		return this.detailId;
	}
	
	public void setDetailId(java.lang.Integer value) {
		this.detailId = value;
	}
	
	public java.lang.Integer getExpId() {
		return this.expId;
	}
	
	public void setExpId(java.lang.Integer value) {
		this.expId = value;
	}
	
	public java.lang.Integer getExpType() {
		return this.expType;
	}
	
	public void setExpType(java.lang.Integer value) {
		this.expType = value;
	}
	
	public java.lang.Integer getStatus() {
		return this.status;
	}
	
	public void setStatus(java.lang.Integer value) {
		this.status = value;
	}
	
	public java.lang.Integer getIsDiscuss() {
		return this.isDiscuss;
	}
	
	public void setIsDiscuss(java.lang.Integer value) {
		this.isDiscuss = value;
	}
	
	public java.lang.Integer getVoucherStatus() {
		return this.voucherStatus;
	}
	
	public void setVoucherStatus(java.lang.Integer value) {
		this.voucherStatus = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
    public Page getPage() {
        return page;
    }
    public void setPage(Page page) {
        this.page = page;
    }   

    public java.lang.Integer getRegType()
    {
        return regType;
    }

    public void setRegType(java.lang.Integer regType)
    {
        this.regType = regType;
    }

    public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("OrderId",getOrderId())
			.append("MemberId",getMemberId())
			.append("UserId",getUserId())
			.append("DetailId",getDetailId())
			.append("ExpId",getExpId())
			.append("ExpType",getExpType())
			.append("Status",getStatus())
			.append("IsDiscuss",getIsDiscuss())
			.append("VoucherStatus",getVoucherStatus())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getOrderId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ExpOrder == false) return false;
		if(this == obj) return true;
		ExpOrder other = (ExpOrder)obj;
		return new EqualsBuilder()
			.append(getOrderId(),other.getOrderId())
			.isEquals();
	}
}

