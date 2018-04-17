package com.suyin.nouser.service.impl;


import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.suyin.common.mapper.CoinLogMapper;
import com.suyin.common.model.CashLog;
import com.suyin.common.model.CoinLog;
import com.suyin.common.model.CoinTeller;
import com.suyin.expzhuan.mapper.ExperienceTaskMapper;
import com.suyin.nouser.mapper.NoUserCenterMapper;
import com.suyin.nouser.model.ExperienceOrder;
import com.suyin.nouser.model.ExperienceVouch;
import com.suyin.nouser.service.NoUserCenterService;


@Service
public class NoUserCenterServiceImpl implements NoUserCenterService
{

    @Autowired
    private NoUserCenterMapper noUserCenterMapper;

    @Autowired
    private ExperienceTaskMapper experienceTaskMapper;

    @Autowired
    private CoinLogMapper coinLogMapper;

    /**
     * 签到
     */
    @Override
    @Transactional
    public synchronized JSONObject signIn(int userId)
    {
        JSONObject result = new JSONObject();
        if (noUserCenterMapper.findIfSignInToday(userId) > 0)
        {
            result.put("msg", "1"); //今天已签到
            return result;
        }
        this.noUserCenterMapper.addSignInLog(userId);
       /* Date d = this.noUserCenterMapper.findLastSignInTime(userId);
        if (d == null)
        {//说明是第一次签到
            this.noUserCenterMapper.addSignInLog(userId);
        }
        else
        {
            Calendar lastSignInTime = Calendar.getInstance();
            lastSignInTime.setTime(d);
            Calendar yesterday = Calendar.getInstance();
            yesterday.add(Calendar.DAY_OF_YEAR, -1);
            //昨天签到了
            if (lastSignInTime.get(Calendar.DAY_OF_YEAR) == yesterday.get(Calendar.DAY_OF_YEAR)
                && lastSignInTime.get(Calendar.YEAR) == yesterday.get(Calendar.YEAR))
            {
                this.noUserCenterMapper.addSignInLog(userId);
            }
            else
            {
                this.noUserCenterMapper.updateSignInLog(userId);
            }
        }*/
        int singleCoin = 1;
        int days=1;
       /* int days = this.noUserCenterMapper.findSignInDays(userId);
        if (days >= 5)
            singleCoin = 2;
        else
            singleCoin = 1;*/
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("singleCoin", singleCoin);
        experienceTaskMapper.updateUserCoin(map); //用户转户添加金币
        this.coinLogMapper.addCoinLog(new CoinLog().setUserId(userId).setDirection("1").setContent(
            "签到成功").setGoldCoin(singleCoin)); //加个log
        result.put("msg", "0");
        result.put("coin", singleCoin);
        result.put("days", days);
        return result;
    }

    @Override
    public Map<String, Object> findUserInfo(int userId)
    {
        return this.noUserCenterMapper.findUserInfo(userId);
    }

    @Override
    public List<ExperienceOrder> findInvolvementByPage(ExperienceOrder order)
    {
        return this.noUserCenterMapper.findInvolvementByPage(order);
    }

    @Override
    public List<ExperienceVouch> findVouch(ExperienceVouch order)
    {
        return this.noUserCenterMapper.findVouchByPage(order);
    }

