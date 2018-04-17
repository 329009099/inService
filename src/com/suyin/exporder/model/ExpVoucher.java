package com.suyin.exporder.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.suyin.system.model.Page;

public class ExpVoucher  implements java.io.Serializable{
    
	private static final long serialVersionUID = 5454155825314635342L;
	public static final String TABLE_ALIAS = "ExpVoucher";
	public static final String ALIAS_VOU_ID = "券号主键";
	public static final String ALIAS_VOU_CODE = "券号";
	public static final String ALIAS_ORDER_ID = "订单id";
	public static final String ALIAS_USER_ID = "用户id";
	public static final String ALIAS_VOU_STAUTS = "券的状态";
	public static final String ALIAS_VALIDITY = "券的到期时间";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";

	private Page page;//分页插件


	//columns start
		
    /**
     * 券号主键       db_column: vou_id 
     */ 	
	private java.lang.Integer vouId;
    /**
     * 券号       db_column: vou_code 
     */ 	
	private java.lang.String vouCode;
    /**
     * 订单id       db_column: order_id 
     */ 	
	private java.lang.Integer orderId;
    /**
     * 用户id       db_column: user_id 
     */ 	
	private java.lang.Integer userId;
    /**
     * 券的状态       db_column: vou_stauts 
     */ 	
	private java.lang.Integer vouStauts;
    /**
     * 券的到期时间       db_column: validity 
     */ 	
	private java.lang.String validity;
    /**
     * 创建时间       db_column: create_time 
     */ 	
	private java.util.Date createTime;
    /**
     * 更新时间       db_column: update_time 
     */ 	
	private java.util.Date updateTime;
	
	//columns end


	public ExpVoucher(){
	}

	public ExpVoucher(
		java.lang.Integer vouId
	){
		this.vouId = vouId;
	}

	

	public void setVouId(java.lang.Integer value) {
		this.vouId = value;
	}
	
	
	public java.lang.Integer getVouId() {
		return this.vouId;
	}
	
	public java.lang.String getVouCode() {
		return this.vouCode;
	}
	
	public void setVouCode(java.lang.String value) {
		this.vouCode = value;
	}
	
	public java.lang.Integer getOrderId() {
		return this.orderId;
	}
	
	public void setOrderId(java.lang.Integer value) {
		this.orderId = value;
	}
	
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}
	
	public java.lang.Integer getVouStauts() {
		return this.vouStauts;
	}
	
	public void setVouStauts(java.lang.Integer value) {
		this.vouStauts = value;
	}
	
	public java.lang.String getValidity() {
		return this.validity;
	}
	
	public void setValidity(java.lang.String value) {
		this.validity = value;
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
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("VouId",getVouId())
			.append("VouCode",getVouCode())
			.append("OrderId",getOrderId())
			.append("UserId",getUserId())
			.append("VouStauts",getVouStauts())
			.append("Validity",getValidity())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getVouId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ExpVoucher == false) return false;
		if(this == obj) return true;
		ExpVoucher other = (ExpVoucher)obj;
		return new EqualsBuilder()
			.append(getVouId(),other.getVouId())
			.isEquals();
	}
}

