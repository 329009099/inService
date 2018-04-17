package com.suyin.nouser.service;


import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.suyin.nouser.model.ExperienceOrder;
import com.suyin.nouser.model.ExperienceVouch;


public interface NoUserCenterService
{

    /**签到*/
    public JSONObject signIn(int userId);

    /**获取用户的基本静态信息，如还有多少钱之类的*/
    public Map<String, Object> findUserInfo(int userId);

    /**找到用户参与的活动*/
    public List<ExperienceOrder> findInvolvementByPage(ExperienceOrder order);

    /**查找用户的券*/
    public List<ExperienceVouch> findVouch(ExperienceVouch order);

    /**用户提交提现申请*/
    public JSONObject cash(Map<String, Object> condition);

    /**用户把金币转换为钱*/
    public String coinToCash(Map<String, Object> condition);

    public int updatePF(Map<String, String> condition);

    /**
     * 保存图片的url
     * @param imgUrl
     * @param userId 
     * @return 
     * @see
     */
    public int updateHead(String imgUrl, String userId);

    /**
     * 更新齐心赚成功或失败状态
     * @param userId
     * @return 
     * @see
     */
    public Integer updateIsLoseTaskOrderInfo(String userId);

    /**
     * 获取用户的基本静态信息，如还有多少钱之类的，附带签到信息
     * @param userId
     * @return 
     * @see
     */
    Map<String, Object> findUserInfoAndSignIn(int userId);

	public void readMsg(int userId);
}
