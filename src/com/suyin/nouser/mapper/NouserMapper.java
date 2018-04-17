package com.suyin.nouser.mapper;


import java.util.*;

import net.sf.json.JSONObject;

import com.suyin.nouser.model.*;
import com.suyin.nouser.service.*;




public interface NouserMapper {

    /**
     * 新增信息
     */
    public Integer addNouser(Nouser entity);
    /**
     * 新增t_nouser_static_protoType 静态属性
     */
    public Integer addNouserStaticProtoType(Map<String, Object> map);
    /**
     * 修改信息
     */
    public Integer updateNouser(Nouser entity);
    /**
     * 修改用户金币数
     * @param userInfo
     * @return 
     * @see
     */
    public Integer updateNouserMoneyInfo(Map<String,Object> userInfo);

    /**
     *根据系统生成密码修改
     */
    public Integer updateNouserPwd(Nouser entity);
    /**
     * 根据id删除单条信息
     * 
     */
    public Integer deleteNouser(String id);
    /**
     * 批量删除
     */
    public Integer deleteNouser(String[] id); 

    /**
     * 根据openid查询 
     * @param entity
     * @return 
     * @see
     */
    public Nouser findNouserByOpenId(Nouser entity);

    /**
     * 根据userId查询对应信息
     * @param entity
     * @return 
     * @see
     */
    public Nouser findNouserWhereByUserId(int userId);
    /**
     * 根据用户Userid查询金币相关信息
     * @param userInfo
     * @return 
     * @see
     */
    public Map<String,Object>findNouserMoneyInfo(Map<String, Object> userInfo);
    /**
     * 查询用户钱包基本信息
     * @param userInfo
     * @return 
     * @see
     */
    public Map<String, Object> findNouserStaticInfo(Map<String, Object> userInfo);
    /**
     * 根据手机号码查询用户信息 
     * @param entity
     * @return 
     * @see
     */
    public Nouser findNouserWhereByIphone(Nouser entity);

    /**
     * 查询列表
     */
    public List<Nouser> findNouserByLogin(Nouser entity);

    /**
     * 查询列表分页  
     */
    public List<Nouser> findNouserByPage(Nouser entity);
    /**
     * 修改安全账户信息
     * @param mapInfo
     * @return 
     * @see
     */
    public Integer updateSecurityInfo(Map<String,Object> mapInfo);
    
    /**
     * 修改提现密码
     * @param mapInfo
     * @return 
     * @see
     */
    public Integer modifyWithdrawalsPassword(Map<String, String> mapInfo);
    
    /**
     * 根据userId查询用户手机号
     * @param userId
     * @return
     */
    public String findUserPhoneById(Integer userId);
    
    /**
     * 根据用户userId查询该用户是否设置安全账户
     * @param mapInfo
     * @return 
     * @see
     */
    public Map<String,Object> findSecurityUserInfo(Map<String,Object>mapInfo);
    /**
     * 
     * 根据userId 更新微信端登录的openid
     * @param userId
     * @return 
     * @see
     */
    public Integer updateUserOpenIdInfo(Map<String,Object>mapInfo);
    
    
    
    /***红包处理部分statr***/
    //20151203
    /**
     * 
     * 根据手机号码查询是否是NO团的注册用户
     * @param mapInfo
     * @return 
     * @see
     */
    public Map<String,Object>findNoUser(Map<String,Object>mapInfo);
    /**
     * 查询该用户在红包活动中的，开到的红包金额
     * @param map
     * @return 
     * @see
     */
    public Map<String,Object>findUserInfo(Map<String,Object> map);
    
    /**
     * 查询该用户在红包活动中的，开到的红包金额
     * @param map
     * @return 
     * @see
     */
    public List<Map<String,Object>>findUserInfoList(Map<String,Object> map);
    /**
     * 
     * 更新NO团网用户的金额
     * @param mapInfo
     * @return 
     * @see
     */
    public Integer updateNoUserMoney(Map<String,Object>mapInfo);
    
    
    /**
     * 添加用户得到钱的记录
     * @param condition
     * @return 
     * @see
     */
    public Integer addCashLog(Map<String, Object> condition);
    
    /**
     * 根据支付宝账号 查询该账号是否存在
     */
	public Integer findAiliCode(String aliPay);
    
    /***红包处理部分stop***/
}
