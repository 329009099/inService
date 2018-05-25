
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
import com.suyin.decoratebuyorder.model.*;
import com.suyin.decoratebuyorder.service.*;


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

