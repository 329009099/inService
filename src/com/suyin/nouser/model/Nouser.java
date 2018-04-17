package com.suyin.nouser.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.suyin.system.model.Page;

public class Nouser  implements java.io.Serializable{

    private static final long serialVersionUID = 5454155825314635342L;
    public static final String TABLE_ALIAS = "Nouser";
    public static final String ALIAS_USER_ID = "主键";
    public static final String ALIAS_OPENID = "微信openid";
    public static final String ALIAS_USER_PHONE = "手机号码";
    public static final String ALIAS_USER_PASSWORD = "用户密码";
    public static final String ALIAS_USER_STATE = "用户状态(0正常,1锁定,2删除)";
    public static final String ALIAS_CREATE_TIME = "创建时间";
    public static final String ALIAS_UPDATE_TIME = "修改时间";

    private Page page;//分页插件


    //columns start

    /**
     * 主键       db_column: user_id 
     */ 	
    private java.lang.Integer userId;
    /**
     * 微信openid       db_column: openid 
     */ 	
    private java.lang.String openid;
    /**
     * 手机号码       db_column: user_phone 
     */ 	
    private java.lang.String userPhone;
    /**
     * 用户密码       db_column: user_password 
     */ 	
    private java.lang.String userPassword;
    
    /**
     * 用户新密码
     */
    private java.lang.String newPassword;
    /**
     * 用户状态(0正常,1锁定,2删除)       db_column: user_state 
     */ 	
    private java.lang.Integer userState;
    /**
     * 创建时间       db_column: create_time 
     */ 	
    private java.util.Date createTime;
    /**
     * 修改时间       db_column: update_time 
     */ 	
    private java.util.Date updateTime;

    //参数调用
    private java.lang.String code;
    private java.lang.String regtype;
    private java.lang.String version;
    //columns end
    private java.lang.Integer loginType;

    public Nouser(){
    }

    public Nouser(
                  java.lang.Integer userId
        ){
        this.userId = userId;
    }



    public void setUserId(java.lang.Integer value) {
        this.userId = value;
    }


    public java.lang.Integer getUserId() {
        return this.userId;
    }

    public java.lang.String getOpenid() {
        return this.openid;
    }

    public void setOpenid(java.lang.String value) {
        this.openid = value;
    }

    public java.lang.String getUserPhone() {
        return this.userPhone;
    }

    public void setUserPhone(java.lang.String value) {
        this.userPhone = value;
    }

    public java.lang.String getUserPassword() {
        return this.userPassword;
    }

    public void setUserPassword(java.lang.String value) {
        this.userPassword = value;
    }

    public java.lang.Integer getUserState() {
        return this.userState;
    }

    public void setUserState(java.lang.Integer value) {
        this.userState = value;
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

    public java.lang.String getCode()
    {
        return code;
    }

    public void setCode(java.lang.String code)
    {
        this.code = code;
    }

    public java.lang.String getRegtype()
    {
        return regtype;
    }

    public void setRegtype(java.lang.String regtype)
    {
        this.regtype = regtype;
    }

    public java.lang.String getVersion()
    {
        return version;
    }

    public void setVersion(java.lang.String version)
    {
        this.version = version;
    }       

    public java.lang.Integer getLoginType()
    {
        return loginType;
    }

    public void setLoginType(java.lang.Integer loginType)
    {
        this.loginType = loginType;
    }

    public java.lang.String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(java.lang.String newPassword) {
		this.newPassword = newPassword;
	}

	public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        .append("UserId",getUserId())
        .append("Openid",getOpenid())
        .append("UserPhone",getUserPhone())
        .append("UserPassword",getUserPassword())
        .append("UserState",getUserState())
        .append("CreateTime",getCreateTime())
        .append("UpdateTime",getUpdateTime())
        .append("NewPassword",getNewPassword())
        .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
        .append(getUserId())
        .toHashCode();
    }

    public boolean equals(Object obj) {
        if(obj instanceof Nouser == false) return false;
        if(this == obj) return true;
        Nouser other = (Nouser)obj;
        return new EqualsBuilder()
        .append(getUserId(),other.getUserId())
        .isEquals();
    }
}

