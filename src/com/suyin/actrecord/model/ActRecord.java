package com.suyin.actrecord.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.suyin.system.model.Page;

public class ActRecord  implements java.io.Serializable{
    
	private static final long serialVersionUID = 5454155825314635342L;
	public static final String TABLE_ALIAS = "ActRecord";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_OPENID = "微信openid";
	public static final String ALIAS_ACT_ID = "活动id";
	public static final String ALIAS_RECORD_TIME = "参与时间";
	public static final String ALIAS_PART_ID = "选手ID";
	public static final String ALIAS_CARETE_TIME = "careteTime";
	public static final String ALIAS_IS_CORD = "是否参与";

	private Page page;//分页插件


	//columns start
		
    /**
     * id       db_column: id 
     */ 	
	private java.lang.Integer id;
    /**
     * 微信openid       db_column: openid 
     */ 	
	private java.lang.String openid;
    /**
     * 活动id       db_column: act_id 
     */ 	
	private java.lang.Integer actId;
    /**
     * 参与时间       db_column: record_time 
     */ 	
	private java.util.Date recordTime;
    /**
     * 选手ID       db_column: part_id 
     */ 	
	private java.lang.Integer partId;
    /**
     * careteTime       db_column: carete_time 
     */ 	
	private java.util.Date careteTime;
    /**
     * 是否参与       db_column: is_cord 
     */ 	
	private java.lang.Integer isCord;
	
	//columns end


	public ActRecord(){
	}

	public ActRecord(
		java.lang.Integer id
	){
		this.id = id;
	}

	

	public void setId(java.lang.Integer value) {
		this.id = value;
	}
	
	
	public java.lang.Integer getId() {
		return this.id;
	}
	
	public java.lang.String getOpenid() {
		return this.openid;
	}
	
	public void setOpenid(java.lang.String value) {
		this.openid = value;
	}
	
	public java.lang.Integer getActId() {
		return this.actId;
	}
	
	public void setActId(java.lang.Integer value) {
		this.actId = value;
	}
	
	public java.util.Date getRecordTime() {
		return this.recordTime;
	}
	
	public void setRecordTime(java.util.Date value) {
		this.recordTime = value;
	}
	
	public java.lang.Integer getPartId() {
		return this.partId;
	}
	
	public void setPartId(java.lang.Integer value) {
		this.partId = value;
	}
	
	public java.util.Date getCareteTime() {
		return this.careteTime;
	}
	
	public void setCareteTime(java.util.Date value) {
		this.careteTime = value;
	}
	
	public java.lang.Integer getIsCord() {
		return this.isCord;
	}
	
	public void setIsCord(java.lang.Integer value) {
		this.isCord = value;
	}
	
    public Page getPage() {
        return page;
    }
    public void setPage(Page page) {
        this.page = page;
    }
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Openid",getOpenid())
			.append("ActId",getActId())
			.append("RecordTime",getRecordTime())
			.append("PartId",getPartId())
			.append("CareteTime",getCareteTime())
			.append("IsCord",getIsCord())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ActRecord == false) return false;
		if(this == obj) return true;
		ActRecord other = (ActRecord)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

