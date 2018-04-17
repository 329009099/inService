
package com.suyin.nouser.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.suyin.nouser.model.RelationNouser;
import com.suyin.nouser.service.RelationNouserService;
import com.suyin.system.model.Page;
import com.suyin.system.util.Tools;


/**
 * 
 * 用户关联表操作 
 * @author lz
 * @version 2015-8-26
 * @see RelationNouserController
 * @since
 */
@Controller
@RequestMapping("/relationnouser")
public class RelationNouserController{

    private final static Logger log=Logger.getLogger(RelationNouserController.class);
    @Autowired
    private RelationNouserService relationNouserService;

    /**
     * 首页
     * @return 
     * @see
     */
    @RequestMapping(value="/index")
    public ModelAndView index() {

        return new ModelAndView("relationnouser/index");
    }


    /**
     * 读取列表
     * @param request
     * @return 
     * @see
     */
    @RequestMapping(value = "/list")
    public @ResponseBody Map<String, Object> findForRelationNouserAll(HttpServletRequest request) {
        ModelMap map=new ModelMap();

        String pag = request.getParameter("page");
        String showCount = request.getParameter("rows");
        Page page = new Page();
        try
        {      
            if (null != pag && null != showCount) {
                page.setCurrentPage(Integer.parseInt(pag));
                page.setShowCount(Integer.parseInt(showCount));
            }

            RelationNouser  entityInfo=new RelationNouser ();
            entityInfo.setPage(page);
            List<RelationNouser > list=relationNouserService.findRelationNouserByPage(entityInfo);
            map.put("rows",list); 
            map.put("total",entityInfo.getPage().getTotalResult()); 

        }
        catch (Exception e)
        {
            log.error("Controller Error RelationNouserController-> findRelationNouserByWhere  " + e.getMessage());
        }

        return map;
    }




    /**
     * 跳转添加页面
     * @param request
     * @return
     */
    @RequestMapping(value = "/jumpAdd")
    public ModelAndView jumpRelationNouserAdd(HttpServletRequest request) {
        ModelMap map=new ModelMap();

        return new ModelAndView("relationnouser/save",map);
    }

    /**
     * 跳转修改页面 
     * @param request
     * @return
     */
    @RequestMapping(value = "/jumpEdit")
    public ModelAndView jumpRelationNouserEdit(HttpServletRequest request) {
        ModelMap map=new ModelMap();
        try
        {

            if(Tools.notEmpty(request.getParameter("id"))){  

                RelationNouser entity=new RelationNouser();
                entity.setRelationUserId(Integer.parseInt(request.getParameter("id")));
                entity=relationNouserService.findRelationNouserById(entity);
                map.put("relationnouser",entity);

            }
        }
        catch (Exception e)
        {

            log.error("Controller Error RelationNouserController-> jumpRelationNouserEdit  " + e.getMessage());
        }
        return new ModelAndView("relationnouser/edit",map);
    }

    /**
     * 信息保存
     * Description: <br>
     * @param 
     * @return 
     * @see
     */
    @RequestMapping(value = "/add")
    public @ResponseBody Map<String, Object> saveRelationNouserInfo(RelationNouser entity) {
        ModelMap map=new ModelMap();
        try
        {

            map.put("result",relationNouserService.addRelationNouser(entity));
        }
        catch (Exception e)
        {
            log.error("Controller Error RelationNouserController-> saveRelationNouserInfo " + e.getMessage());
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
    public @ResponseBody Map<String, Object> updateRelationNouserById(RelationNouser entity) {
        ModelMap map=new ModelMap();
        try
        {            
            map.put("result",relationNouserService.updateRelationNouser(entity));
        }
        catch (Exception e)
        {
            log.error("Controller Error RelationNouserController-> updateRelationNouserById  " + e.getMessage());
        }
        return map;
    }

    /**
     * 信息删除
     * @param 
     * @return
     */
    @RequestMapping(value = "/delete")
    public @ResponseBody Map<String, Object> deleteRelationNouserById(String id) {

        ModelMap map=new ModelMap();
        try
        {
            if(Tools.notEmpty(id)){

                map.put("result",relationNouserService.deleteRelationNouser(id));
            }  
        }
        catch (Exception e)
        {
            log.error("Controller Error RelationNouserController-> deleteRelationNouserById  " + e.getMessage());
        }

        return map;
    }


}

