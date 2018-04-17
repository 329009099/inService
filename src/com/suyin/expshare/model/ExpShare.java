package com.suyin.expshare.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.suyin.system.model.Page;

public class ExpShare  implements java.io.Serializable{

    private static final long serialVersionUID = 5454155825314635342L;
    public static final String TABLE_ALIAS = "ExpShare";
    public static final String ALIAS_SHARE_ID = "用户分享主键_id";
    public static final String ALIAS_DETAIL_ID = "分享活动详情id";
    public static final String ALIAS_USER_ID = "分享者主键信息";
    public static final String ALIAS_OPEN_ID = "微信openid";
    public static final String ALIAS_EXP_TYPE = "活动类型：0 抽奖式,1人气式,2兑换式，3试用式";
    public static final String ALIAS_CREATE_TIME = "创建时间";
    public static final String ALIAS_UPDATE_TIME = "更新时间";

    private Page page;//分页插件


    //columns start

    /**
     * 用户分享主键_id       db_column: share_id 
     */ 	
    private java.lang.Integer shareId;
    /**
     * 分享活动详情id       db_column: detail_id 
     */ 	
    private java.lang.Integer detailId;
    /**
     * 分享者主键信息       db_column: user_id 
     */ 	
    private java.lang.Integer userId;
    /**
     * 微信openid       db_column: open_id 
     */ 	
    private java.lang.String openId;
    /**
     * 活动类型：0 抽奖式,1人气式,2兑换式，3试用式       db_column: exp_type 
     */ 	
    private java.lang.Integer expType;
    /**
     * 活动id       db_column: exp_id 
     */     
    private java.lang.Integer expId;
    /**
     * 分享总数     db_column: share_num 
     */     
    private java.lang.Integer shareNum;
    /**
     * 创建时间       db_column: create_time 
     */ 	
    private java.util.Date createTime;
    /**
     * 更新时间       db_column: update_time 
     */ 	
    private java.util.Date updateTime;
    
    private String expTimeId;
    

    //columns end


    public final String getExpTimeId() {
		return expTimeId;
	}

	public final void setExpTimeId(String expTimeId) {
		this.expTimeId = expTimeId;
	}

	public static final long getSerialversionuid() {
		return serialVersionUID;
	}

	public static final String getTableAlias() {
		return TABLE_ALIAS;
	}

	public static final String getAliasShareId() {
		return ALIAS_SHARE_ID;
	}

	public static final String getAliasDetailId() {
		return ALIAS_DETAIL_ID;
	}

	public static final String getAliasUserId() {
		return ALIAS_USER_ID;
	}

	public static final String getAliasOpenId() {
		return ALIAS_OPEN_ID;
	}

	public static final String getAliasExpType() {
		return ALIAS_EXP_TYPE;
	}

	public static final String getAliasCreateTime() {
		return ALIAS_CREATE_TIME;
	}

	public static final String getAliasUpdateTime() {
		return ALIAS_UPDATE_TIME;
	}

	public ExpShare(){
    }

    public ExpShare(
                    java.lang.Integer shareId
        ){
        this.shareId = shareId;
    }



    public void setShareId(java.lang.Integer value) {
        this.shareId = value;
    }


    public java.lang.Integer getShareId() {
        return this.shareId;
    }

    public java.lang.Integer getDetailId() {
        return this.detailId;
    }

    public void setDetailId(java.lang.Integer value) {
        this.detailId = value;
    }

    public java.lang.Integer getUserId() {
        return this.userId;
    }

    public void setUserId(java.lang.Integer value) {
        this.userId = value;
    }

    public java.lang.String getOpenId() {
        return this.openId;
    }

    public void setOpenId(java.lang.String value) {
        this.openId = value;
    }

    public java.lang.Integer getExpType() {
        return this.expType;
    }

    public void setExpType(java.lang.Integer value) {
        this.expType = value;
    }

    public java.lang.Integer getExpId()
    {
        return expId;
    }

    public void setExpId(java.lang.Integer expId)
    {
        this.expId = expId;
    }

    public java.lang.Integer getShareNum()
    {
        return shareNum;
    }

    public void setShareNum(java.lang.Integer shareNum)
    {
        this.shareNum = shareNum;
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
        .append("ShareId",getShareId())
        .append("DetailId",getDetailId())
        .append("UserId",getUserId())
        .append("OpenId",getOpenId())
        .append("ExpType",getExpType())
        .append("CreateTime",getCreateTime())
        .append("UpdateTime",getUpdateTime())
        .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
        .append(getShareId())
        .toHashCode();
    }

    public boolean equals(Object obj) {
        if(obj instanceof ExpShare == false) return false;
        if(this == obj) return true;
        ExpShare other = (ExpShare)obj;
        return new EqualsBuilder()
        .append(getShareId(),other.getShareId())
        .isEquals();
    }
}

