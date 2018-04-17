package com.suyin.common;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

public class Utils {
	public static void fillResult(HttpServletRequest request, ModelMap result) {
		result.put("module", request.getParameter("module"));
		result.put("regtype", request.getParameter("regtype"));
		result.put("version", request.getParameter("version"));
		if(null!=request.getParameter("userId"))
		{
		    result.put("userid", request.getParameter("userId"));
		}
	}
}
