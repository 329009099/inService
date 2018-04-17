package com.suyin.nouser.mapper;

import java.util.List;
import java.util.Map;

import com.suyin.nouser.model.NoUserPrototype;

public interface NoUserPrototypeMapper {

	public Integer addNoUserPrototype(NoUserPrototype noUserPrototype);

	public Integer deleteNoUserPrototypeByUserId(Map<String,Object> map);
	
	/**
	 * 获取所有用户动态属性
	 * @return
	 */
	public List<Map<String,Object>> findUserPrototype(Map<String,Object> map);

	/**
	 * 获取动态属性选项
	 */
	public List<Map<String,Object>> findUserPrototypeOption(Map<String,Object> map);
	
	/**
	 * 检查当前节点是否有子集
	 */
	public Integer findIsChildren(String dictionary_id);
	
	/**
	 * 获取页面分组对象
	 */
	public List<Map<String,Object>> findCodeByMoudleType(String moudleType);

	/**
	 * 获取省市
	 * @return
	 */
	public List<Map<String, Object>> findCitysByInface();

	public void updateUserArea(Map map);

	public Map findUserCitysByInface(int userId);

	public void updateNoUserPrototype(NoUserPrototype noUserPrototype);

	public int queryUserPrototypeAll(Integer user_id);

	public void addNoUserCoinLog(Map<String, Object> map);

	public void updateUserGoldCoin(Map<String, Object> map);

	public int findUserUpdateFlg(int parseInt);
	
	public List<Map<String,String>> findUserPrototypeSelected(Map<String,String> conditon);

	public void deleteAllLevelOneNoUserPrototypeByUserId(
			Map<String, Object> condition);
	
	public int addUserProperties(List<Map<String,Object>> list);
}
