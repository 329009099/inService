package com.suyin.expzhuan.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.map.deser.MapDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suyin.common.Utils;
import com.suyin.expzhuan.model.ExperienceTaskOrder;
import com.suyin.expzhuan.service.ExperienceTaskService;
import com.suyin.nouser.model.Nouser;
import com.suyin.nouser.service.NouserService;
import com.suyin.system.model.Page;
import com.suyin.system.util.CommLogUtil;
import com.suyin.system.util.Constant;
import com.suyin.system.util.ConstantLogUtil;

/**
 * 
 * 赚金币活动处理
 * @author lz
 * @version 2015-12-11
 * @see ExperienceZhuanController
 * @since
 */
@Controller
@RequestMapping("/expz")
public class ExperienceZhuanController {

    @Autowired
    private ExperienceTaskService experienceZhuanService;

    @Autowired
    private NouserService nouserService;

    /**
     * 
     * 微信请求的装载赚金币的俩个模块
     * @param request
     * @return 
     * @see
     */
    @RequestMapping("/findTwo")
    public @ResponseBody ModelMap findTwo(HttpServletRequest request) {
        Map<String,Object> condition=new HashMap<String, Object>();
        ModelMap result=new ModelMap();       
        Utils.fillResult(request, result);
        condition.put("exp_type", 0);
        Page page=new Page();
        page.setShowCount(2);
        page.setCurrentPage(1);
        condition.put("page", page);
        //请求城市id
        String cityId=request.getParameter("city_id");

        ModelMap data=new ModelMap();	
        //请求平台类型	
        condition.put("regtype", result.get("regtype"));    
        //装载齐心赚的数据
        if(0==(Integer)condition.get("exp_type")){
            if(StringUtils.isEmpty(cityId)){
                condition.put("city_id", Constant.DEFAULT_CITY);
            }else{
                condition.put("city_id",cityId);
            }
            data.put("0", this.experienceZhuanService.findExperienceByTypeByPage(condition));
        }
        condition.put("exp_type", 1);                   
        //装载全民赚的数据
        data.put("1", this.experienceZhuanService.findQuminzhuanExpByTypeByPage(condition));
        result.put("data", data);
        result.put("message", "success");
        return result;
    }

    /**
     *  赚金币列表查询
     *  isTow
     *  page.showCount
     *  page.currentPage
     */
    @RequestMapping(value="/findExp")
    public @ResponseBody ModelMap findExpInIndex(HttpServletRequest request) {
        Map<String,Object> condition=new HashMap<String, Object>();
        String expType=request.getParameter("expType");
        String cityId=request.getParameter("city_id");
        condition.put("exp_type", expType);
        Page page=new Page();
        if(StringUtils.isNotBlank(request.getParameter("page.showCount"))) 
            page.setShowCount(Integer.parseInt(request.getParameter("page.showCount")));
        if(StringUtils.isNotBlank(request.getParameter("page.currentPage")))
            page.setCurrentPage(Integer.parseInt(request.getParameter("page.currentPage")));
        condition.put("page", page);
        ModelMap result=new ModelMap();
        Utils.fillResult(request, result);
        result.put("args", condition);
        condition.put("regtype", result.get("regtype"));
        List<Map<String,String>> list=new ArrayList<Map<String,String>>();
        if("0".equals(condition.get("exp_type"))){//0代表齐心赚
            if(StringUtils.isEmpty(cityId)){
                condition.put("city_id", Constant.DEFAULT_CITY);
            }else{
                condition.put("city_id", cityId);
            }
            list=this.experienceZhuanService.findExperienceByTypeByPage(condition);
        }
        else if("1".equals(condition.get("exp_type"))){//1代表全名赚
            list=this.experienceZhuanService.findQuminzhuanExpByTypeByPage(condition);
        }
        if(list.size()<1){

            result.put("message", "error");
        }else{

            if(page.getCurrentPage()<2){

                CommLogUtil.CommLogTaskList(result, expType);
            }
            result.put("data", list);
            result.put("message", "success");
        }

        return result;
    }

