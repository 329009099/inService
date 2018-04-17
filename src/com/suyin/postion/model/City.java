package com.suyin.postion.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.suyin.system.model.Page;

public class City implements java.io.Serializable{

	
	private static final long serialVersionUID=5454155825314635342L;
	public static final String TABLE_ALIAS="City";
	public static final String ALIAS_ID="城市id";
	public static final String ALIAS_Name="城市名称name";
	public static final String ALIAS_LEVEL="城市级别level";
	public static final String ALIAS_UPID="从属于upid";
	public static final String ALIAS_LIST="排序";
	
	private java.lang.Integer hotCity;//0代表是热门城市，1代表不是热门城市
	
	//column start
	
	public java.lang.Integer getHotCity() {
		return hotCity;
	}

	public void setHotCity(java.lang.Integer hotCity) {
		this.hotCity = hotCity;
	}

	/**
	 * 城市id       db_column:id
	 */
	private java.lang.Integer id;
	
	/**
	 * 城市名        db_column:name
	 */
	private java.lang.String name;
	
	/**
	 * 城市级别       db_column:level
	 */
	private java.lang.Integer level;
	
	/**
	 * 从属于        db_column:upid
	 */
	private java.lang.Integer upid;
	
	/**
	 * 城市排序        db_column:list
	 */
	private java.lang.Integer list;

	//column end
	
	public City()
	{}
	
	public City(java.lang.Integer id)
	{
		this.id=id;
	}
	
	public City(String name)
	{
		this.name=name;
	}
	
	public java.lang.Integer getId() {
		return id;
	}

	public void setId(java.lang.Integer id) {
		this.id = id;
	}

	public java.lang.String getName() {
		return name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public java.lang.Integer getLevel() {
		return level;
	}

	public void setLevel(java.lang.Integer level) {
		this.level = level;
	}

	public java.lang.Integer getUpid() {
		return upid;
	}

	public void setUpid(java.lang.Integer upid) {
		this.upid = upid;
	}

	public java.lang.Integer getList() {
		return list;
	}

	public void setList(java.lang.Integer list) {
		this.list = list;
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
		.append("id",getId())
		.append("name",getName())
		.append("level",getLevel())
		.append("upid",getUpid())
		.append("list",getList())
		.toString();		
	}
	
	public int hashCode()
	{
		return new HashCodeBuilder()
		.append(getId())
		.toHashCode();		
	}
	
	public boolean equals(Object obj)
	{
		if(obj instanceof City == false)
		{
			return false;
		}
		if(obj == this)
		{
			return true;
		}		
		City other = new City();
		return new EqualsBuilder()
		.append(getId(), other.getId())
		.isEquals();
	}
	
}
