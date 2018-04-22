package com.suyin.decorate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.suyin.decorate.mapper.DecorateMapper;
import com.suyin.decorate.model.Decorate;
import com.suyin.decorate.service.DecorateService;

@Service
public class DecorateServiceImpl implements DecorateService {

	@Autowired
	private DecorateMapper decorateMapper;
	
	@Override
	public Decorate findDecorateById(String id) {
		return decorateMapper.findDecorateById(id);
	}

}
