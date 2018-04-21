package com.suyin.decorate.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suyin.decorate.model.Decorate;
import com.suyin.decorate.service.DecorateService;

@Controller
@RequestMapping("/indecorate")
public class DecorateController {
	
	@Autowired
	private DecorateService decorateService;
	
	/**
	 * 通过活动ID获取活动信息
	 * @return
	 */
	@RequestMapping("/findDecorateById")
	public @ResponseBody Decorate findDecorateById(HttpServletRequest request) {
		Decorate decorate = decorateService.findDecorateById(1);
		return decorate;
	}

}
