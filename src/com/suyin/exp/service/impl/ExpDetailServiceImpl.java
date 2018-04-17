package com.suyin.exp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.suyin.exp.service.ExpDetailService;
import com.suyin.expzhuan.model.ExperienceTaskOrder;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Transactional
@Service("ExpDetailService")
public class ExpDetailServiceImpl implements ExpDetailService{

    private final static Logger log=Logger.getLogger(ExpDetailServiceImpl.class);

    @Autowired
    private com.suyin.exp.mapper.ExpDetailMapper ExpDetailMapper; 


    /**
     * 根据活动详情查询，是否已经添加活动产品
     */
    @Override
    public Map<String, Object> findExpDetailById(Map<String, Object> entity)
    {
        // TODO Auto-generated method stub
        return ExpDetailMapper.findExpDetailById(entity);
    }


    /**
     * 根据详情id查询产品图片
     */
    @Override
    public List<Map<String, Object>> findExpDetailImage(Map<String, Object> image)
    {
        // TODO Auto-generated method stub
        return ExpDetailMapper.findExpDetailImage(image);
    }


    /**
     * 查询用户参与状态
     */
    @Override
    public Map<String, Object> findIsUserExpStauts(Map<String, Object> expUserInfo)
    {
        // TODO Auto-generated method stub
        return ExpDetailMapper.findIsUserExpStauts(expUserInfo);
    }


    /**
     * 查询当前时间段内产品数量是否满足
     */
    @Override
    public Map<String, Object> findExpIsProNum(Map<String, Object> detail)
    {
        // TODO Auto-generated method stub
        return ExpDetailMapper.findExpIsProNum(detail);
    }


    /**
     * 更新时间段表中的exp_num数量
     */
    @Override
    public Integer updateTimeExpNum(Map<String, Object> timeExpInfo)
    {
        // TODO Auto-generated method stub
        return ExpDetailMapper.updateTimeExpNum(timeExpInfo);
    }
    /**
     * 更新时间段表中的exp_num数量
     */
    @Override
    public Integer updateExpDetailExpNum(String expDetailId)
    {
        // TODO Auto-generated method stub
        return ExpDetailMapper.updateExpDetailExpNum(expDetailId);
    }

    /**
     * 根据当前时间段主键id查询是否在当前活动时间内
     * @param map
     * @return 
     * @see
     */
    @Override
    public String findExpTimeStauts(Map<String, Object> map)
    {
        // TODO Auto-generated method stub
        return ExpDetailMapper.findExpTimeStauts(map);
    }


    /**
     * 
     * 根据当前用户查询是否在活动时间段内助力分享过该活动
     * @param request
     * @return 
     * @see
     */
    @Override
    public List<Map<String,Object>> findExpTimeSharDetail(Map<String, Object> map)
    {
        // TODO Auto-generated method stub
        return ExpDetailMapper.findExpTimeSharDetail(map);
    }


    /**
     * 获取首次分享者信息
     */
    @Override
    public Map<String, Object> findExpShareInfo(Map<String, Object> map)
    {
        // TODO Auto-generated method stub
        return ExpDetailMapper.findExpShareInfo(map);
    }


    /**
     * 试用式问卷信息查询
     */
    @Override
    public List<Map<String, Object>> findExpTryDoubt(String expId, String moduleType,String userId)
    {
        // TODO Auto-generated method stub
        Map<String,Object> param=new HashMap<String,Object>();
        param.put("expId", expId);
        param.put("moduleType", moduleType);
        param.put("userId", userId);
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        list = ExpDetailMapper.findExpZhuanPrototype(param); 
        for (Map map : list) {
            param.put("dictionary_id", map.get("dictionary_id"));
            map.put("options", ExpDetailMapper.findExpZhuanPrototypeOption(param));
        }
        return list;
    }


    /**
     * 保存试用式问卷题目入库
     */
    @Override
    public Integer involVedTryaddInfo(String json)
    {
        // TODO Auto-generated method stub
        try {
            JSONArray array = new JSONArray(json);
            JSONObject iObj;
            Map<String, Object> map=new HashMap<String, Object>();
            for(int i=0;i<array.length();i++){              
                iObj= array.getJSONObject(i);
                map.put("expId", iObj.get("expid").toString());
                map.put("type", iObj.get("moduletype").toString());
                map.put("value", iObj.get("value").toString());
                map.put("isSelected", 1);
                map.put("dId",iObj.get("did").toString());
                map.put("pId",iObj.get("pid").toString());


                ExpDetailMapper.involVedTryaddInfo(map);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return 1;

    }


    /**
     * 独立的人气式进程页面详情数据查询
     */
    @Override
    public Map<String, Object> findExpDetailRank(Map<String, Object> detail)
    {
        // TODO Auto-generated method stub
        return ExpDetailMapper.findExpDetailRank(detail);
    }



}
