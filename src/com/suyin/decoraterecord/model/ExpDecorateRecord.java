package com.suyin.decoraterecord.model;

import java.math.BigDecimal;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.suyin.system.model.Page;

public class ExpDecorateRecord  implements java.io.Serializable{
    
	private static final long serialVersionUID = 5454155825314635342L;


	private Page page;//分页插件

	//columns start
		
    /**
     * recordId       db_column: record_id 
     */ 	
	private java.lang.Integer recordId;
    /**
     * 微信分享发起者openid       db_column: publish_openid 
     */ 	
	private java.lang.String publishOpenid;
    /**
     * 发布者userid       db_column: publish_userid 
     */ 	
	private java.lang.Integer publishUserid;
    /**
     * 被邀请人微信openid       db_column: accept_openid 
     */ 	
	private java.lang.String acceptOpenid;
    /**
     * 接受人userid       db_column: accpt_userid 
     */ 	
	private java.lang.Integer accptUserid;
    /**
     * 标识：0发起者，1:接收者       db_column: state 
     */ 	
	private java.lang.Integer state;
    /**
     * createTime       db_column: create_time 
     */ 	
	private java.util.Date createTime;
	
	/**
	 * 本次佣金金额
	 */
	private String commissionPrice ;
	
	//columns end


	public ExpDecorateRecord(){
	}

	public ExpDecorateRecord(
		java.lang.Integer recordId
	){
		this.recordId = recordId;
	}

	

	public void setRecordId(java.lang.Integer value) {
		this.recordId = value;
	}
	
	
	public java.lang.Integer getRecordId() {
		return this.recordId;
	}
	
	public java.lang.String getPublishOpenid() {
		return this.publishOpenid;
	}
	
	public void setPublishOpenid(java.lang.String value) {
		this.publishOpenid = value;
	}
	
	public java.lang.Integer getPublishUserid() {
		return this.publishUserid;
	}
	
	public void setPublishUserid(java.lang.Integer value) {
		this.publishUserid = value;
	}
	
	public java.lang.String getAcceptOpenid() {
		return this.acceptOpenid;
	}
	
	public void setAcceptOpenid(java.lang.String value) {
		this.acceptOpenid = value;
	}
	
	public java.lang.Integer getAccptUserid() {
		return this.accptUserid;
	}
	
	public void setAccptUserid(java.lang.Integer value) {
		this.accptUserid = value;
	}
	
	public java.lang.Integer getState() {
		return this.state;
	}
	
	public void setState(java.lang.Integer value) {
		this.state = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
    public String getCommissionPrice() {
		return commissionPrice;
	}

	public void setCommissionPrice(String commissionPrice) {
		this.commissionPrice = commissionPrice;
	}

	public Page getPage() {
        return page;
    }
    public void setPage(Page page) {
        this.page = page;
    }
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("RecordId",getRecordId())
			.append("PublishOpenid",getPublishOpenid())
			.append("PublishUserid",getPublishUserid())
			.append("AcceptOpenid",getAcceptOpenid())
			.append("AccptUserid",getAccptUserid())
			.append("State",getState())
			.append("CreateTime",getCreateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getRecordId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ExpDecorateRecord == false) return false;
		if(this == obj) return true;
		ExpDecorateRecord other = (ExpDecorateRecord)obj;
		return new EqualsBuilder()
			.append(getRecordId(),other.getRecordId())
			.isEquals();
	}
}

