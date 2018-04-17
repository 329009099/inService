package com.suyin.about.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suyin.about.mapper.AboutMapper;
import com.suyin.about.service.AboutService;

@Transactional
@Service("aboutService")
public class AboutServiceImpl implements AboutService {
	private final Logger log=Logger.getLogger(this.getClass());
	
	@Autowired
	AboutMapper aboutMapper;
	/**
	 * 查询合作商家列表
	 */
	@Override
	public List<Map<String, Object>> findMemberByAll(Map<String, Object> map) {
		try {
			return aboutMapper.findMemberByAll(map);
		} catch (Exception e) {
			  log.error("Service Error AboutServiceImpl-> findMemberByAll" + e.getMessage());
			  throw new RuntimeException();
		}
	}
	/**
	 * 查询 体验店列表
	 */
	@Override
	public List<Map<String, Object>> findStoreByAll(Map<String, Object> map) {
		try{
			return aboutMapper.findStoreByAll(map);
			
		}catch(Exception e){
			log.error("Service Error AboutServiceImpl-> findStoreByALL"+e.getMessage());
			throw new RuntimeException();
		}


	}

}
