package com.suyin.version.service;

import java.util.*;
import net.sf.json.JSONObject;

public interface VersionService{

	/**
	 * 根据客户端版本名称客户端类型查询新版本信息
	 */
	public JSONObject findVersionInfo(Map<String, String> map);

   
}
