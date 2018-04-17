package com.suyin.nouser.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.suyin.system.model.Page;

public class RelationNouser  implements java.io.Serializable{
    
	private static final long serialVersionUID = 5454155825314635342L;
	public static final String TABLE_ALIAS = "RelationNouser";
	public static final String ALIAS_RELATION_USER_ID = "主键";
	public static final String ALIAS_USER_ID = "用户表主键";
	public static final String ALIAS_REGISTERED_TYPE = "登陆平台类型(0微信,1ios,2安卓)";
	public static final String ALIAS_REGISTERED_EDITION = "当前客户端版本号";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "修改时间";

	private Page page;//分页插件


	//columns start
		
    /**
     * 主键       db_column: relation_user_id 
     */ 	
	private java.lang.Integer relationUserId;
    /**
     * 用户表主键       db_column: user_id 
     */ 	
	private java.lang.Integer userId;
    /**
     * 登陆平台类型(0微信,1ios,2安卓)       db_column: registered_type 
     */ 	
	private java.lang.Integer registeredType;
    /**
     * 当前客户端版本号       db_column: registered_edition 
     */ 	
	private java.lang.String registeredEdition;
    /**
     * 创建时间       db_column: create_time 
     */ 	
	private java.util.Date createTime;
    /**
     * 修改时间       db_column: update_time 
     */ 	
	private java.util.Date updateTime;
	
	//columns end


	public RelationNouser(){
	}

	public RelationNouser(
		java.lang.Integer relationUserId
	){
		this.relationUserId = relationUserId;
	}

	

	public void setRelationUserId(java.lang.Integer value) {
		this.relationUserId = value;
	}
	
	
	public java.lang.Integer getRelationUserId() {
		return this.relationUserId;
	}
	
	public java.lang.Integer getUserId() {
		return this.userId;
	}
	
	public void setUserId(java.lang.Integer value) {
		this.userId = value;
	}
	
	public java.lang.Integer getRegisteredType() {
		return this.registeredType;
	}
	
	public void setRegisteredType(java.lang.Integer value) {
		this.registeredType = value;
	}
	
	public java.lang.String getRegisteredEdition() {
		return this.registeredEdition;
	}
	
	public void setRegisteredEdition(java.lang.String value) {
		this.registeredEdition = value;
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
			.append("RelationUserId",getRelationUserId())
			.append("UserId",getUserId())
			.append("RegisteredType",getRegisteredType())
			.append("RegisteredEdition",getRegisteredEdition())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getRelationUserId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof RelationNouser == false) return false;
		if(this == obj) return true;
		RelationNouser other = (RelationNouser)obj;
		return new EqualsBuilder()
			.append(getRelationUserId(),other.getRelationUserId())
			.isEquals();
	}
}

