package com.suyin.find.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suyin.common.Utils;
import com.suyin.find.service.FindService;
import com.suyin.system.model.Page;
import com.suyin.system.util.CommLogUtil;
import com.suyin.system.util.ConstantLogUtil;
import com.suyin.system.util.PropertiesUtils;


@Controller
@RequestMapping("/find")
public class FindController {

    @Autowired
    private FindService findService;

    @RequestMapping("/findTOD")
    public @ResponseBody ModelMap findThemeOrDiscount(HttpServletRequest request){
        Map<String,Object> condition=new HashMap<String, Object>();
        condition.put("type", request.getParameter("type"));
        ModelMap result=new ModelMap();
        //condition.put("type", "4");
        Page page=new Page();
        if(StringUtils.isNotBlank(request.getParameter("page.showCount"))) 
            page.setShowCount(Integer.parseInt(request.getParameter("page.showCount")));
        if(StringUtils.isNotBlank(request.getParameter("page.currentPage")))
            page.setCurrentPage(Integer.parseInt(request.getParameter("page.currentPage")));
        condition.put("page", page);
        Utils.fillResult(request, result);
        result.put("data", this.findService.findThemeOrDiscountByPage(condition));
        result.put("args", condition);
        result.put("message", "success");
        result.put("baseUrl", PropertiesUtils.getValByKey("baseUrl", "/sms.properties"));
        if(page.getCurrentPage()<2){
            if(null != result.get("userid"))
                {
                    CommLogUtil.saveOptLog(result.get("regtype").toString(), ConstantLogUtil.FIND_LIST, ConstantLogUtil.FIND_LIST_NUMBER, ConstantLogUtil.FIND_LIST_NAME, result.get("userid").toString());
                }
            }
        return result;
    }
}
