/*
 * 文件名：ExpInvolvedService.java
 * 版权：Copyright by www.isure.net
 * 描述：
 * 修改人：windows7
 * 修改时间：2015-9-15
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.suyin.exp.service;

import java.util.Map;

import com.suyin.exp.model.Experience;
import com.suyin.expzhuan.model.ExperienceTaskOrder;

/**
 * 活动参与 
 * 活动类型expType 为0 抽奖式,1人气式,2兑换式，3试用式
 * @author lz
 * @version 2015-9-12
 * @see ExpInvolvedController
 * @since
 */
public interface ExpInvolvedService
{
    /**
     * 抽奖式  show_type 0:普通类型1:拼图:2刮刮卡3抽签
     * @param exp
     * @param condition
     * @return 
     * @see
     */
    public String inVolvePrize(Experience exp,Map<String, Object> condition);

    /**
     * 金币兑换式 
     * @param exp
     * @param condition
     * @return 
     * @see
     */
    public String inVolveExchange(Experience exp,Map<String, Object> condition);


    /**
     * 微信分享人气式 
     * @param exp
     * @param condition
     * @return 
     * @see
     */
    public String inVolvePopularity(Experience exp,Map<String, Object> condition);



    /**
     * 申请试用式
     * @param exp
     * @param condition
     * @return 
     * @see
     */
    public String inVolveTry(Experience exp,Map<String, Object> condition);
    /**
     * 问卷页面弹出是查看当前用户是否已经参与过活动了 
     * @param exp
     * @param condition
     * @return 
     * @see
     */
    public String inVolveExpUserStauts(Experience exp,Map<String, Object> condition);
    /**
     * 效验匹配动态属性是否符合条件
     * @param order
     * @return 
     * @see
     */
    public int isQualified(ExperienceTaskOrder order);
}
