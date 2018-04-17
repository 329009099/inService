package com.suyin.simple.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suyin.simple.mapper.SyncDataMapper;
import com.suyin.simple.model.DataModel;
import com.suyin.simple.service.SyncDataService;

@Transactional
@Service("syncDataService")
public class SyncDataServiceImpl implements SyncDataService {

	@Autowired
	private SyncDataMapper syncDataMapper;
	
	public void syncData() {
		List<Map<String, Object>> list=syncDataMapper.findOldUserByAll();
		DataModel dataModel;
		Map<String, Object> data=new HashMap<String,Object>();
		for(Map map:list){
			dataModel=new DataModel();
			dataModel.setData(data);
			dataModel.setUserPhone(map.get("tel").toString());
			syncDataMapper.addNouser(dataModel);
			Integer point=Integer.parseInt(map.get("point").toString());
			if(point<=99){
				dataModel.setCoin(10);
			}else if(point<=499 && point>=100){
				dataModel.setCoin(20);
			}else if(point<=999 && point>=500){
				dataModel.setCoin(50);
			}else if(point>=1000){
				dataModel.setCoin(100);
			}
			syncDataMapper.addNouserStaticPrototype(dataModel);
		}
	}

	
}
