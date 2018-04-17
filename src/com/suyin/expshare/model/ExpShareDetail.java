package com.suyin.expshare.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.suyin.system.model.Page;

public class ExpShareDetail  implements java.io.Serializable{
    
	private static final long serialVersionUID = 5454155825314635342L;
	public static final String TABLE_ALIAS = "ExpShareDetail";
	public static final String ALIAS_SHARE_DETAIL_ID = "微信分享详情id";
	public static final String ALIAS_SHARE_ID = "主分享者_记录主键";
	public static final String ALIAS_OPEN_ID = "协助分享_朋友_微信openid";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";

	private Page page;//分页插件


	//columns start
		
    /**
     * 微信分享详情id       db_column: share_detail_id 
     */ 	
	private java.lang.Integer shareDetailId;
    /**
     * 主分享者_记录主键       db_column: share_id 
     */ 	
	private java.lang.Integer shareId;
    /**
     * 协助分享_朋友_微信openid       db_column: open_id 
     */ 	
	private java.lang.String openId;
    /**
     * 创建时间       db_column: create_time 
     */ 	
	private java.util.Date createTime;
    /**
     * 更新时间       db_column: update_time 
     */ 	
	private java.util.Date updateTime;
	
	//columns end


	public ExpShareDetail(){
	}

	public ExpShareDetail(
		java.lang.Integer shareDetailId
	){
		this.shareDetailId = shareDetailId;
	}

	

	public void setShareDetailId(java.lang.Integer value) {
		this.shareDetailId = value;
	}
	
	
	public java.lang.Integer getShareDetailId() {
		return this.shareDetailId;
	}
	
	public java.lang.Integer getShareId() {
		return this.shareId;
	}
	
	public void setShareId(java.lang.Integer value) {
		this.shareId = value;
	}
	
	public java.lang.String getOpenId() {
		return this.openId;
	}
	
	public void setOpenId(java.lang.String value) {
		this.openId = value;
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
			.append("ShareDetailId",getShareDetailId())
			.append("ShareId",getShareId())
			.append("OpenId",getOpenId())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getShareDetailId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ExpShareDetail == false) return false;
		if(this == obj) return true;
		ExpShareDetail other = (ExpShareDetail)obj;
		return new EqualsBuilder()
			.append(getShareDetailId(),other.getShareDetailId())
			.isEquals();
	}
}

