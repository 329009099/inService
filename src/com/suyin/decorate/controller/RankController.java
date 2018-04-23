package com.suyin.decorate.controller;

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

import com.suyin.decorate.model.ExpDecorateUser;
import com.suyin.decorate.service.ExpDecorateUserService;
import com.suyin.decorate.service.RankService;
import com.suyin.system.model.Page;

/**
 * 排名接口
 * @author chun
 *
 */
@Controller
@RequestMapping("/interfarank")
public class RankController {

	@Autowired
	private RankService rankService;
	
	@Autowired
	private ExpDecorateUserService decorateRecordService;
	/**
	 * 查找我的排名信息
	 * @return
	 */
	@RequestMapping("/findMyRankInfo")
	public @ResponseBody ModelMap findMyRankInfo(HttpServletRequest request) {
		
		ModelMap result=new ModelMap();
		String expId=request.getParameter("id");//活动id
		String openid=request.getParameter("openid");
		ExpDecorateUser expDecorateUser = this.decorateRecordService.findUserInfoByUserIdOrOpenId("",openid);
		Integer rankNumber = this.rankService.getMyRankInfo(openid);
		result.put("rankNumber", rankNumber);
		result.put("expDecorateUser", expDecorateUser);
		return result;
	}
	
	/**
	 * 查找整体排名信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findAllRankInfo")
	public @ResponseBody ModelMap findAllRankInfo(HttpServletRequest request) {
		ModelMap result = new ModelMap();
		Map<String, Object> condition = new HashMap<String, Object>();
		String expId=request.getParameter("id");//活动id
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
		List<Map<String, Object>> list = this.rankService.findAllRankInfo(condition);
		if (list.size() < 1) {
			result.put("message", "error");
		} else {
			result.put("data", list);
			result.put("message", "success");
		}
		return result;
	}

}
