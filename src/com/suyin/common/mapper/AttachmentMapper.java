package com.suyin.common.mapper;

import java.util.List;

import com.suyin.common.model.Attachment;


public interface AttachmentMapper {
	   
	public Integer addAttachment(Attachment attachment);
	
	public Integer deleteAttachment(Attachment attachment);
	
	//public Integer updateAttachment(Attachment attachment);
	
	public List<Attachment> findAttachment(Attachment attachment);
	
	
	public Integer addAttachments(List<Attachment> attachment);
	
	
	public Integer upAttachmentEntity(Attachment attachment);
	
}
