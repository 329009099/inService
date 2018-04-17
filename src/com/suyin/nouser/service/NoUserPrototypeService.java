package com.suyin.nouser.service;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


public interface NoUserPrototypeService {
	
	/**
	 * 用户属性添加
	 * @param noUserPrototype
	 * @return
	 */
	public Integer addNoUserPrototype(String jsonArray,String upFlag);

	/**
	 * 删除用户属性
	 * @param userId
	 * @return
	 */
	public Integer deleteNoUserPrototypeByUserId(Map<String,Object> map);
	
	/**
	 * 查询用户模块动态配置属性(userI为null那么查询用户保存信息,否则查询所有字段)
	 * @param userId
	 * @return
	 */
	public List<Map<String,Object>> findUserPrototype(Map<String,Object> map);
	
	/**
	 * 获取页面分组对象
	 */
	public List<Map<String,Object>> findCodeByMoudleType(String moudleType);

	/**
	 * 获取省市
	 * @return
	 */
	public List<Map<String, Object>> findCitysByInface();

	public void updateUserArea(String parameter);

	public JSONObject findUserCitysByInface(String userId);

	public int queryUserPrototypeAll(String userId);

	public void addNoUserCoinLog(Map<String, Object> goldMap);

	public void updateUserGoldCoin(Map<String, Object> goldMap);

	public int findUserUpdateFlg(String parameter);

	public List<Map<String, String>> findUserPrototypeSelected(Map<String, String> condition);


	public int addUserPrototype2(org.json.JSONArray j, org.json.JSONObject jo,
			Map<String, Object> condition);
	
}
