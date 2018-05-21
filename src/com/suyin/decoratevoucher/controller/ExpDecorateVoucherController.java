
package com.suyin.decoratevoucher.controller;

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
import com.suyin.system.util.Constant;
import com.suyin.system.util.Tools;

import java.util.*;

import com.suyin.common.Utils;
import com.suyin.decorate.model.ExpDecorateOrder;
import com.suyin.decoratevoucher.model.*;
import com.suyin.decoratevoucher.service.*;


/**
 * 禧居家居福利券设置
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/indecoratevoucher")
public class ExpDecorateVoucherController{

    private final static Logger log=Logger.getLogger(ExpDecorateVoucherController.class);
    @Autowired
    private ExpDecorateVoucherService expDecorateVoucherService;
   
    /**
     * 查询详情
     * @return
     */
    @RequestMapping("/findDetial")
    public @ResponseBody ExpDecorateVoucher findDetial(HttpServletRequest request){
		String id=request.getParameter("id");
    	ExpDecorateVoucher exvoucher=expDecorateVoucherService.findDetial(id);
    	return exvoucher;
    }
    /**
     * 
     * 首页板块显示
     * @param request
     * @return 
     * @see
     */
    @RequestMapping("/findTwo")
    public @ResponseBody ModelMap findTwo(HttpServletRequest request) {
        Map<String,Object> condition=new HashMap<String, Object>();
        ModelMap result=new ModelMap();       
        Utils.fillResult(request, result);
        Page page=new Page();
        page.setShowCount(6);
        page.setCurrentPage(1);
        condition.put("page", page);
        ModelMap data=new ModelMap();	
        //装载福利券
        condition.put("type", 0);
        data.put("0", this.expDecorateVoucherService.findExpDecorateVoucherByPage(condition));
        //装载体验券
        condition.put("type", 1);                   
        data.put("1", this.expDecorateVoucherService.findExpDecorateVoucherByPage(condition));
        result.put("data", data);
        //装载优惠券
        condition.put("type", 2);                   
        data.put("2", this.expDecorateVoucherService.findExpDecorateVoucherByPage(condition));
        result.put("data", data);
        result.put("message", "success");
        return result;
    }

    /**
     * 读取列表
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value = "/findVoucheList")
    public @ResponseBody Map<String, Object> findForExpDecorateVoucherAll(HttpServletRequest request) {
    	 ModelMap result=new ModelMap();
	        Map<String,Object> condition=new HashMap<String, Object>();
	        Page page=new Page();
	        if(StringUtils.isNotBlank(request.getParameter("page.showCount"))) 
	            page.setShowCount(Integer.parseInt(request.getParameter("page.showCount")));
	        if(StringUtils.isNotBlank(request.getParameter("page.currentPage")))
	            page.setCurrentPage(Integer.parseInt(request.getParameter("page.currentPage")));
	        condition.put("page", page); 
	        result.put("args", condition);
			List<ExpDecorateVoucher> list=expDecorateVoucherService.findExpDecorateVoucherByPage(condition);	
			if(list.size()<1){
	            result.put("message", "error");
	        }else {
	            result.put("data", list);
	            result.put("message", "success");
	       
	        }

		return result;
    }

}