    /**
     * 赚金币活动详情查询
     * 备用
     * @param request
     * @return
     */
    @RequestMapping(value="/findExpByIdTwo")
    public @ResponseBody ModelMap findExpByIdTwo( ExperienceTaskOrder order, HttpServletRequest request) {
        Map<String,Object> condition=new HashMap<String, Object>();
        condition.put("exp_id", request.getParameter("expId"));
        ModelMap result=new ModelMap();
        result.put("args", condition);
        Utils.fillResult(request, result);
        Map<String,String> e=this.experienceZhuanService.findExpById(condition);
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
     * 赚金币活动详情查询
     * @param request
     * @return
     */
    @RequestMapping(value="/findExpById")
    public @ResponseBody ModelMap findExpById( ExperienceTaskOrder order, HttpServletRequest request) {
        Map<String,Object> condition=new HashMap<String, Object>();
        String expId=request.getParameter("expId");
        condition.put("exp_id", expId);
        ModelMap result=new ModelMap();
        result.put("args", condition);
        Utils.fillResult(request, result);
        Map<String,String> e=this.experienceZhuanService.findExpById(condition);
        //有两种情况下e==null。1：数据库查询出错了；2.找不到结果（但是我们暴露出去的活动id一定是可以查找到结果的）
        if(e==null) {
            result.put("message", "error");
        }else{
            //日志详情记录
            CommLogUtil.CommLogTaskDetail(result, JSONObject.fromObject(e).get("exp_type").toString());
            result.put("data", e);
            result.put("message", "success");
            //详细活动记录
            Map<String,Object> params=new HashMap<String, Object>();
            params.put("expId", expId);
            params.put("userId", result.get("userid"));
            params.put("detaiId", expId);//此处已活动id充当 详情id 与免费活动公用
            params.put("clicentType",  result.get("regtype"));
            params.put("expType", JSONObject.fromObject(e).get("exp_type").toString());
            CommLogUtil.expZhuanOptLog(params);
        }
        return result;
    }

    /**
     * 参加全民赚的活动和齐心赚的活动
     * @param request
     * @return
     */
    @RequestMapping(value="/joinExp")
    public @ResponseBody String joinExp(Nouser user,HttpServletRequest request) {
        return this.experienceZhuanService.addExpZhuanOrder(user,request.getParameter("expId"),request.getParameter("moduleType")).toString();
    }

    /**
     * 用户分享了齐心赚，要给金币啊~~~
     * @param order
     * @return
     */
    @RequestMapping("/shareExp")
    public @ResponseBody String shareExp(ExperienceTaskOrder order) {
        try {
            this.experienceZhuanService.updateExpTaskOrderShareCoin(order);
        } catch (Exception e) {
            return "{\"message\":\"error\"}";
        }
        return "{\"message\":\"success\"}";
    }

    /**
     * 有人点击了齐心赚分享出去的东西，只有要加金币什么的,该方法其实没有什么好返回的
     * @param request
     * @return
     */
    @RequestMapping(value="/clickExpTaskB")
    public @ResponseBody String clickExpB(ExperienceTaskOrder order, HttpServletRequest request) {
        try {
            String openId=request.getParameter("openid");
            String expId=order.getExpId().toString();
            String userId=order.getUserId().toString();
            Map<String, Object> mapInfo=new HashMap<String, Object>();
            mapInfo.put("openId", openId);
            mapInfo.put("expId", expId);           
            mapInfo.put("userId", userId);

            List<Map<String,Object>> resultMap=this.experienceZhuanService.findExpTaskQinxinShareInfo(mapInfo);
            if(resultMap.size()>0){
                //已经参与助力了,直接出去
                return "{\"message\":\"success\"}";
            }else{
                this.experienceZhuanService.updateExpZhuanOrderCoin(order);
                //保存齐心赚朋友圈点击记录
                this.experienceZhuanService.addExpTaskQinxinShareInfo(mapInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "{\"message\":\"error\"}";
        }
        return "{\"message\":\"success\"}";
    }

    /**
     * 全民赚要上传图片的，上传完图片后要修改订单状态，图片地址什么的
     * @param order
     * @return
     */
    @RequestMapping(value="/submitExpTaskA")
    public @ResponseBody String submitExpTaskAPic(ExperienceTaskOrder order, HttpServletRequest request) {
        try {
            this.experienceZhuanService.submitExpTaskA(order,request.getParameter("attach"));
        } catch (Exception e) {
            return "{\"message\":\"error\"}";
        }
        return "{\"message\":\"success\"}";
    }

    /**
     * 找到全民赚的表单
     * @param order
     * @return
     */
    @RequestMapping("/findExpForm")
    public @ResponseBody ModelMap findExpForm(ExperienceTaskOrder order){
        ModelMap result=new ModelMap();
        result.put("data", this.experienceZhuanService.findExpForm(order));
        return result;
    }

    /**
     * 全民赚要上传表单的，先上传表单数据，再修改订单状态
     * @param data
     * @return
     */
    @RequestMapping(value="/submitExpTaskFormA")
    public @ResponseBody String submitExpTaskAForm(ExperienceTaskOrder order,HttpServletRequest request) {
        JSONArray jsoA=JSONArray.fromObject(request.getParameter("data"));
        Collection<HashMap> list=JSONArray.toCollection(jsoA, HashMap.class);
        try {
            this.experienceZhuanService.addExpFormAnswer(list,order);
        } catch (Exception e) {
            return "{\"message\":\"error\"}";
        }
        return "{\"message\":\"success\"}";
    }


    @RequestMapping(value="/findExpZAppOrder")
    public @ResponseBody ModelMap findExpZAppOrder(@RequestParam("expId") String expId,@RequestParam("userId")String userId) {
        ModelMap result=new ModelMap();
        Map<String,Object>params=new HashMap<String, Object>();
        params.put("expId", expId);
        params.put("userId", userId);
        Map<String,String> map= this.experienceZhuanService.findExpZAppOrder(params);
        result.put("data", map);
        return result;
    }

    /**
     * 全民赚app下载图片示例
     * @param orderId
     * @return 
     * @see
     */
    @RequestMapping(value="/findExpZAppDownInfo")
    public @ResponseBody ModelMap findExpZAppDownInfo(@RequestParam("expId") String expId,@RequestParam("userId") String userId) {

        ModelMap result=new ModelMap();
        Map<String, Object>params=new HashMap<String, Object>();
        params.put("expId", expId);
        params.put("userId", userId);
        Map<String,Object> map= this.experienceZhuanService.findExpZAppDownInfo(params);
        result.put("data", map);
        return result; 
    } 

    @RequestMapping(value="/getExpFormAnswer")
    public @ResponseBody ModelMap getExpFormAnswer(@RequestParam("expId") String expId,@RequestParam("userId") String userId,
                                                   @RequestParam("orderId") String orderId){
        ModelMap result=new ModelMap();
        Map<String, String> condition=new HashMap<String, String>();
        condition.put("expId", expId);
        condition.put("userId", userId);
        condition.put("orderId", orderId);
        List<Map<String,String>> map= this.experienceZhuanService.getExpFormAnswer(condition);
        result.put("data", map);
        return result;
    }

    /**
     * 根据活动id查询该活动的分享信息
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value="/findExpShareInfo")
    public @ResponseBody ModelMap findExpShareInfo(HttpServletRequest request){
        ModelMap model=new ModelMap();
        String expId=request.getParameter("expId");
        Map<String,Object> mapInfo=new HashMap<String, Object>();
        mapInfo.put("expId", expId);
        model.put("result",this.experienceZhuanService.findExpShareInfo(mapInfo));
        return model;

    }
    @RequestMapping(value="/findQixinZhuanODetail")
    public @ResponseBody ModelMap findQixinZhuanODetail(@RequestParam("orderId") int id) {
        ModelMap result=new ModelMap();
        Map<String,String> map= this.experienceZhuanService.findQixinZhuanODetail(id);
        result.put("data", map);
        return result;
    }




}
