package com.suyin.participator.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suyin.participator.model.Participator;
import com.suyin.participator.service.ParticipatorService;
import com.suyin.system.model.Page;

@Controller
@RequestMapping("/participator")
public class ParticipatorController {

	@Autowired
	private ParticipatorService participatorService;
	
	/**
	 * 获取标兵列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findParticipatorList")
	public @ResponseBody ModelMap findParticipatorList(
			Participator participator, HttpServletRequest request) {
		ModelMap result = new ModelMap();
		Map<String, Object> condition = new HashMap<String, Object>();
		String type = request.getParameter("type");// 分类 0：标兵 1：警嫂 无：全部
		String nameOrNumber = request.getParameter("keyword"); // 姓名或编号
		if (null != nameOrNumber && !"undefined".equals(nameOrNumber)) {
			condition.put("nameOrNumber", nameOrNumber);
		}
		condition.put("type", type);
		Page page = new Page();
		if (StringUtils.isNotBlank(request.getParameter("page.showCount"))) {
			page.setShowCount(Integer.parseInt(request
					.getParameter("page.showCount")));
		}
		if (StringUtils.isNotBlank(request.getParameter("page.currentPage"))) {
			page.setCurrentPage(Integer.parseInt(request
					.getParameter("page.currentPage")));
		}
		condition.put("page", page);
		result.put("args", condition);
		List<Map<String, Object>> list = this.participatorService.findParticipatorList(condition);
		if (list.size() < 1) {
			result.put("message", "error");
		} else {
			result.put("data", list);
			result.put("message", "success");
		}
		return result;
	}

	/**
	 * 获取标兵详情
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findParticipatorDetailById")
	public @ResponseBody Participator findParticipatorDetailById(
			Participator participator) {
		return this.participatorService.findParticipatorDetailById(Integer
				.valueOf(participator.getId()));
	}

	/**
	 * 获取距上一名票数差
	 * 
	 * @param participator
	 * @return
	 */
	@RequestMapping(value = "/getPoorVotes")
	public @ResponseBody Participator getPoorVotes(Participator participator) {
		return this.participatorService.getPoorVotes(participator.getId());
	}

	/**
	 * 获取排名列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findRanKingList")
	public @ResponseBody ModelMap findRanKingList(HttpServletRequest request) {
		ModelMap result = new ModelMap();
		Map<String, Object> condition = new HashMap<String, Object>();
		String type = request.getParameter("type");
		condition.put("type", type);
		Page page = new Page();
		if (StringUtils.isNotBlank(request.getParameter("page.showCount"))) {
			page.setShowCount(Integer.parseInt(request
					.getParameter("page.showCount")));
		}
		if (StringUtils.isNotBlank(request.getParameter("page.currentPage"))) {
			page.setCurrentPage(Integer.parseInt(request
					.getParameter("page.currentPage")));
		}
		condition.put("page", page);
		result.put("args", condition);
		List<Map<String, Object>> list = this.participatorService
				.findRanKingList(condition);
		if (list.size() < 1) {
			result.put("message", "error");
		} else {
			result.put("data", list);
			result.put("message", "success");
		}
		return result;
	}

}
