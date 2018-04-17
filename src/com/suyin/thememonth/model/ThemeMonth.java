package com.suyin.thememonth.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.suyin.system.model.Page;

public class ThemeMonth  implements java.io.Serializable{
    
	private static final long serialVersionUID = 5454155825314635342L;
	public static final String TABLE_ALIAS = "ThemeMonth";
	public static final String ALIAS_THEME_ID = "themeId";
	public static final String ALIAS_THEME_TITLE = "主题月标题";
	public static final String ALIAS_THEME_LOGO = "主题logo";
	public static final String ALIAS_THEME_PIC = "主题图";
	public static final String ALIAS_BOTTOM_PIC = "bottomPic";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "更新时间";
	public static final String ALIAS_COLOR = "color";
	public static final String ALIAS_MONTH = "选择月份";

	private Page page;//分页插件


	//columns start
		
    /**
     * themeId       db_column: theme_id 
     */ 	
	private java.lang.Integer themeId;
    /**
     * 主题月标题       db_column: theme_title 
     */ 	
	private java.lang.String themeTitle;
    /**
     * 主题logo       db_column: theme_logo 
     */ 	
	private java.lang.String themeLogo;
    /**
     * 主题图       db_column: theme_pic 
     */ 	
	private java.lang.String themePic;
    /**
     * bottomPic       db_column: bottom_pic 
     */ 	
	private java.lang.String bottomPic;
    /**
     * 创建时间       db_column: create_time 
     */ 	
	private java.util.Date createTime;
    /**
     * 更新时间       db_column: update_time 
     */ 	
	private java.util.Date updateTime;
    /**
     * color       db_column: color 
     */ 	
	private java.lang.String color;
    /**
     * 选择月份       db_column: month 
     */ 	
	private java.util.Date month;
	
	//columns end


	public ThemeMonth(){
	}

	public ThemeMonth(
		java.lang.Integer themeId
	){
		this.themeId = themeId;
	}

	

	public void setThemeId(java.lang.Integer value) {
		this.themeId = value;
	}
	
	
	public java.lang.Integer getThemeId() {
		return this.themeId;
	}
	
	public java.lang.String getThemeTitle() {
		return this.themeTitle;
	}
	
	public void setThemeTitle(java.lang.String value) {
		this.themeTitle = value;
	}
	
	public java.lang.String getThemeLogo() {
		return this.themeLogo;
	}
	
	public void setThemeLogo(java.lang.String value) {
		this.themeLogo = value;
	}
	
	public java.lang.String getThemePic() {
		return this.themePic;
	}
	
	public void setThemePic(java.lang.String value) {
		this.themePic = value;
	}
	
	public java.lang.String getBottomPic() {
		return this.bottomPic;
	}
	
	public void setBottomPic(java.lang.String value) {
		this.bottomPic = value;
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
	
	public java.lang.String getColor() {
		return this.color;
	}
	
	public void setColor(java.lang.String value) {
		this.color = value;
	}
	
	public java.util.Date getMonth() {
		return this.month;
	}
	
	public void setMonth(java.util.Date value) {
		this.month = value;
	}
	
    public Page getPage() {
        return page;
    }
    public void setPage(Page page) {
        this.page = page;
    }
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("ThemeId",getThemeId())
			.append("ThemeTitle",getThemeTitle())
			.append("ThemeLogo",getThemeLogo())
			.append("ThemePic",getThemePic())
			.append("BottomPic",getBottomPic())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.append("Color",getColor())
			.append("Month",getMonth())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getThemeId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof ThemeMonth == false) return false;
		if(this == obj) return true;
		ThemeMonth other = (ThemeMonth)obj;
		return new EqualsBuilder()
			.append(getThemeId(),other.getThemeId())
			.isEquals();
	}
}

