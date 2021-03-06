
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
import com.suyin.decorate.model.ExpDecorateUser;
import com.suyin.decorate.service.ExpDecorateUserService;
import com.suyin.decoratebuyorder.model.DecorateBuyOrder;
import com.suyin.decoratebuyorder.service.DecorateBuyOrderService;
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
	@Autowired
	private DecorateBuyOrderService decorateBuyOrderService;
    @Autowired
    private ExpDecorateUserService expDecorateUserService;
	/**
	 * 根据用户OPENID查询我的券
	 * @param request
	 * @return
	 */
	@RequestMapping("/findByUserVoucherList")
	public @ResponseBody ModelMap findByUserVoucherList(HttpServletRequest request){
		ModelMap result=new ModelMap();
		Map<String,Object> condition=new HashMap<String, Object>();
		String openId=request.getParameter("openid");
		String state=request.getParameter("state");
		Page page=new Page();
		if(StringUtils.isNotBlank(request.getParameter("page.showCount"))) 
			page.setShowCount(Integer.parseInt(request.getParameter("page.showCount")));
		if(StringUtils.isNotBlank(request.getParameter("page.currentPage")))
			page.setCurrentPage(Integer.parseInt(request.getParameter("page.currentPage")));
		condition.put("page", page); 
		condition.put("openid", openId);
		condition.put("state", state);
		result.put("args", condition);
		List<ExpDecorateUserVoucher> list=expDecorateVoucherService.findExpDecorateUserVoucherByPage(condition);	
		if(list.size()<1){
			result.put("message", "error");
		}else {
			result.put("data", list);
			result.put("message", "success");

		}

		return result;
	}
	/**
	 * 查询详情
	 * 购买券下单
	 * @return
	 */
	@RequestMapping("/findBuyDetial")
	public @ResponseBody ExpDecorateVoucher findBuyDetial(HttpServletRequest request){
		String id=request.getParameter("id");
		String openId=request.getParameter("openid");
		String orderNum=request.getParameter("orderNum");
		ExpDecorateVoucher exvoucher=expDecorateVoucherService.findDetial(id);
		if(null!=exvoucher){
			//新增订单
			DecorateBuyOrder entity=new DecorateBuyOrder();		
			entity.setOpenid(openId);
			entity.setOrderCode(orderNum);
			entity.setOrderPrice(exvoucher.getPrice());
			entity.setOrderName(exvoucher.getName());
			entity.setVoucherCode(Utils.getRandomString(12));
			entity.setOrderType(exvoucher.getType());
			entity.setOrderState(0);
			int result=decorateBuyOrderService.addDecorateBuyOrder(entity);
			if(result>0){
				//根据订单编号查询
				DecorateBuyOrder  order=this.decorateBuyOrderService.findByOrderNumInfo(orderNum);
				//查询用户信息
				ExpDecorateUser user=expDecorateUserService.findUserInfoByUserIdOrOpenId("", openId);
				//新增券至购买者账户
				ExpDecorateUserVoucher voucherUser=new ExpDecorateUserVoucher();
				voucherUser.setOpenid(openId);
				voucherUser.setOrderId(order.getOrderId());
				voucherUser.setVoucherId(exvoucher.getId());
				voucherUser.setVourcheCode(order.getVoucherCode());
				voucherUser.setState(0);
				voucherUser.setUseOpenid(user.getUseOpenid());
				expDecorateVoucherService.saveUserVoucher(voucherUser);
				//减少券的数量
				expDecorateVoucherService.updateRemNum(exvoucher.getId());
	
			}
		}
		return exvoucher;
	} 

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

