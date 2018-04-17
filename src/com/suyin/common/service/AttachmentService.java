package com.suyin.common.service;

import java.util.List;

import com.suyin.common.model.Attachment;

public interface AttachmentService {
	
	public Integer addAttachment(Attachment attachment);
	
	public Integer deleteAttachment(Attachment attachment);
	
	//public Integer updateAttachment(Attachment attachment);
	
	public List<Attachment> findAttachment(Attachment attachment);
	
	
	public Attachment getAttachment(Attachment attachment);
}
