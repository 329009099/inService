package com.suyin.decorate.mapper;

import com.suyin.decorate.model.Decorate;

public interface DecorateMapper {

	/**
	 * 通过id查询活动信息
	 * @param id
	 * @return
	 */
	 public Decorate findDecorateById(Integer id);
}
