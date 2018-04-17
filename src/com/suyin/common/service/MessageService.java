package com.suyin.common.service;

import java.util.List;
import java.util.Map;

public interface MessageService {
	/**
	 * 关于两个赚的活动消息,提供userId和expId,pattern
	 * @param message
	 * @return
	 */
	public int addMessageForExpTask(Map<String,String> message);
	
	/**
	 * 一般的消息，提供content和userId
	 * */
	public int addMessage(Map<String,String> message);
	
	/**
	 * 查找用户的消息，userId page.currentPage page.showCount
	 * @param message
	 * @return
	 */
	public List<Map<String,String>> findMessageForUserByPage(Map<String,Object> message);
	
	public int deleteMessage(int messageId);
}
