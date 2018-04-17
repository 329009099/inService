
package com.suyin.exp.controller;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suyin.common.Utils;
import com.suyin.exp.model.ExpDetail;
import com.suyin.exp.service.ExpDetailService;
import com.suyin.exp.service.ExperienceService;
import com.suyin.expzhuan.model.ExperienceTaskOrder;
import com.suyin.expzhuan.service.ExperienceTaskService;
import com.suyin.system.util.CommLogUtil;
import com.suyin.system.util.ConstantLogUtil;


/**
 * 
 * 该活动只负责查询免费活动中
 * 所有类型活动的详情 
 * 活动类型 为0 抽奖式,1人气式,2兑换式，3试用式
 * @author lz
 * @version 2015-9-12
 * @see ExpDetailController
 * @since
 */
@Controller
@RequestMapping("/expdetail")
public class ExpDetailController{

    private final static Logger log=Logger.getLogger(ExpDetailController.class);
    @Autowired
    private ExpDetailService expDetailService; //活动详情



    /**
     * 保存试用式问卷内容
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value="/involVedTryaddInfo")
    public @ResponseBody ModelMap involVedTryaddInfo(ExperienceTaskOrder order,HttpServletRequest request){
        ModelMap modelMap=new ModelMap();
        String expId=request.getParameter("expId");
        String paramdata=request.getParameter("paramdata");
        String userId=request.getParameter("userId");

        expDetailService.involVedTryaddInfo(paramdata);
        return modelMap;

    }
    /**
     * 查找试用式问卷调查题目
     * ExperienceTaskOrder
     * moduleType 1 全民赚问卷调查，2试用问卷调查
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value="/findExpTryDoubt")
    public @ResponseBody ModelMap findExpTryDoubt(HttpServletRequest request){

        ModelMap modelMap=new ModelMap();
        String expId=request.getParameter("expId");
        String moduleType=request.getParameter("moduleType");
        String userId=request.getParameter("userId");
        modelMap.put("result",expDetailService.findExpTryDoubt(expId, moduleType,userId));
        return modelMap;

    }

    /**
     * 获取首次分享者信息
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value="/findExpShareInfo")
    public @ResponseBody ModelMap findExpShareInfo(HttpServletRequest request){
        ModelMap modelMap=new ModelMap();
        Map<String,Object>conn=new HashMap<String, Object>();
        String detailId=request.getParameter("detailId");
        String userId=request.getParameter("userId");
        String expTimeId=request.getParameter("expTimeId");
        conn.put("detailId", detailId);
        conn.put("userId", userId);
        conn.put("expTimeId", expTimeId);
        modelMap.put("data",expDetailService.findExpShareInfo(conn));
        return modelMap;
    }
    /**
     * 
     * 根据当前用户查询是否在活动时间段内助力分享过该活动
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value="/findExpTimeSharDetail")
    public @ResponseBody  ModelMap findExpTimeSharDetail(HttpServletRequest request){
        ModelMap modelMap=new  ModelMap();

        Map<String,Object>conn=new HashMap<String, Object>();
        String userId=request.getParameter("userId");
        String expTimeId=request.getParameter("expTimeId");
        String openId=request.getParameter("openId");
        conn.put("userId", userId);
        conn.put("expTimeId", expTimeId);
        conn.put("openId", openId);

        List list=expDetailService.findExpTimeSharDetail(conn);
        if(list.size()>0){

            modelMap.put("message", "success");
        }else{

            modelMap.put("message", "invalidData");
        }

        return modelMap;
    }

    /**
     * 根据时间段主键id查询该时间段内是否有活动进行
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value="/findExpTimeStauts")
    public @ResponseBody ModelMap findExpTimeStauts(HttpServletRequest request){      
        ModelMap result=new ModelMap();
        String expTimeId=request.getParameter("expTimeId");
        Map<String, Object> map=new HashMap<String, Object>();
        map.put("expTimeId", expTimeId);
        String n=expDetailService.findExpTimeStauts(map);
        result.put("data",""+n+"");
        return  result;
    }
    /**
     * 
     * 查询用户参与活动状态
     * @param detail
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value="/findIsUserExpStauts")
    public @ResponseBody ModelMap findIsUserExpStauts(ExpDetail detail, HttpServletRequest request) {
        ModelMap result=new ModelMap();
        Map<String,Object> condition=new HashMap<String, Object>();
        condition.put("detailId", request.getParameter("detailId"));
        condition.put("userId", request.getParameter("userId"));
        Utils.fillResult(request, result);
        Map<String, Object>  detailInfo=this.expDetailService.findIsUserExpStauts(condition);
        if(detailInfo==null)
            result.put("message", "error");
        else {
            result.put("message", "success");
            result.put("data", detailInfo);
        }
        return result;
    }
    /**
     * 
     * 根据活动详情id查询页面对应信息 
     * @param detail
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value="/findExpDetail")
    public @ResponseBody ModelMap findExpInIndex(ExpDetail detail, HttpServletRequest request) {
        ModelMap result=new ModelMap();
        Map<String,Object> condition=new HashMap<String, Object>();
        String detaiId= request.getParameter("detailId");
        condition.put("detailId",detaiId);
        String expType=request.getParameter("expType");
        Utils.fillResult(request, result);
        Map<String, Object>  detailInfo=this.expDetailService.findExpDetailById(condition);
        if(detailInfo==null){

            result.put("message", "error");
        }else {
            result.put("message", "success");
            result.put("data", detailInfo);
            //模块记录
            CommLogUtil.NoMsmfCommLogDetial(result, expType);

            //详细活动记录
            Map<String,Object> params=new HashMap<String, Object>();
            params.put("expId", detailInfo.get("exp_id"));
            params.put("userId", result.get("userid"));
            params.put("detaiId", detaiId);
            params.put("clicentType",  result.get("regtype"));
            params.put("expType", expType);
            CommLogUtil.expOptLog(params);
        }
        return result;
    }


    /**
     * 
     * 人气式进程详情数据查询 不和其他活动做
     * 任何关联性操作
     * @param detail
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value="/findExpDetailRank")
    public @ResponseBody ModelMap findExpDetailRank(ExpDetail detail, HttpServletRequest request) {
        ModelMap result=new ModelMap();
        Map<String,Object> condition=new HashMap<String, Object>();
        condition.put("detailId", request.getParameter("detailId"));
        Utils.fillResult(request, result);
        Map<String, Object>  detailInfo=this.expDetailService.findExpDetailRank(condition);
        if(detailInfo==null)
            result.put("message", "error");
        else {
            result.put("message", "success");
            result.put("data", detailInfo);
        }
        return result;
    }


    /**
     * 
     * 根据活动详情id查询产品对应信息
     * @param detail
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value="/findExpProImage")
    public @ResponseBody ModelMap findExpProImage(ExpDetail detail, HttpServletRequest request) {
        ModelMap result=new ModelMap();
        Map<String,Object> condition=new HashMap<String, Object>();
        condition.put("detailId", request.getParameter("detailId"));
        //        Utils.fillResult(request, result);
        List<Map<String, Object>> imageList=this.expDetailService.findExpDetailImage(condition);
        if(imageList==null)
            result.put("message", "error");
        else {
            result.put("message", "success");
            result.put("data", imageList);
        }
        return result;
    }

    //查询评论显示信息

}

