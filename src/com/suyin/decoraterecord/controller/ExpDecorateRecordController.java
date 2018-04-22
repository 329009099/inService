
package com.suyin.decoraterecord.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suyin.system.model.Page;
import com.suyin.system.util.CommLogUtil;
import com.suyin.system.util.Constant;
import com.suyin.system.util.Tools;

import java.util.*;

import com.suyin.common.Utils;
import com.suyin.decoraterecord.model.*;
import com.suyin.decoraterecord.service.*;


/**
 * 邀请记录查询
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/indecoraterecord")
public class ExpDecorateRecordController{

	private final static Logger log=Logger.getLogger(ExpDecorateRecordController.class);
	@Autowired
	private ExpDecorateRecordService expDecorateRecordService;

	/**
	 * 首页
	 * @return 
	 * @see
	 */
	@RequestMapping(value="/index")
	public ModelAndView index() {

		return new ModelAndView("expdecoraterecord/index");
	}
	/**
	 * 查询我的邀请
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/findInvite")
	public @ResponseBody Map<String,Object>findInviteDecorateByPage(HttpServletRequest request){
		 ModelMap result=new ModelMap();
	        Map<String,Object> condition=new HashMap<String, Object>();
	        String openid=request.getParameter("openid");
	
	        Page page=new Page();
	        if(StringUtils.isNotBlank(request.getParameter("page.showCount"))) 
	            page.setShowCount(Integer.parseInt(request.getParameter("page.showCount")));
	        if(StringUtils.isNotBlank(request.getParameter("page.currentPage")))
	            page.setCurrentPage(Integer.parseInt(request.getParameter("page.currentPage")));
	        condition.put("page", page); 
	        condition.put("openid", openid);
	        result.put("args", condition);
			List<Map<String,Object> > list=expDecorateRecordService.findInviteDecorateByPage(condition);	
			if(list.size()<1){
	            result.put("message", "error");
	        }else {
	            result.put("data", list);
	            result.put("message", "success");
	       
	        }

		return result;
	}




	/**
	 * 跳转添加页面
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/jumpAdd")
	public ModelAndView jumpExpDecorateRecordAdd(HttpServletRequest request) {
		ModelMap map=new ModelMap();

		return new ModelAndView("expdecoraterecord/save",map);
	}

	/**
	 * 跳转修改页面 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/jumpEdit")
	public ModelAndView jumpExpDecorateRecordEdit(HttpServletRequest request) {
		ModelMap map=new ModelMap();
		try
		{

			if(Tools.notEmpty(request.getParameter("id"))){  

				ExpDecorateRecord entity=new ExpDecorateRecord();
				entity.setRecordId(Integer.parseInt(request.getParameter("id")));
				entity=expDecorateRecordService.findExpDecorateRecordById(entity);
				map.put("expdecoraterecord",entity);

			}
		}
		catch (Exception e)
		{

			log.error("Controller Error ExpDecorateRecordController-> jumpExpDecorateRecordEdit  " + e.getMessage());
		}
		return new ModelAndView("expdecoraterecord/edit",map);
	}

	/**
	 * 信息保存
	 * Description: <br>
	 * @param 
	 * @return 
	 * @see
	 */
	@RequestMapping(value = "/add")
	public @ResponseBody Map<String, Object> saveExpDecorateRecordInfo(ExpDecorateRecord entity) {
		ModelMap map=new ModelMap();
		try
		{

			map.put("result",expDecorateRecordService.addExpDecorateRecord(entity));
		}
		catch (Exception e)
		{
			log.error("Controller Error ExpDecorateRecordController-> saveExpDecorateRecordInfo " + e.getMessage());
		}
		return map;
	}
	/**
	 * 信息修改
	 * Description: <br>
	 * @param 
	 * @return 
	 * @see
	 */
	@RequestMapping(value = "/update")
	public @ResponseBody Map<String, Object> updateExpDecorateRecordById(ExpDecorateRecord entity) {
		ModelMap map=new ModelMap();
		try
		{            
			map.put("result",expDecorateRecordService.updateExpDecorateRecord(entity));
		}
		catch (Exception e)
		{
			log.error("Controller Error ExpDecorateRecordController-> updateExpDecorateRecordById  " + e.getMessage());
		}
		return map;
	}

	/**
	 * 信息删除
	 * @param 
	 * @return
	 */
	@RequestMapping(value = "/delete")
	public @ResponseBody Map<String, Object> deleteExpDecorateRecordById(String id) {

		ModelMap map=new ModelMap();
		try
		{
			if(Tools.notEmpty(id)){

				map.put("result",expDecorateRecordService.deleteExpDecorateRecord(id));
			}  
		}
		catch (Exception e)
		{
			log.error("Controller Error ExpDecorateRecordController-> deleteExpDecorateRecordById  " + e.getMessage());
		}

		return map;
	}


}

