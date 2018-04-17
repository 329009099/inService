package com.suyin.exp.service;

import java.util.List;
import java.util.Map;

import java.util.*;

import com.suyin.exp.model.ExpDetail;





public interface ExpDetailService{


    /**
     * 保存试用式申请数据
     * @param json
     * @return 
     * @see
     */
    public Integer involVedTryaddInfo(String json);
    /**
     * 根据活动ID查询试用式
     * 问卷调查内容
     * @param expId
     * @return 
     * @see
     */
    public List<Map<String, Object>> findExpTryDoubt(String expId,String moduleType,String userId);

    /**
     * 根据详情id查询详情信息
     * @param entity
     * @return 
     * @see
     */
    public Map<String, Object>findExpDetailById(Map<String, Object> detail);
    /**
     * 人气式进程页面详情数据查询
     * @param detail
     * @return 
     * @see
     */
    public Map<String,Object>findExpDetailRank(Map<String, Object> detail);
    /**
     * 查询当前时间段产品数量是否满足
     * @param detail
     * @return 
     * @see
     */
    public Map<String,Object>findExpIsProNum(Map<String,Object> detail);
    /**
     * 根据详情id查询图片 
     * @param image
     * @return 
     * @see
     */
    public List<Map<String,Object>>findExpDetailImage(Map<String, Object> image);
    /**
     * 查询用户参与活动状态
     * @param expUserInfo
     * @return 
     * @see
     */
    public Map<String,Object>findIsUserExpStauts(Map<String,Object> expUserInfo);
    /**
     * 更新时间段表中的参与数量 
     * @param expUserInfo
     * @return 
     * @see
     */
    public Integer updateTimeExpNum(Map<String,Object> timeExpInfo);
    /**
     * 
     * 更新活动详情参与人数
     * @param expDetailId
     * @return 
     * @see
     */
    public Integer updateExpDetailExpNum(String expDetailId);
    /**
     * 根据当前时间段主键id查询是否在当前活动时间内
     * @param map
     * @return 
     * @see
     */
    public String findExpTimeStauts(Map<String, Object> map);

    /**
     * 
     * 根据当前用户查询是否在活动时间段内助力分享过该活动
     * @param request
     * @return 
     * @see
     */
    public List<Map<String,Object>> findExpTimeSharDetail(Map<String, Object> map);
    /**
     * 获取首次分享者信息
     * @param map
     * @return 
     * @see
     */
    public Map<String, Object>findExpShareInfo(Map<String, Object> map);
}
