package com.suyin.exp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suyin.common.Utils;
import com.suyin.exp.model.Experience;
import com.suyin.exp.service.ExperienceService;
import com.suyin.expzhuan.service.ExperienceTaskService;
import com.suyin.other.mapper.OtherMapper;
import com.suyin.other.service.OtherService;
import com.suyin.system.model.Page;
import com.suyin.system.util.CommLogUtil;
import com.suyin.system.util.Constant;
import com.suyin.system.util.ConstantLogUtil;

/**
 * 该controller负责所有活动的查询，并且只负责活动查询
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/exp")
public class ExperienceController {

    @Autowired
    private ExperienceService experienceService;
    @Autowired
    private ExperienceTaskService experienceZhuanService;
    @Autowired
    private OtherService otherService;

    /**
     * 一步把首页要的所有内容都查出来
     * @param request
     * @return
     */
    @RequestMapping(value="/index")
    public @ResponseBody ModelMap index(HttpServletRequest request) {
        ModelMap result=new ModelMap();
        Utils.fillResult(request, result);
        String cityId=request.getParameter("cityId");
        if(StringUtils.isEmpty(cityId)){
            result.put("cityId", Constant.DEFAULT_CITY);
        }else{
            result.put("cityId",cityId);
        }
        result.put("exp", this.findExp(result));
        result.put("task", this.findExpTask(result));
        result.put("advs", this.findAdvs(result));
        return result;
    }


    @RequestMapping(value="/findExpById",method=RequestMethod.GET)
    public @ResponseBody ModelMap findExpById(Experience exp, HttpServletRequest request) {
        ModelMap result=new ModelMap();
        Utils.fillResult(request, result);
        result.put("args", exp);
        Experience e=this.experienceService.findExpById(exp);
        //有两种情况下e==null。1：数据库查询出错了；2.找不到结果（但是我们暴露出去的活动id一定是可以查找到结果的）
        if(e==null) {
            result.put("message", "error");
        }else {
            result.put("data", e);
            result.put("message", "success");
        }
        return result;
    }

    /**
     * 首页0元试，抽奖乐，人气王，金币兑换显示两个产品,以及显示更多该类别的产品, 在request中有属性isTwo说明是指搜索该类别前两个
     * 没有就是搜索该类别
     * @param type 表示是那个类别
     * @param page 表示是第几页
     */
    @RequestMapping(value="/findTwo")
    public @ResponseBody ModelMap findTwo(HttpServletRequest request) {
        ModelMap result=new ModelMap();
        
        Utils.fillResult(request, result);
        String cityId=request.getParameter("cityId");
        if(StringUtils.isEmpty(cityId)){
            result.put("cityId", Constant.DEFAULT_CITY);
        }else{
            result.put("cityId",cityId);
        }
        result.put("exp", findExp(result));
        return result;
    }


    private ModelMap findExp(Map<String, Object> results) {
        Experience exp=new Experience();
        exp.setPage(new Page());
        exp.getPage().setShowCount(2);
        exp.getPage().setCurrentPage(1);
        ModelMap result=new ModelMap();
        ModelMap data=new ModelMap();
        //平台类型
        exp.setRegType(results.get("regtype").toString());
        //城市id
        exp.setCityId(results.get("cityId").toString());
        exp.setExpType(0);
        data.put("0", this.experienceService.findExperienceByTypeByPage(exp));
        exp.setExpType(1);
        data.put("1", this.experienceService.findExperienceByTypeByPage(exp));
        exp.setExpType(2);
        data.put("2", this.experienceService.findExperienceByTypeByPage(exp));
        exp.setExpType(3);
        data.put("3", this.experienceService.findExperienceByTypeByPage(exp));
        result.put("data", data);
        result.put("message", "success");
        //首页日志记录
        CommLogUtil.saveOptLog(results.get("regtype").toString(), ConstantLogUtil.HOME, ConstantLogUtil.HOME_NUMBER, ConstantLogUtil.HOME_NAME, results.get("userid").toString());
        return result;
    }


    private ModelMap findExpTask(Map<String,Object>results) {
        Map<String,Object> condition=new HashMap<String, Object>();
        condition.put("exp_type", 0);
        Page page=new Page();
        page.setShowCount(2);
        page.setCurrentPage(1);
        condition.put("page", page);
        ModelMap result=new ModelMap();
        ModelMap data=new ModelMap();
        String cityId=results.get("cityId").toString();      
        condition.put("regtype", results.get("regtype"));
        if(0==(Integer)condition.get("exp_type")){
			if(StringUtils.isEmpty(cityId)){
				condition.put("city_id", Constant.DEFAULT_CITY);
			}else{
				condition.put("city_id",cityId);
			}
			//装载齐心赚的数据
			data.put("0", this.experienceZhuanService.findExperienceByTypeByPage(condition));
        }
        condition.put("exp_type", 1);
        //装载全民赚的数据
        data.put("1", this.experienceZhuanService.findQuminzhuanExpByTypeByPage(condition));
        result.put("data", data);
        result.put("message", "success");
        return result;
    }
    private ModelMap findAdvs(Map<String,Object>results) {
        ModelMap result=new ModelMap();
        try {

            //获取请求端平台类型
            Map<String,Object> params=new HashMap<String, Object>();
            //params.put("type", "0");
            params.put("regtype",  results.get("regtype"));

            List<Map<String,String>> list=this.otherService.findAdvs(params);
            result.put("message", "success");
            result.put("data", list);
        } catch (Exception e) {
            result.put("message", "error");
        }
        return result;
    }



    /**
     * 首页0元试，抽奖乐，人气王，金币兑换显示两个产品,以及显示更多该类别的产品, 在request中有属性isTwo说明是指搜索该类别前两个
     * 没有就是搜索该类别
     * @param type 表示是那个类别
     * @param page 表示是第几页
     */
    @RequestMapping(value="/findExp")
    public @ResponseBody ModelMap findExpInIndex(Experience exp, HttpServletRequest request) {
        ModelMap result=new ModelMap();
        Map<String,Object> condition=new HashMap<String, Object>();
        String expType=request.getParameter("expType");
        String cityId=request.getParameter("cityId");
        if(StringUtils.isEmpty(cityId)){
            condition.put("cityId", Constant.DEFAULT_CITY);
        }else{
            condition.put("cityId",cityId);
        }
        condition.put("expType", expType);
        Page page=new Page();
        if(StringUtils.isNotBlank(request.getParameter("page.showCount"))) 
            page.setShowCount(Integer.parseInt(request.getParameter("page.showCount")));
        if(StringUtils.isNotBlank(request.getParameter("page.currentPage")))
            page.setCurrentPage(Integer.parseInt(request.getParameter("page.currentPage")));
        condition.put("page", page); 
        Utils.fillResult(request, result);
        condition.put("regType", result.get("regtype"));
        result.put("args", condition);
        List<Map<String,Object>> list=this.experienceService.findExperienceByTypeByPageMap(condition);
        if(list.size()<1){
            result.put("message", "error");
        }else {
            result.put("data", list);
            result.put("message", "success");
            //日志记录
            if(page.getCurrentPage()<2){

                CommLogUtil.NoMsmfCommLogList(result, expType);
            }
        }
        return result;
    }
}
