package com.suyin.common.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suyin.common.mapper.MessageMapper;
import com.suyin.common.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageMapper messageMapper;
	@Override
	public int addMessageForExpTask(Map<String, String> message) {
		return this.messageMapper.addMessageForExpTask(message);
	}

	@Override
	public int addMessage(Map<String, String> message) {
		return this.messageMapper.addMessage(message);
	}
	
	/**
	 * 查找用户的消息，userId page.currentPage page.showCount
	 * @param message
	 * @return
	 */
	@Override
	public List<Map<String, String>> findMessageForUserByPage(
			Map<String, Object> message) {
		return this.messageMapper.findMessageForUserByPage(message);
	}

	@Override
	public int deleteMessage(int messageId) {
		return this.messageMapper.deleteMessage(messageId);
	}


}
