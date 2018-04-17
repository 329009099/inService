package com.suyin.simple.mapper;

import java.util.List;
import java.util.Map;

import com.suyin.simple.model.DataModel;

public interface SyncDataMapper {
	public List<Map<String, Object>> findOldUserByAll();

	public Integer addNouser(DataModel dataModel);
	
	public Integer addNouserStaticPrototype(DataModel dataModel);
}
