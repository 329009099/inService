package com.suyin.nouser.controller;


import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suyin.common.Utils;
import com.suyin.exp.model.Experience;
import com.suyin.nouser.model.NouserDiscuz;
import com.suyin.nouser.service.NouserDiscuzService;
import com.suyin.system.model.Page;
import com.suyin.system.util.Tools;


/**
 * 客户端用户注册信息操作
 * @author lz
 * @version 2015-8-28
 * @see NouserDiscuzController
 * @since
 */
@Controller
@RequestMapping("/nouserDiscuz")
public class NouserDiscuzController
{

	private final static Logger log = Logger.getLogger(NouserDiscuzController.class);

	@Autowired
	private NouserDiscuzService nouserDiscuzService;



	/**
	 * 评价信息新增
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/addDiscuz")
	public @ResponseBody
	ModelMap addDiscuz(HttpServletRequest request)
			throws Exception
	{
		ModelMap model = new ModelMap();
		try
		{
			NouserDiscuz entity = new NouserDiscuz();

			//商户id
			if(!Tools.isEmpty(request.getParameter("memberId"))){
				entity.setMemberId(Integer.valueOf(request.getParameter("memberId")));
			}
			//评分
			if(!Tools.isEmpty(request.getParameter("qcdScore"))){
				entity.setQcdScore(request.getParameter("qcdScore"));
			}
			//产品详情id
			if(!Tools.isEmpty(request.getParameter("detailId"))){
				entity.setDetailId(Integer.valueOf(request.getParameter("detailId")));
			}
			//用户id
			if(!Tools.isEmpty(request.getParameter("userId"))){
				entity.setUserId(request.getParameter("userId"));
			}
			//活动类型
			if(!Tools.isEmpty(request.getParameter("expType"))){
				entity.setExpType(Integer.valueOf(request.getParameter("expType")));
			}
			//产品id
			if(!Tools.isEmpty(request.getParameter("proId"))){
				entity.setProId(Integer.valueOf(request.getParameter("proId")));
			}
			//评价内容
			entity.setContent(filterOffUtf8Mb4(request.getParameter("content")));
			//图片路径
			entity.setPicUrl(request.getParameter("picUrl"));

			
			int n = nouserDiscuzService.addNouserDiscuz(entity);
			if (n == 1){
				model.put("message", "success");
			}
			else if(n==2){
				model.put("message", "reset");
			}else {
				model.put("message", "error");
			}
		}
		catch (Exception e)
		{
			model.put("message", "error");
			log.error("评价新增异常：" + e.getMessage());
			throw e;
		}
		return model;
	}
	/**
	 * 根据商户id查询评价信息
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/findDiscuss")
	public @ResponseBody  ModelMap findDiscuss(HttpServletRequest request)throws Exception
	{
		ModelMap modelMap  = new ModelMap();
		try
		{
			//产品id
			String proId=request.getParameter("proId");
			//活动详情id
			String detailId=request.getParameter("detailId");
			String currpage=request.getParameter("page.currentPage");

			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put("proId", proId);
			map.put("detailId", detailId);
			Page page = new Page();
			//查询商户评论总行数
			int count =nouserDiscuzService.findDiscussCount(map);
			List<NouserDiscuz>  list = new ArrayList<NouserDiscuz>();
			page.setTotalResult(count);
			page.setTotalPage(count%10==0?count/10:count/10+1);
			if(Integer.valueOf(currpage)==0){
				page.setCurrentPage(1);
				page.setShowCount(1);
			
			}else{
				page.setCurrentPage(Integer.valueOf(currpage));
				page.setShowCount(10);
				
			}
			map.put("page", page);
			modelMap.put("args", map);
			list= nouserDiscuzService.findDiscussByPage(map);
			if(Integer.valueOf(currpage)==0){
				modelMap.put("message", "success");
				modelMap.put("data", list);
				modelMap.put("discussCount", count);
			}else{
				modelMap.put("message", "success");

				modelMap.put("data", list);
			}


		}
		catch (Exception e)
		{
			e.printStackTrace();
			modelMap.put("message", "error");
			log.error("评价新增异常：" + e.getMessage());
			throw e;
		}
		return modelMap;
	}

	@RequestMapping(value="/findDiscussA")
	public @ResponseBody ModelMap findExpInIndex(HttpServletRequest request) throws Exception {
		ModelMap result=new ModelMap();
		Map<String,Object> condition=new HashMap<String, Object>();
		//产品id
		String proId=request.getParameter("proId");
		//活动详情id
		String detailId=request.getParameter("detailId");
		Page page=new Page();
		if(StringUtils.isNotBlank(request.getParameter("page.showCount"))) 
			page.setShowCount(Integer.parseInt(request.getParameter("page.showCount")));
		if(StringUtils.isNotBlank(request.getParameter("page.currentPage")))
			page.setCurrentPage(Integer.parseInt(request.getParameter("page.currentPage")));
		condition.put("page", page);        
		condition.put("proId", proId);
		condition.put("detailId", detailId);
		result.put("args", condition);

		List<NouserDiscuz>  list= nouserDiscuzService.findDiscussByPage(condition);
		if(list==null)
			result.put("message", "error");
		else {
			result.put("data", list);
			result.put("message", "success");
		}
		return result;
	}

	//    @RequestMapping(value = "/findDiscuss")
	//    public @ResponseBody
	//    ModelMap findDiscuss(HttpServletRequest request)
	//    		throws Exception
	//    {
	//    	ModelMap modelMap  = new ModelMap();
	//    	try
	//    	{
	//    		//产品id
	//    		String proId=request.getParameter("proId");
	//    		//活动详情id
	//    		String detailId=request.getParameter("detailId");
	//    		
	//    		//是否是第一页
	//    		String currpage=request.getParameter("currpage");
	//    		
	//    		Map<String, Object> map = new HashMap<String, Object>(); 
	//    		map.put("proId", proId);
	//    		map.put("detailId", detailId);
	//    		Page page = new Page();
	//    		
	//    		//查询商户评论总行数
	//    		int count =nouserDiscuzService.findDiscussCount(map);
	//    		if(Integer.valueOf(currpage)==0){
	//    			map.put("currpage", currpage);
	//    			map.put("pageSize", 3);
	//    		}else{
	//    			map.put("currpage", (Integer.valueOf(currpage)-1)*15);
	//    			map.put("pageSize", 2);
	//    			page.setShowCount(2);
	//    		}
	//    		page.setCurrentPage(Integer.valueOf(currpage));
	//    		map.put("page", page);
	//    		//查询评论列表
	//    		List<NouserDiscuz>  list= nouserDiscuzService.findDiscuss(map);
	//    		
	//    		
	//    		modelMap.put("message", "success");
	//    		modelMap.put("args", map);
	//    		modelMap.put("discussList", list);
	//    		modelMap.put("discussCount", count);
	//    		
	//    	}
	//    	catch (Exception e)
	//    	{
	//    		e.printStackTrace();
	//    		modelMap.put("message", "error");
	//    		log.error("评价新增异常：" + e.getMessage());
	//    		throw e;
	//    	}
	//    	return modelMap;
	//    }



	/**
	 * utf8是变长字符集，单个字符占用1～4个字节。
	 * mysql在选择utf8字符集时，最多只能存储3个字节的utf8字符，
	 * 如果想要保存任意的utf8字符，数据必须用utf8mb4字符集，
	 * 不能变更已选定的字符集，把输入中的4个字节的utf8字符全部过滤掉
	 * 
	 * @param text
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @see
	 */
	public static String filterOffUtf8Mb4(String text)
			throws UnsupportedEncodingException
	{

		byte[] bytes = text.getBytes("utf-8");

		ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
		try
		{

			int i = 0;

			while (i < bytes.length)
			{

				short b = bytes[i];

				if (b > 0)
				{

					buffer.put(bytes[i++ ]);

					continue;

				}

				b += 256;

				if ((b ^ 0xC0) >> 4 == 0)
				{

					buffer.put(bytes, i, 2);

					i += 2;

				}

				else if ((b ^ 0xE0) >> 4 == 0)
				{

					buffer.put(bytes, i, 3);

					i += 3;

				}

				else if ((b ^ 0xF0) >> 4 == 0)
				{

					i += 4;

				}
				else
				{
					buffer.put(bytes[i++ ]);

					continue;
				}

			}

			buffer.flip();

		}
		catch (Exception e)
		{
			log.error(e.getCause());
		}
		String reString = new String(buffer.array(), "utf-8");

		return reString;
	}
}