    /**
     * 提取金币变为钱
     */
    @Override
    @Transactional
    public String coinToCash(Map<String, Object> condition)
    {
//        Map<String, Object> map = this.noUserCenterMapper.findUserInfo((Integer)condition.get("userId"));
//        if ((Integer)condition.get("coin") > (Long)map.get("gold_coin"))
//        {
//            return "morecoin";
//        }

        //判断是否存在未审核的数据
//        List<Map<String, Object>> resultMapInfo = this.noUserCenterMapper.findIsCoinInfo(condition);
//        if (resultMapInfo.size() > 0)
//        {
//
//            return "notaudit";
//        }
//        else
//        {
            //减少t_nouser_static_prototype.gold_coin,同时增加frozen_gold_coin
            this.noUserCenterMapper.updateUserCoin(condition);

            //添加申请记录到 t_nouser_coin_teller,并且同意状态
            CoinTeller teller = new CoinTeller().setUserId((Integer)condition.get("userId")).setStatus(
                1).setGoldCoin((Integer)condition.get("coin")).setMoney(
                new BigDecimal(((Integer)condition.get("coin")) / 10.0));

            this.coinLogMapper.addCoinTeller(teller);
            //添加t_nouser_coin_log的变化记录
            CoinLog log = new CoinLog();
            log.setUserId((Integer)condition.get("userId"));
            //log.setContent("提现钱包" + condition.get("coin") + "金币审核中");
            log.setContent("提取金币");
            log.setDirection("2");
            log.setGoldCoin((Integer)condition.get("coin"));
            log.setStatus(0);
            log.setCoinTellerId(teller.getLogId());
            this.coinLogMapper.addCoinLog(log);
            
            
            //修改金币转换钱包数
            condition.put("money", new BigDecimal(((Integer)condition.get("coin")) / 10.0));
            this.noUserCenterMapper.updateAddUserMoney(condition);

            CashLog cashLog = new CashLog();
            
            cashLog.setUserId((Integer)condition.get("userId"));
            cashLog.setDirection("1");
            cashLog.setStatus(0);
            cashLog.setMoney(new BigDecimal(((Integer)condition.get("coin")) / 10.0));
            cashLog.setContent("金币转换");
            
            this.coinLogMapper.addCashLog(cashLog);
            return "success";
//        }

    }

    /**
     * 提取钱到支付宝
     */
    @Override
    @Transactional
    public JSONObject cash(Map<String, Object> condition)
    {
        JSONObject result = new JSONObject();
        Map<String, Object> map = this.noUserCenterMapper.findUserInfo((Integer)condition.get("userId"));
        if (map == null || !condition.get("password").equals(map.get("withdrawals_password")))
        {
            result.put("message", "invalidP"); //提现密码错误
            return result;
        }
        if (new BigDecimal(String.valueOf(map.get("money"))).compareTo((BigDecimal)condition.get("cash")) < 0)
        {
            result.put("message", "lessmoney");
            return result;
        }
        List<Map<String, Object>> resultMapInfo = this.noUserCenterMapper.findIsTellerInfo(condition);
        if (resultMapInfo.size() > 0)
        {

            result.put("message", "notaudit");
            return result;
        }
        else
        {

            this.noUserCenterMapper.updateUserMoney(condition);
            CoinTeller co = new CoinTeller();
            co.setUserId(Integer.parseInt(condition.get("userId").toString()));
            co.setMoney(new BigDecimal(String.valueOf(condition.get("cash"))));
            co.setStatus(0);
            co.setAlipay(condition.get("alipay").toString());

            co.setName(condition.get("name").toString());

            //添加提现申请的log
            this.noUserCenterMapper.addCashTeller(co);

            condition.put("coinTellerId", co.getLogId());
            condition.put("direction", "2");
            //添加提现的log
            this.noUserCenterMapper.addCashLog(condition);
            result.put("message", "success");
        }
        return result;
    }

    @Override
    public int updatePF(Map<String, String> condition)
    {
        return this.noUserCenterMapper.updatePF(condition);
    }

    @Override
    public int updateHead(String imgUrl, String userId)
    {
        int row = 0;
        try
        {
            row = noUserCenterMapper.updateHead(imgUrl, userId);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return row;
    }

    /**
     * 更新齐心赚成功或失败状态
     */
    @Override
    public Integer updateIsLoseTaskOrderInfo(String userId)
    {
        // TODO Auto-generated method stub
        return noUserCenterMapper.updateIsLoseTaskOrderInfo(userId);
    }

    @Override
    public Map<String, Object> findUserInfoAndSignIn(int userId)
    {
        ModelMap result = new ModelMap();
        if (noUserCenterMapper.findIfSignInToday(userId) > 0)
        {
            result.put("msg", "1"); //今天已签到
        }
        else
        {
            result.put("msg", "0"); //今天未签到
        }
        result.put("userInfo", this.noUserCenterMapper.findUserInfo(userId));
        return result;
    }

	@Override
	public void readMsg(int userId) {
		this.noUserCenterMapper.updateMsgStatus(userId);
	}
}
