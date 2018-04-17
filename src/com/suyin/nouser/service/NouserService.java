package com.suyin.nouser.service;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import com.suyin.nouser.model.*;
import com.suyin.nouser.service.*;




public interface NouserService{

    /**
     * 新增信息
     * @param entity
     * @return
     * @throws Exception 
     */
    public Integer addNouser(Nouser entity) throws Exception;

    /**
     * 修改信息
     * @param entity
     * @return
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
     * 系统生成密码进行修改
     * @param entity
     * @return 
     * @see
     */
    public Integer updateNouserPwd(Nouser entity);

    /**
     * 删除信息
     * @param id
     * @return
     */
    public Integer deleteNouser(String id);

    /**
     * 查找信息列表
     * @param entity
     * @return
     */
    public List<Nouser> findNouser(Nouser entity);

    /**
     * 查找信息列表(分页)
     * @param entity
     * @return
     */
    public List<Nouser> findNouserByPage(Nouser entity);


    /**
     * 
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
     * 根据id查询对应的信息
     * @param entity
     * @return
     */
    public Nouser findNouserById(Nouser entity);

    /**
     * 获取注册验证码 
     * @param request
     * @return 
     * @see
     */
    public String requestNumber(HttpServletRequest request) throws Exception;
    /**
     * 获取找回密码验证码
     * @param request
     * @return 
     * @see
     */
    public String backCodeNumber(HttpServletRequest request) throws Exception;
    /**
     * 找回密码
     * @param request
     * @return 
     * @see
     */
    public String backPwd(HttpServletRequest request, Nouser entity) throws Exception;
    
    /**
     * 忘记密码
     * @param request
     * @return 
     * @see
     */
    public String forgetPwd(HttpServletRequest request, Nouser entity) throws Exception;

    /**
     *
     *根据微信openid查询 用户信息 
     * @param entity
     * @return
     * @throws Exception 
     * @see
     */
    public String findNouserWhereByOpenId(Nouser entity)throws Exception;
    /**
     * 查询用户钱包基本信息
     * @param userInfo
     * @return 
     * @see
     */
    public JSONObject findNouserStaticInfo(Map<String, Object> userInfo)throws Exception;
    /**
     * 
     * 根据手机号码查询用户信息 
     * @param entity
     * @return
     * @throws Exception 
     * @see
     */
    public String findNouserWhereByIphone(Nouser entity )throws Exception;

    /**
     * 验证用户是否存在
     * @param request
     * @return 
     * @see
     */
    public String validUserInfo(Nouser entity) throws Exception;

    /**
     * 用户登录
     * @param entity
     * @return 
     * @see
     */
    public String login(Nouser entity) throws Exception;
    /**
     * 用户注册
     * @param request
     * @param entity
     * @return 
     * @throws Exception 
     * @see
     */
    public String regNouserInfo(HttpServletRequest request,Nouser entity) throws Exception;
    /**
     * 修改登录密码
     * @param map
     * @return 
     * @see
     */
    public String updatePW(Map<String, String> map);
    /**
     * 修改安全账户信息
     * @param mapInfo
     * @return 
     * @see
     */
    public String updateSecurityInfo(Map<String,Object> mapInfo);
    
    /**
     * 修改提现密码
     * @param userId
     * @param userPhone
     * @param withdrawalsPassword
     * @return
     */
    public String modifyWithdrawalsPassword(HttpServletRequest request, String userId, String userPhone, String withdrawalsPassword, String code);
    
    /**
     * 获取修改提现密码验证码
     * @param userId
     * @param userPhone
     * @param withdrawalsPassword
     * @return
     */
    public String withdrawalsCode(HttpServletRequest request);
    
    /**
     * 根据用户userId查询该用户是否设置安全账户
     * @param mapInfo
     * @return 
     * @see
     */
    public String findSecurityUserInfo(Map<String,Object>mapInfo);
    /**
     * 绑定操作
     * @param mapInfo
     * @return 
     * @see
     */
    public String updateUserOpenIdInfo(Nouser entity);
}
