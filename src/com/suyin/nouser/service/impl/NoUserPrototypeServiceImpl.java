package com.suyin.nouser.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;













import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;












import com.suyin.nouser.mapper.NoUserPrototypeMapper;
import com.suyin.nouser.model.NoUserPrototype;
import com.suyin.nouser.service.NoUserPrototypeService;

@Transactional
@Service("noUserPrototypeService")
public class NoUserPrototypeServiceImpl implements NoUserPrototypeService {

	@Autowired
	NoUserPrototypeMapper noUserPrototypeMapper;

	@Override
	public Integer addNoUserPrototype(String jsonArray,String upFlag)  {
		try {
			JSONArray array = new JSONArray(jsonArray);
			JSONObject iObj;
			NoUserPrototype noUserPrototype;
			 for(int i=0;i<array.length();i++)
			 {
				 iObj= array.getJSONObject(i);
				 noUserPrototype=new NoUserPrototype();
				 noUserPrototype.setUser_id(Integer.parseInt(iObj.get("uid").toString()));
				 noUserPrototype.setIs_selected(1);
				 noUserPrototype.setCreate_time(new Date());
				 noUserPrototype.setDictionary_id(Integer.parseInt(iObj.get("did").toString()));
				 if(StringUtils.isNotBlank(upFlag))
				 {
					 noUserPrototype.setPrototype_id(Integer.parseInt(iObj.get("prototype_id").toString()));
					 noUserPrototypeMapper.updateNoUserPrototype(noUserPrototype);
				 }
				 else
				 {
					 noUserPrototype.setParent_id(iObj.isNull("parent_id")?-1:Integer.parseInt(iObj.get("parent_id").toString()));
					 noUserPrototype.setLevel(iObj.isNull("level")?2:Integer.parseInt(iObj.get("level").toString()));
					 noUserPrototype.setCode(iObj.get("code").toString());
					 noUserPrototypeMapper.addNoUserPrototype(noUserPrototype);
				 }
	         }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 1;
	}

	@Override
	public Integer deleteNoUserPrototypeByUserId(Map<String,Object> map) {
		return noUserPrototypeMapper.deleteNoUserPrototypeByUserId(map);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map<String, Object>> findUserPrototype(Map<String,Object> paramMap) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = noUserPrototypeMapper.findUserPrototype(paramMap);
		Map<String,Object> param=new HashMap<String,Object>();
		if(null!=paramMap.get("userId")){
			param.put("userId", paramMap.get("userId"));
		}
		for (Map map : list) {
			param.put("dictionary_id", map.get("dictionary_id"));
			map.put("isChildren", noUserPrototypeMapper.findIsChildren(map.get("dictionary_id").toString()));
			map.put("options", noUserPrototypeMapper.findUserPrototypeOption(param));
		}
		return list;
	}

	@Override
	public List<Map<String,Object>> findCodeByMoudleType(String moudleType) {
		try {
			return  noUserPrototypeMapper.findCodeByMoudleType(moudleType);
		} catch (Exception e) {
			
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> findCitysByInface() {
		
		return noUserPrototypeMapper.findCitysByInface();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void updateUserArea(String parameter) {
		net.sf.json.JSONObject jsonObject = net.sf.json.JSONObject.fromObject(parameter);
		Map map = new HashMap();
		map.put("city_id", jsonObject.get("city_id"));
		map.put("provin_id", jsonObject.get("provin_id"));
		map.put("user_id", jsonObject.get("uid"));
		noUserPrototypeMapper.updateUserArea(map);
	}
	private void updateUserArea(net.sf.json.JSONObject jsonObject) {
		Map map = new HashMap();
		map.put("city_id", jsonObject.get("city_id"));
		map.put("provin_id", jsonObject.get("provin_id"));
		map.put("user_id", jsonObject.get("uid"));
		noUserPrototypeMapper.updateUserArea(map);
	}

	@SuppressWarnings({ "static-access", "rawtypes" })
	@Override
	public net.sf.json.JSONObject findUserCitysByInface(String userId) {
		net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
		Map map = noUserPrototypeMapper.findUserCitysByInface(Integer.parseInt(userId));
		return jsonObject.fromObject(map);
	}

	@Override
	public int queryUserPrototypeAll(String userId) {
		
		return noUserPrototypeMapper.queryUserPrototypeAll(Integer.parseInt(userId));
	}

	@Override
	public void addNoUserCoinLog(Map<String, Object> goldMap) {
		
		noUserPrototypeMapper.addNoUserCoinLog(goldMap);
	}

	@Override
	public void updateUserGoldCoin(Map<String, Object> goldMap) {
		
		noUserPrototypeMapper.updateUserGoldCoin(goldMap);
	}

	@Override
	public int findUserUpdateFlg(String userId) {
		
		return noUserPrototypeMapper.findUserUpdateFlg(Integer.parseInt(userId));
	}

	@Override
	public List<Map<String, String>> findUserPrototypeSelected(
			Map<String, String> condition) {
		return this.noUserPrototypeMapper.findUserPrototypeSelected(condition);
	}


	@Override
	public int addUserPrototype2(org.json.JSONArray jsa,
			org.json.JSONObject jso, Map<String, Object> condition) {
		this.noUserPrototypeMapper.deleteAllLevelOneNoUserPrototypeByUserId(condition);
		this.updateUserArea(jso.toString());
		List<Map<String, Object>> list=new ArrayList<Map<String,Object>>();
		JSONObject jso1=null;
		
		try {
			for(int i=0;i<jsa.length();i++) {
				jso1=jsa.getJSONObject(i);
				Map<String,Object> m=new HashMap<String, Object>();
				m.put("dictionary_id", jso1.get("did"));
				m.put("parent_id", jso1.get("parent_id"));
				m.put("userId", jso1.get("uid")); 
				m.put("code", jso1.get("code")); 
				list.add(m);
			}
		} catch (JSONException e) {
			throw new RuntimeException(e.getMessage());
		}
		this.noUserPrototypeMapper.addUserProperties(list);
		return 0;
	}


}
