package com.suyin.find.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suyin.find.mapper.FindMapper;
import com.suyin.find.service.FindService;

@Service
public class FindServiceImpl implements FindService {
	
	@Autowired
	private FindMapper findMapper;

	/**查找折扣,优惠,必须提供type
	 * type==4是主题，type==3是折扣
	 * */
	@Override
	public List<Map<String, String>> findThemeOrDiscountByPage(
			Map<String, Object> condition) {
		return this.findMapper.findThemeOrDiscountByPage(condition);
	}

	
	
	
}
