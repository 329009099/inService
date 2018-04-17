package com.suyin.find.service;

import java.util.List;
import java.util.Map;

public interface FindService {
	/**查找折扣,优惠*/
	public List<Map<String,String>> findThemeOrDiscountByPage(Map<String,Object> condition);
}
