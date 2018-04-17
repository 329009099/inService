package com.suyin.common.mapper;

import java.util.List;
import java.util.Map;

/**
 * 消息
 * @author Administrator
 *
 */
public interface MessageMapper {
	/**
	 * 关于两个赚的活动消息,提供userId和expId和parrern，
	 * pattern格式是 你参加了【#】活动（#是要使用活动的标题的地方）
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
