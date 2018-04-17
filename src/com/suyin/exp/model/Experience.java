package com.suyin.exp.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.suyin.system.model.Page;

public class Experience  implements java.io.Serializable{

    private static final long serialVersionUID = 5454155825314635342L;

    private Page page;//分页插件

    /**
     * 活动主键       db_column: exp_id 
     */ 	
    private java.lang.Integer expId;
    /**
     * 商家id       db_column: member_id 
     */ 	
    private java.lang.Integer memberId;
    /**
     * 活动标题       db_column: title 
     */ 	
    private java.lang.String title;
    /**
     * 
     */ 	
    private java.lang.Integer type;
    /**
     * 活动类型 0抽奖式 1人气式
     */
    private java.lang.Integer expType;
    /**
     * 活动是（0）否（1）开始       db_column: is_begin 
     */ 	
    private java.lang.Integer isBegin;
    /**
     * 活动概率       db_column: probability 
     */ 	
    private java.lang.Integer probability;
    /**
     * 活动开始时间       db_column: begin_time 
     */ 	
    private java.lang.String beginTime;
    /**
     * 活动结束时间       db_column: end_time 
     */ 	
    private java.lang.String endTime;
    /**
     * 单位发券天数       db_column: unit_day 
     */ 	
    private java.lang.Integer unitDay;
    /**
     * 0:普通类型1:拼图:2刮刮卡3抽签       db_column: show_type 
     */ 	
    private java.lang.Integer showType;
    /**
     * 增加订单数       db_column: add_order_num 
     */ 	
    private java.lang.Integer addOrderNum;
    /**
     * createTime       db_column: create_time 
     */ 	
    private java.lang.String createTime;
    /**
     * 更新时间       db_column: update_time 
     */ 	
    private java.lang.String updateTime;
    /**
     * 省份    db_column: provin_id 
     */     
    private java.lang.String provinId;
    /**
     * 市区       db_column: city_id 
     */     
    private java.lang.String cityId;
    /**
     * 活动简介       db_column: info 
     */ 	
    private java.lang.String info;

    //columns end


    //追加字段 辅助查询 准查询的平台类型
    private java.lang.String regType;
    
    /**
     * 获取期数
     */
    private String installments;
     

    public java.lang.String getRegType()
    {
        return regType;
    }

    public void setRegType(java.lang.String regType)
    {
        this.regType = regType;
    }

    public Experience(){
    }

    public Experience(
                      java.lang.Integer expId
        ){
        this.expId = expId;
    }



    public void setExpId(java.lang.Integer value) {
        this.expId = value;
    }


    public java.lang.Integer getExpId() {
        return this.expId;
    }

    public java.lang.Integer getMemberId() {
        return this.memberId;
    }

    public void setMemberId(java.lang.Integer value) {
        this.memberId = value;
    }

    public java.lang.String getTitle() {
        return this.title;
    }

    public void setTitle(java.lang.String value) {
        this.title = value;
    }

    public java.lang.Integer getType() {
        return this.type;
    }

    public void setType(java.lang.Integer value) {
        this.type = value;
    }

    public java.lang.Integer getExpType()
    {
        return expType;
    }

    public void setExpType(java.lang.Integer expType)
    {
        this.expType = expType;
    }

    public java.lang.Integer getIsBegin() {
        return this.isBegin;
    }

    public void setIsBegin(java.lang.Integer value) {
        this.isBegin = value;
    }

    public java.lang.Integer getProbability() {
        return this.probability;
    }

    public void setProbability(java.lang.Integer value) {
        this.probability = value;
    }

    public java.lang.String getBeginTime() {
        return this.beginTime;
    }

    public void setBeginTime(java.lang.String value) {
        this.beginTime = value;
    }

    public java.lang.String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(java.lang.String value) {
        this.endTime = value;
    }

    public java.lang.Integer getUnitDay() {
        return this.unitDay;
    }

    public void setUnitDay(java.lang.Integer value) {
        this.unitDay = value;
    }

    public java.lang.Integer getShowType() {
        return this.showType;
    }

    public void setShowType(java.lang.Integer value) {
        this.showType = value;
    }

    public java.lang.Integer getAddOrderNum() {
        return this.addOrderNum;
    }

    public void setAddOrderNum(java.lang.Integer value) {
        this.addOrderNum = value;
    }

    public java.lang.String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(java.lang.String value) {
        this.createTime = value;
    }

    public java.lang.String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(java.lang.String value) {
        this.updateTime = value;
    }

    public java.lang.String getInfo() {
        return this.info;
    }

    public void setInfo(java.lang.String value) {
        this.info = value;
    }

    public Page getPage() {
        return page;
    }
    public void setPage(Page page) {
        this.page = page;
    }

    public java.lang.String getProvinId()
    {
        return provinId;
    }

    public void setProvinId(java.lang.String provinId)
    {
        this.provinId = provinId;
    }

    public java.lang.String getCityId()
    {
        return cityId;
    }

    public void setCityId(java.lang.String cityId)
    {
        this.cityId = cityId;
    }

    
    public String getInstallments()
    {
        return installments;
    }

    public void setInstallments(String installments)
    {
        this.installments = installments;
    }

    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
        .append("ExpId",getExpId())
        .append("MemberId",getMemberId())
        .append("Title",getTitle())
        .append("Type",getType())
        .append("IsBegin",getIsBegin())
        .append("Probability",getProbability())
        .append("BeginTime",getBeginTime())
        .append("EndTime",getEndTime())
        .append("UnitDay",getUnitDay())
        .append("ShowType",getShowType())
        .append("AddOrderNum",getAddOrderNum())
        .append("CreateTime",getCreateTime())
        .append("UpdateTime",getUpdateTime())
        .append("Info",getInfo())
        .append("Installments",getInstallments())
        .toString();
    }

    public int hashCode() {
        return new HashCodeBuilder()
        .append(getExpId())
        .toHashCode();
    }

    public boolean equals(Object obj) {
        if(obj instanceof Experience == false) return false;
        if(this == obj) return true;
        Experience other = (Experience)obj;
        return new EqualsBuilder()
        .append(getExpId(),other.getExpId())
        .isEquals();
    }
}

