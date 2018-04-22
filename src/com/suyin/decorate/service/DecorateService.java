package com.suyin.decorate.service;

import com.suyin.decorate.model.Decorate;

public interface DecorateService {

	/**
	 * 通过id查询活动信息
	 * @param id
	 * @return
	 */
	 public Decorate findDecorateById(String id);
}
