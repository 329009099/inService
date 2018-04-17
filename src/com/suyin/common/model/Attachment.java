package com.suyin.common.model;

import java.io.Serializable;
import java.util.Date;

public class Attachment implements Serializable {

	private static final long serialVersionUID = 4213667196861566590L;

	private Integer attachment_id;// '附件主键',
	private String attachment_ids;// '附件主键',
	private String module;// '业务模块',
	private Integer entity;// '业务实体',
	private String entity_attribute;// '业务实体属性',
	private String file_name;// '附件名称',
	private String file_type;// '附件类型',
	private Integer file_size;// '附件大小',
	private String file_path;// '附件物理路径',
	private String display_path;// '文件显示路径',
	private Integer deleted;// '删除标志',
	private Date create_time;// '创建时间',
	private Date update_time;// '修改时间'

	public final String getAttachment_ids() {
		return attachment_ids;
	}

	public final void setAttachment_ids(String attachment_ids) {
		this.attachment_ids = attachment_ids;
	}

	public Integer getAttachment_id() {
		return attachment_id;
	}

	public Attachment setAttachment_id(Integer attachment_id) {
		this.attachment_id = attachment_id;
		return this;
	}

	public String getModule() {
		return module;
	}

	public Attachment setModule(String module) {
		this.module = module;
		return this;
	}

	public Integer getEntity() {
		return entity;
	}

	public Attachment setEntity(Integer entity) {
		this.entity = entity;
		return this;
	}

	public String getEntity_attribute() {
		return entity_attribute;
	}

	public Attachment setEntity_attribute(String entity_attribute) {
		this.entity_attribute = entity_attribute;
		return this;
	}

	public String getFile_name() {
		return file_name;
	}

	public Attachment setFile_name(String file_name) {
		this.file_name = file_name;
		return this;
	}

	public String getFile_type() {
		return file_type;
	}

	public Attachment setFile_type(String file_type) {
		this.file_type = file_type;
		return this;
	}

	public Integer getFile_size() {
		return file_size;
	}

	public Attachment setFile_size(Integer file_size) {
		this.file_size = file_size;
		return this;
	}

	public String getFile_path() {
		return file_path;
	}

	public Attachment setFile_path(String file_path) {
		this.file_path = file_path;
		return this;
	}

	public String getDisplay_path() {
		return display_path;
	}

	public Attachment setDisplay_path(String display_path) {
		this.display_path = display_path;
		return this;
	}

	public Integer getDeleted() {
		return deleted;
	}

	public void setDeleted(Integer deleted) {
		this.deleted = deleted;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public Date getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}

}
