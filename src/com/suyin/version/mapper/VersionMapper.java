package com.suyin.version.mapper;

import java.util.*;
import com.suyin.version.model.Version;




public interface VersionMapper {

	/**
	 * 根据客户端版本名称客户端类型查询新版本信息
	 */
	public Version findVersionInfo(Map<String, String> map);

}
