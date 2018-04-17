package com.suyin.nouser.model;

import java.io.Serializable;
import java.util.Date;

public class NoUserPrototype implements Serializable {
	private static final long serialVersionUID = 4828211330795647867L;
	private Integer prototype_id;//
	private Integer user_id;// '用户表主键',
	private Integer dictionary_id;// '字典表主键',
	private String text_value;// '文本框类型的值',
	private Integer is_selected;// '复选框/单选框(1选中,2未选中)',
	private Date create_time;// ; '创建时间',
	private int parent_id ;//问题ID
	private int level ;//1：一级 ， 2：二级
	private String code;
	
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Integer getPrototype_id() {
		return prototype_id;
	}

	public void setPrototype_id(Integer prototype_id) {
		this.prototype_id = prototype_id;
	}

	public Integer getUser_id() {
		return user_id;
	}

	public void setUser_id(Integer user_id) {
		this.user_id = user_id;
	}

	public Integer getDictionary_id() {
		return dictionary_id;
	}

	public void setDictionary_id(Integer dictionary_id) {
		this.dictionary_id = dictionary_id;
	}

	public String getText_value() {
		return text_value;
	}

	public void setText_value(String text_value) {
		this.text_value = text_value;
	}

	public Integer getIs_selected() {
		return is_selected;
	}

	public void setIs_selected(Integer is_selected) {
		this.is_selected = is_selected;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getParent_id() {
		return parent_id;
	}

	public void setParent_id(int parent_id) {
		this.parent_id = parent_id;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
