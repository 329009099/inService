package com.suyin.find.mapper;

import java.util.List;
import java.util.Map;

/**
 * 发现 tab页的接口
 * @author Administrator
 *
 */
public interface FindMapper {

	/**查找折扣,优惠*/
	public List<Map<String,String>> findThemeOrDiscountByPage(Map<String,Object> condition);
}
