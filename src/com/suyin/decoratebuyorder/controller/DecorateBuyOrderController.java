
package com.suyin.decoratebuyorder.controller;

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

import com.suyin.system.model.Page;
import com.suyin.system.util.Tools;
import com.suyin.decorate.service.ExpDecorateUserService;
import com.suyin.decoratebuyorder.model.*;
import com.suyin.decoratebuyorder.service.*;
import com.suyin.decoratemessage.model.DecorateMessage;
import com.suyin.decoratemessage.service.DecorateMessageService;
import com.suyin.decoratevoucher.service.ExpDecorateVoucherService;


/**
 * 微信支付订单
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/indecoratebuyorder")
public class DecorateBuyOrderController{

    private final static Logger log=Logger.getLogger(DecorateBuyOrderController.class);
    @Autowired
    private DecorateBuyOrderService decorateBuyOrderService;
    @Autowired
    private DecorateMessageService decorateMessageService;
    @Autowired
    private ExpDecorateUserService decorateUserService;
	@Autowired
	private ExpDecorateVoucherService expDecorateVoucherService;
    /**
     * 根据ID查询订单
     * @param request
     * @return
     */
    @RequestMapping(value="/findOrderByIdInfo")
    public @ResponseBody DecorateBuyOrder findOrderByIdInfo(HttpServletRequest request){
    	String id=request.getParameter("id");
    	return decorateBuyOrderService.findOrderByIdInfo(id);
    }
    /**
     * 读取列表
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value = "/findUserOrderList")
    public @ResponseBody ModelMap  findForDecorateBuyOrderAll(HttpServletRequest request) {
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
 		List<DecorateBuyOrder> list=decorateBuyOrderService.findDecorateBuyOrderByPage(condition);	
 		if(list.size()<1){
            result.put("message", "error");
        }else {
            result.put("data", list);
            result.put("message", "success");       
        }
        return result;
    }

    /**
     * 修改订单状态
     * @param request
     */
    @RequestMapping(value = "/orderState")
    public @ResponseBody void orderState(HttpServletRequest request) {
    	String tradeNo=request.getParameter("tradeNo");
    	String openid=request.getParameter("openid");
    	DecorateMessage entity=new DecorateMessage();
    	DecorateBuyOrder order=decorateBuyOrderService.findByOrderNumInfo(tradeNo);
    	if(null!=order){
	    	decorateBuyOrderService.orderUpdateState(order.getOrderCode());
	    	//更新当前购买用户为体验用户 0 否：1是    isExpUser
	    	decorateUserService.updateExpDecorateUserByOpenId(openid);
	    	//组织修改券状态所需的参数
	    	Map<String,Object>params=new HashMap<String, Object>();
	    	params.put("orderId", order.getOrderId());
	    	params.put("vourcheCode", order.getVoucherCode());
	    	//修改券支付状态
	    	expDecorateVoucherService.updateVoucherPayState(params);
			//增加Message购买信息至用户中心我的消息
	    	entity.setContent("恭喜你成功购买了福利券:"+order.getOrderName()+"");
	    	entity.setOpenid(openid);
	    	entity.setSendEntity(2);
	    	entity.setSendModuleName("购买福券");
    	}else{
    	  	entity.setContent("恭喜你成功购买了福利券失败");
	    	entity.setOpenid(openid);
	    	entity.setSendEntity(2);
	    	entity.setSendModuleName("购买福券");
    	}
    	decorateMessageService.addDecorateMessage(entity);
    }
    /**
     * 信息保存
     * Description: <br>
     * @param 
     * @return 
     * @see
     */
    @RequestMapping(value = "/saveOrderByInfo")
    public @ResponseBody Map<String, Object> saveDecorateBuyOrderInfo(DecorateBuyOrder entity) {
        ModelMap map=new ModelMap();
        try
        {
            
            map.put("result",decorateBuyOrderService.addDecorateBuyOrder(entity));
        }
        catch (Exception e)
        {
            log.error("Controller Error DecorateBuyOrderController-> saveDecorateBuyOrderInfo " + e.getMessage());
        }
        return map;
    }
    
    /**
     * 支付成功后根据订单id修改状态为 已支付
     * 信息修改
     * Description: <br>
     * @param 
     * @return 
     * @see
     */
    @RequestMapping(value = "/updateDecorateBuyOrderById")
    public @ResponseBody Map<String, Object> updateDecorateBuyOrderById(DecorateBuyOrder entity) {
        ModelMap map=new ModelMap();
        try
        {            
            map.put("result",decorateBuyOrderService.updateDecorateBuyOrder(entity));
        }
        catch (Exception e)
        {
            log.error("Controller Error DecorateBuyOrderController-> updateDecorateBuyOrderById  " + e.getMessage());
        }
        return map;
    }

    /**
     * 信息删除
     * @param 
     * @return
     */
    @RequestMapping(value = "/deleteDecorateBuyOrderById")
    public @ResponseBody Map<String, Object> deleteDecorateBuyOrderById(String id) {

        ModelMap map=new ModelMap();
        try
        {
            if(Tools.notEmpty(id)){
                
                map.put("result",decorateBuyOrderService.deleteDecorateBuyOrder(id));
            }  
        }
        catch (Exception e)
        {
            log.error("Controller Error DecorateBuyOrderController-> deleteDecorateBuyOrderById  " + e.getMessage());
        }
 
        return map;
    }


}

