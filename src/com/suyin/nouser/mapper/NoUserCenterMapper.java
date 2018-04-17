package com.suyin.nouser.mapper;


import java.util.Date;
import java.util.List;
import java.util.Map;

import com.suyin.common.model.CoinTeller;
import com.suyin.nouser.model.ExperienceOrder;
import com.suyin.nouser.model.ExperienceVouch;


public interface NoUserCenterMapper
{

    /**查看今天是否已经签到过了,返回超过0，说明签过了*/
    public int findIfSignInToday(int userId);

    /**签到*/
    public int addSignInLog(int userId);

    /**获取连续签到的天数*/
    public int findSignInDays(int userId);

    /**获取上一回签到的时间*/
    public Date findLastSignInTime(int userId);

    /**用户签到断片了，就重置记录*/
    public int updateSignInLog(int userId);

    /**查找用户参与的东西*/
    public List<Map<String, String>> findInvolvementByPage(int userId);

    /**查找用户的静态信息如金币，钱之类的*/
    public Map<String, Object> findUserInfo(int userId);

    /**找到用户参与的活动*/
    public List<ExperienceOrder> findInvolvementByPage(ExperienceOrder order);

    /**查找用户的券*/
    public List<ExperienceVouch> findVouchByPage(ExperienceVouch order);

    /**减少t_nouser_static_prototype.money,同时增加 frozen_money*/
    public void updateUserMoney(Map<String, Object> condition);

    /**添加用户的提现记录*/
    public Integer addCashLog(Map<String, Object> condition);

    public void updateUserCoin(Map<String, Object> condition);
    
    public void updateAddUserMoney(Map<String, Object> condition);

    public void addCashTeller(CoinTeller condition);

    public int updatePF(Map<String, String> condition);

    /**
     * 查询是否有提现成功还未审核通过的数据
     * @param condition
     * @return 
     * @see
     */
    public List<Map<String, Object>> findIsTellerInfo(Map<String, Object> condition);

    /**
     * 修改头像
     * @param imgUrl
     * @param userId 
     * @return 
     * @see
     */
    public int updateHead(String imgUrl, String userId);
    public List<Map<String,Object>>findIsCoinInfo(Map<String,Object>condition);    
    /**
     * 更新齐心赚成功或失败状态
     * @param userId
     * @return 
     * @see
     */
    public Integer updateIsLoseTaskOrderInfo(String userId);

	public int updateMsgStatus(int userId);
}
