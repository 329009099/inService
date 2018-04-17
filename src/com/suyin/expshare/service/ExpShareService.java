package com.suyin.expshare.service;

import java.util.*;

import com.suyin.expshare.model.*;
import com.suyin.expshare.service.*;




public interface ExpShareService{

    /**
     * 新增信息
     * @param entity
     * @return
     */
    public Integer addExpShare(ExpShare entity);
    /**
     * 修改信息
     * @param entity
     * @return
     */
    public Integer updateExpShare(ExpShare entity);
    /**
     * 获取人气式排名数据
     * @param condition
     * @return 
     * @see
     */
    public List<Map<String,Object>> findRankForShareByPage(Map<String,Object> condition);
    /**
     * 获取人气式数据_包含假数据
     * @param condition
     * @return 
     * @see
     */
    public List<Map<String,Object>>findFalseRankForShareByPage(Map<String,Object>condition);
    /**
     * 获取人气式我的排名
     * @param condition
     * @return 
     * @see
     */
    public Map<String, Object> findRankForMySelf(Map<String, Object> condition);
    /**
     * 获取人气式_我的排名_假数据混合
     * @param condition
     * @return 
     * @see
     */
    public Map<String,Object>findFalseRankForMySelf(Map<String,Object>condition);

}