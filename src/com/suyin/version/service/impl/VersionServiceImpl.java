package com.suyin.version.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.suyin.expshare.mapper.ExpShareMapper;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suyin.system.util.Md5Util;
import com.suyin.version.mapper.VersionMapper;
import com.suyin.version.model.Version;
import com.suyin.version.service.VersionService;

import java.util.*;

import com.suyin.expshare.model.*;
import com.suyin.expshare.service.*;



@Transactional
@Service("VersionService")
public class VersionServiceImpl implements VersionService{

    private final static Logger log=Logger.getLogger(VersionServiceImpl.class);

    @Autowired
    private VersionMapper versionMapper;

	
	@Override
	public JSONObject findVersionInfo(Map<String, String> map) {
		Version version = versionMapper.findVersionInfo(map);
		JSONObject jsonObject = new JSONObject();
		try {
			if(!map.get("versionName").equals(version.getVersionName())){
				jsonObject.put("data", version);
				jsonObject.put("message", "success");
			}else{
				jsonObject.put("message", "isNew");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			jsonObject.put("message", "error");
		}
//		
		return jsonObject;
	    
	} 

   

}
