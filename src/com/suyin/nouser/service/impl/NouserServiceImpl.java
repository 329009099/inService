package com.suyin.nouser.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.suyin.common.mapper.CoinLogMapper;
import com.suyin.common.model.CoinLog;
import com.suyin.nouser.mapper.NouserMapper;
import com.suyin.nouser.mapper.RelationNouserMapper;
import com.suyin.nouser.model.Nouser;
import com.suyin.nouser.model.RelationNouser;
import com.suyin.nouser.service.NoUserCenterService;
import com.suyin.nouser.service.NouserService;
import com.suyin.system.util.Md5Util;
import com.suyin.system.util.SendMessage;
import com.suyin.system.util.Tools;

@Transactional(readOnly = false)
@Service("NouserService")
public class NouserServiceImpl implements NouserService {

    private final static Logger log = Logger.getLogger(NouserServiceImpl.class);

    private final static String REG_BZ = "reg_";

    private final static String WITHDRAWALS_BZ = "withdrawals_";

    private final static String BACK_BZ = "back_";
    @Autowired
    private NouserMapper NouserMapper;
    @Autowired
    private RelationNouserMapper RelationNouserMapper; // 用户注册标示，及客户端版本等操作
    @Autowired
    private CoinLogMapper conLogMapper; // 添加金币操作

    @Autowired
    private NoUserCenterService noUserCenterService;

    /**
     * 新增信息
     * 
     * @param entity
     * @return
     */

    @Override
    public Integer addNouser(Nouser entity) throws Exception {
        Integer result = 0;
        RelationNouser relation = new RelationNouser();
        CoinLog coinLog = new CoinLog();
        Map<String, Object> map = new HashMap<String, Object>();
        try {

            if (entity == null) {
                return result;
            } else {

                result = NouserMapper.addNouser(entity);
                relation.setUserId(entity.getUserId());
                relation.setRegisteredEdition(entity.getVersion());
                relation.setRegisteredType(Integer.parseInt(entity.getRegtype()));

                result += RelationNouserMapper.addRelationNouser(relation);

                // 添加金币操作
                // setDirection 1 代表增加 ，2代表减少 ，GoldCoin 注册所得积分
                coinLog.setUserId(entity.getUserId()).setGoldCoin(5)
                .setDirection("1").setContent("注册");
                conLogMapper.addCoinLog(coinLog);

                // 奖userId 添加t_nouser_static_prototype
                map.put("userId", entity.getUserId());
                map.put("goldCoin", "5"); // 初始化的5个积分 ,后期改为配置问卷读取方式
                NouserMapper.addNouserStaticProtoType(map);

            }

        } catch (Exception e) {

            throw new RuntimeException();
        }
        return result;

    }

    /**
     * 修改信息
     * 
     * @param entity
     * @return
     */
    @Override
    public Integer updateNouser(Nouser entity) {

        Integer result = 0;
        try {
            if (entity == null) {

                return result;
            } else {

                result = NouserMapper.updateNouser(entity);
            }
        } catch (Exception e) {

            log.error("Nouser信息修改异常" + e.getMessage());
            new RuntimeException();
            e.printStackTrace();
        }
        return result;

    }

    /**
     * 系统生成密码进行修改
     */
    @Override
    public Integer updateNouserPwd(Nouser entity) {
        Integer result = 0;
        try {
            if (entity == null) {

                return result;
            } else {

                result = NouserMapper.updateNouserPwd(entity);
            }
        } catch (Exception e) {

            log.error("updateNouserPwd信息修改异常" + e.getMessage());
            new RuntimeException();
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 删除信息
     * 
     * @param id
     * @return
     */
    @Override
    public Integer deleteNouser(String id) {

        return NouserMapper.deleteNouser(id);
    }

    /**
     * 根据openid查询
     */
    @Override
    public Nouser findNouserByOpenId(Nouser entity) {
        // TODO Auto-generated method stub
        return NouserMapper.findNouserByOpenId(entity);
    }

    /**
     * 查找信息列表
     * 
     * @param entity
     * @return
     */
    @Override
    public List<Nouser> findNouser(Nouser entity) {

        return NouserMapper.findNouserByLogin(entity);
    }

    /**
     * 查找信息列表(分页)
     * 
     * @param entity
     * @return
     */
    @Override
    public List<Nouser> findNouserByPage(Nouser entity) {

        return NouserMapper.findNouserByPage(entity);
    }

    /**
     * 根据id查询对应的信息
     * 
     * @param entity
     * @return
     */
    @Override
    public Nouser findNouserById(Nouser entity) {

        List<Nouser> list = NouserMapper.findNouserByLogin(entity);
        return list != null && !list.isEmpty() ? list.get(0) : null;
    }

    /**
     * 获取验证码
     */
    @Override
    public String requestNumber(HttpServletRequest request) throws Exception {
        String phone = request.getParameter("userPhone");
        org.json.simple.JSONObject jo = new org.json.simple.JSONObject();

        try {
            String str = Tools.getValByKey("time", "/sms.properties");// 获取失效时长
            if (Tools.isMobileNO(phone)) {
                if (request.getSession().getServletContext()
                    .getAttribute(REG_BZ + phone) != null) {

                    Object message = request.getSession().getServletContext()
                        .getAttribute(REG_BZ + phone);
                    @SuppressWarnings("unchecked")
                    Map<String, Object> sms = (Map<String, Object>) message;
                    Date ds = new Date();
                    ds.setTime(Long.parseLong(sms.get("time").toString()));
                    long time = Tools.queryMarginTime(ds, new Date());// 时间比较
                    if (time >= Long.parseLong(str)) {

                        request.getSession().getServletContext()
                        .removeAttribute(REG_BZ + phone);
                        // 重新发送
                        String code = Tools.createRandom(6);
                        Map<String, Object> smsInfo = new HashMap<String, Object>();
                        smsInfo.put("code", code);
                        smsInfo.put("time", new Date().getTime());
                        smsInfo.put("phone", phone);
                        SendMessage.nuserRegMessage(phone, code);
                        request.getSession().getServletContext()
                        .setAttribute(REG_BZ + phone, smsInfo);
                    } else {

                        SendMessage.nuserRegMessage(
                            sms.get("phone").toString(), sms.get("code")
                            .toString());

                    }
                    jo.put("message", "success");
                } else {

                    String message = Tools.createRandom(6);

                    Map<String, Object> smsInfo = new HashMap<String, Object>();
                    smsInfo.put("code", message);
                    smsInfo.put("time", new Date().getTime());
                    smsInfo.put("phone", phone);
                    SendMessage.nuserRegMessage(phone, message);
                    request.getSession().getServletContext()
                    .setAttribute(REG_BZ + phone, smsInfo);
                    jo.put("message", "success");

                }
            } else {

                jo.put("message", "invalidNumber");
            }
        } catch (Exception e) {
            jo.put("message", "error");
            // TODO Auto-generated catch block
            log.error("ServiceImpl Error NouserServiceImpl-> requestNumber "
                + e.getMessage());
        }
        return jo.toJSONString();
    }

    /**
     * 获取找回密码验证码
     */
    @Override
    public String backCodeNumber(HttpServletRequest request) throws Exception {
        // TODO Auto-generated method stub
        String phone = request.getParameter("userPhone");
        org.json.simple.JSONObject jo = new org.json.simple.JSONObject();

        try {
            Nouser nouser = new Nouser();
            nouser.setUserPhone(phone);
            nouser = this.findNouserById(nouser);
            String str = Tools.getValByKey("time", "/sms.properties");// 获取失效时长

            if (null != nouser) {

                if (request.getSession().getServletContext()
                    .getAttribute(BACK_BZ + phone) != null) {

                    Object message = request.getSession().getServletContext()
                        .getAttribute(BACK_BZ + phone);
                    @SuppressWarnings("unchecked")
                    Map<String, Object> sms = (Map<String, Object>) message;
                    Date ds = new Date();
                    ds.setTime(Long.parseLong(sms.get("time").toString()));
                    long time = Tools.queryMarginTime(ds, new Date());// 时间比较
                    if (time >= Long.parseLong(str)) { // 是否大于设置的超时时间
                        request.getSession().getServletContext()
                        .removeAttribute(BACK_BZ + phone);
                        // 重新发送
                        String code = Tools.createRandom(6);
                        Map<String, Object> smsInfo = new HashMap<String, Object>();
                        smsInfo.put("code", code);
                        smsInfo.put("time", new Date().getTime());
                        smsInfo.put("phone", phone);
                        SendMessage.backPwdMessage(phone, code);

                        request.getSession().getServletContext()
                        .setAttribute(BACK_BZ + phone, smsInfo);
                    } else {

                        SendMessage.backPwdMessage(sms.get("phone").toString(),
                            sms.get("code").toString());
                    }

                    jo.put("message", "success");
                } else {

                    String code = Tools.createRandom(6);

                    Map<String, Object> smsInfo = new HashMap<String, Object>();
                    smsInfo.put("code", code);
                    smsInfo.put("time", new Date().getTime());
                    smsInfo.put("phone", phone);
                    SendMessage.backPwdMessage(phone, code);
                    request.getSession().getServletContext()
                    .setAttribute(BACK_BZ + phone, smsInfo);
                    jo.put("message", "success");
                }

            } else {
                // 用户信息不存在
                jo.put("message", "invalidInfo");
            }
        } catch (Exception e) {
            jo.put("message", "error");
            // TODO Auto-generated catch block
            log.error("ServiceImpl Error NouserServiceImpl-> backCodeNumber "
                + e.getMessage());
        }
        return jo.toJSONString();
    }

    /**
     * 找回密码
     */
    @Override
    public String backPwd(HttpServletRequest request, Nouser entity)
        throws Exception {
        org.json.simple.JSONObject jo = new org.json.simple.JSONObject();
        Nouser nouserinfo = new Nouser();

        try {

            nouserinfo = this.findNouserById(entity);
            if (null != nouserinfo) {
                Map<String, Object> validMap = backRegPwdValid(request);
                if (validMap.get("message").equals("success")) {

                    Nouser nouser = new Nouser();
                    nouser.setUserPhone(entity.getUserPhone());
                    String pwd = Tools.createRandom(8);
                    String pwdMd5 = Md5Util.toMD5(pwd);
                    nouser.setUserPassword(pwdMd5);

                    int n = NouserMapper.updateNouserPwd(nouser);
                    if (n > 0) {

                        SendMessage.backCodeMessage(entity.getUserPhone(), pwd);
                        jo.put("message", "success");
                    }
                } else {

                    jo.put("message", validMap.get("message"));
                }
            } else {

                // 用户信息不存在
                jo.put("message", "invalidInfo");
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            log.error("ServiceImpl Error NouserServiceImpl-> backPwd "
                + e.getMessage());
            throw new RuntimeException();
        }
        return jo.toJSONString();
    }

    /**
     * 找回密码
     */
    @Override
    public String forgetPwd(HttpServletRequest request, Nouser entity)
        throws Exception {
        org.json.simple.JSONObject jo = new org.json.simple.JSONObject();
        Nouser nouserinfo = new Nouser();
        try {
            nouserinfo = this.findNouserById(entity);
            if (null != nouserinfo) {
                Map<String, Object> validMap = backRegPwdValid(request);
                if (validMap.get("message").equals("success")) {

                    Nouser nouser = new Nouser();
                    nouser.setUserPhone(entity.getUserPhone());
                    String pwdMd5 = Md5Util.toMD5(entity.getNewPassword());
                    nouser.setUserPassword(pwdMd5);

                    int n = NouserMapper.updateNouserPwd(nouser);
                    if (n > 0) {
                        // SendMessage.backCodeMessage(entity.getUserPhone(),
                        // entity.getNewPassword());
                        jo.put("message", "success");
                    }
                } else {

                    jo.put("message", validMap.get("message"));
                }
            } else {

                // 用户信息不存在
                jo.put("message", "invalidInfo");
            }
        } catch (NumberFormatException e) {
            // TODO Auto-generated catch block
            log.error("ServiceImpl Error NouserServiceImpl-> backPwd "
                + e.getMessage());
            throw new RuntimeException();
        }
        return jo.toJSONString();
    }

    /**
     * 判断用户是否存在
     */
    @Override
    public String validUserInfo(Nouser entity) throws Exception {
        // TODO Auto-generated method stub
        org.json.simple.JSONObject jo = new org.json.simple.JSONObject();

        try {
            entity = this.findNouserById(entity);

            if (null != entity) {
                jo.put("message", "success");
            } else {
                // 用户信息不存在
                jo.put("message", "invalidInfo");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("ServiceImpl Error NouserServiceImpl-> validUserInfo "
                + e.getMessage());
        }

        return jo.toJSONString();
    }

    /**
     * 用户登录
     */
    @Override
    public String login(Nouser entity) throws Exception {
        // TODO Auto-generated method stub
        org.json.simple.JSONObject jo = new org.json.simple.JSONObject();
        JSONObject j = new JSONObject();
        try {
            Nouser model = this.findNouserById(entity);

            if (null != model) {

                if (null != model.getUserPassword()) {
                    if (model.getUserPassword().equals(
                        Md5Util.toMD5(entity.getUserPassword()))) {

                        j.put("userid", model.getUserId());
                        j.put("userPhone", model.getUserPhone());
                        j.put("openid", model.getOpenid());
                        j.put("state", model.getUserState());
                        jo.put("data", j);
                        jo.put("message", "success");

                        if (0 == entity.getLoginType()) {// 微信端登录
                            if (null != model.getOpenid()
                                && !"".equals(model.getOpenid())) {
                                if (entity.getOpenid()
                                    .equals(model.getOpenid())) {

                                    // 本次登录用户与上次绑定用户唯一标示一致
                                    jo.put("userStatus", "validOpenid");
                                } else {

                                    jo.put("userStatus", "invalidOpenid");

                                }

                            } else {

                                jo.put("userStatus", "binding");

                            }

                        }

                    } else {

                        jo.put("message", "invalidPwd");
                    }
                } else {

                    jo.put("message", "invalidPwdBack");
                }
            } else {

                jo.put("message", "invalidInfo");
            }
        } catch (Exception e) {
            log.error("ServiceImpl Error NouserServiceImpl-> login "
                + e.getMessage());
        }
        return jo.toJSONString();
    }

    /**
     * 用户注册
     */
    @Transactional(readOnly = false)
    @Override
    public String regNouserInfo(HttpServletRequest request, Nouser entity)
        throws Exception {
        // TODO Auto-generated method stub
        org.json.simple.JSONObject jo = new org.json.simple.JSONObject();
        try {
            Map<String, Object> validMap = regValid(request, entity);
            if (validMap.get("message").equals("success")) {

                String phone = entity.getUserPhone().toString();
                String pwd = entity.getUserPassword();
                String strPwd = Md5Util.toMD5(pwd);
                entity.setUserPassword(strPwd);
                int n = this.addNouser(entity);
                if (n > 1) {

                    redPack(phone);//红包方面的处理
                    jo.put("message", "success");
                }

            } else {

                jo.put("message", validMap.get("message"));
            }

        } catch (Exception e) {

            log.error("ServiceImpl Error NouserServiceImpl-> regNouserInfo "
                + e.getMessage());
            throw new RuntimeException();
        }
        return jo.toJSONString();
    }

    private void redPack(String phone){
        /**处理NO团红包推广部分的金额同步问题 start**/
        //查询该新用户是否参与过红包活动20151203
        Map<String,Object>mapInfo=new HashMap<String, Object>();
        mapInfo.put("phone", phone);
        List<Map<String,Object>> redInfo=NouserMapper.findUserInfoList(mapInfo);
        if(redInfo.size()>0){
            //获取用户userId信息
            Map<String,Object> noUser=NouserMapper.findNoUser(mapInfo);
            for (int i = 0; i < redInfo.size(); i++ )
            {
                if(null!=noUser){

                    mapInfo.put("userId", noUser.get("user_id"));
                    mapInfo.put("price", redInfo.get(i).get("tg_price"));
                    int isSuccess=NouserMapper.updateNoUserMoney(mapInfo);
                    if(isSuccess>0){
                        //日志记录下，在个人中心钱包明细中查看
                        Map<String, Object> condition = new HashMap<String, Object>();

                        condition.put("money", mapInfo.get("price"));
                        condition.put("userId", mapInfo.get("userId"));
                        if("0".equals(redInfo.get(i).get("tg_type").toString())){

                            condition.put("content", "庆NO团网APP上线，现金红包获得" + mapInfo.get("price") + "元");
                        }else if("2".equals(redInfo.get(i).get("tg_type").toString())){

                            condition.put("content", "NO团网庆双旦，现金红包获得" + mapInfo.get("price") + "元");    
                        }
                        condition.put("direction", "1");
                        condition.put("log_status", "0");
                        NouserMapper.addCashLog(condition);  
                    }
                }

            }
        }
        /**处理NO团红包推广部分的金额同步问题 stop**/

    }
    /**
     * 密码找回验证码相关效验
     * 
     * @param request
     * @return
     * @see
     */
    public Map<String, Object> backRegPwdValid(HttpServletRequest request) {
        String phone = request.getParameter("userPhone");
        String code = request.getParameter("code");
        String str = Tools.getValByKey("time", "/sms.properties");// 获取失效时长
        ModelMap jo = new ModelMap();

        if (request.getSession().getServletContext()
            .getAttribute(BACK_BZ + phone) != null) {

            Object message = request.getSession().getServletContext()
                .getAttribute(BACK_BZ + phone);
            @SuppressWarnings("unchecked")
            Map<String, Object> sms = (Map<String, Object>) message;
            Date ds = new Date();
            ds.setTime(Long.parseLong(sms.get("time").toString()));

            long time = Tools.queryMarginTime(ds, new Date());// 时间比较
            if (code.equals(sms.get("code"))) {

                if (time >= Long.parseLong(str)) { // 是否大于设置的超时时间

                    request.getSession().getServletContext()
                    .removeAttribute(BACK_BZ + phone);
                    jo.put("message", "timeout");
                } else {

                    jo.put("message", "success");
                }

            } else {

                jo.put("message", "invalidcode");
            }
        } else {

            jo.put("message", "error");
        }
        return jo;
    }

    /**
     * 
     * 用户注册验证码相关验证
     * 
     * @param request
     * @param entity
     * @return
     * @see
     */
    public Map<String, Object> regValid(HttpServletRequest request,
        Nouser entity) {
        // TODO Auto-generated method stub
        ModelMap jo = new ModelMap();
        Nouser model = this.findNouserById(entity);
        if (null != model) {

            jo.put("message", "repeat");

        } else {

            String str = Tools.getValByKey("time", "/sms.properties");// 获取失效时长
            String phone = entity.getUserPhone().toString();

            if (request.getSession().getServletContext()
                .getAttribute(REG_BZ + phone) != null) {

                Object message = request.getSession().getServletContext()
                    .getAttribute(REG_BZ + phone);
                @SuppressWarnings("unchecked")
                Map<String, Object> sms = (Map<String, Object>) message;
                Date ds = new Date();
                ds.setTime(Long.parseLong(sms.get("time").toString()));
                String code = entity.getCode();

                long time = Tools.queryMarginTime(ds, new Date());// 时间比较
                if (code.equals(sms.get("code"))) {

                    if (time >= Long.parseLong(str)) { // 是否大于设置的超时时间

                        request.getSession().getServletContext()
                        .removeAttribute(REG_BZ + phone);
                        jo.put("message", "timeout");
                    } else {

                        jo.put("message", "success");
                    }

                } else {

                    jo.put("message", "invalidcode");
                }
            } else {

                jo.put("message", "error");
            }

        }

        return jo;
    }

    /**
     * 根据openid查询用户信息
     */
    @Override
    public String findNouserWhereByOpenId(Nouser entity) throws Exception {
        // TODO Auto-generated method stub
        JSONObject jo = new JSONObject();
        try {
            entity = NouserMapper.findNouserByOpenId(entity);
            if (null != entity) {

                jo.put("message", "success");
                jo.put("userid", entity.getUserId());
                jo.put("openid", entity.getOpenid());
                jo.put("userPhone", entity.getUserPhone());
                jo.put("state", entity.getUserState());

            } else {
                jo.put("message", "invalidNouser");

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("ServiceImpl Error NouserServiceImpl-> findNouserWhereByOpenId "
                + e.getMessage());
            jo.put("message", "error");
        }
        return jo.toJSONString();
    }

    /**
     * 根据手机号码查询用户信息
     */
    @Override
    public String findNouserWhereByIphone(Nouser entity) throws Exception {
        // TODO Auto-generated method stub
        JSONObject jo = new JSONObject();
        try {
            entity = NouserMapper.findNouserWhereByIphone(entity);
            if (null != entity) {

                jo.put("message", "success");
                jo.put("userid", entity.getUserId());
                jo.put("openid", entity.getUserPhone());
                jo.put("state", entity.getUserState());

            } else {
                jo.put("message", "invalidNouser");

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            log.error("ServiceImpl Error NouserServiceImpl-> findNouserWhereByIphone "
                + e.getMessage());
        }
        return jo.toJSONString();
    }

    /**
     * 根据userId查询对应信息
     */
    @Override
    public Nouser findNouserWhereByUserId(int userId) {
        // TODO Auto-generated method stub
        return NouserMapper.findNouserWhereByUserId(userId);
    }

    /**
     * 根据用户userId查询金币相关信息
     */
    @Override
    public Map<String, Object> findNouserMoneyInfo(Map<String, Object> userInfo) {
        // TODO Auto-generated method stub
        return NouserMapper.findNouserMoneyInfo(userInfo);
    }

    /**
     * 修改用户金币数
     */
    @Override
    public Integer updateNouserMoneyInfo(Map<String, Object> userInfo) {
        // TODO Auto-generated method stub
        return NouserMapper.updateNouserMoneyInfo(userInfo);
    }

    @Override
    public String updatePW(Map<String, String> map) {

        return null;
    }

    /**
     * 查询钱包基本信息
     */
    @Override
    public net.sf.json.JSONObject findNouserStaticInfo(
                                                       Map<String, Object> userInfo) throws Exception {
        // TODO Auto-generated method stub
        net.sf.json.JSONObject jo = new net.sf.json.JSONObject();
        Map<String, Object> mapInfo = NouserMapper
            .findNouserStaticInfo(userInfo);
        if (null != mapInfo) {

            jo.put("message", "success");
            jo.put("data", mapInfo);

        } else {

            jo.put("message", "invalidNouser");
        }

        return jo;
    }

    /**
     * 修改安全账户信息
     */
    @Override
    public String updateSecurityInfo(Map<String, Object> mapInfo) {
        org.json.JSONObject jo = new org.json.JSONObject();

        try {
            Integer a=NouserMapper.findAiliCode(String.valueOf(mapInfo.get("ali_pay")));
            if(a>0){
                jo.put("message", "aliPayRepeat"); //支付宝账号已存在 不能重复提交
            }else{
                Integer n = NouserMapper.updateSecurityInfo(mapInfo);
                if (n > 0) {

                    jo.put("message", "success");
                } else {

                    jo.put("message", "error");

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo.toString();
    }

    /**
     * 修改提现密码
     */
    @Override
    public String modifyWithdrawalsPassword(HttpServletRequest request,
                                            String userId, String userPhone, String withdrawalsPassword,
                                            String code) {
        org.json.JSONObject jo = new org.json.JSONObject();
        Map<String, String> mapInfo = new HashMap<String, String>();
        try {
            if (StringUtils.isBlank(userId)) {
                jo.put("message", "userIdIsNull");
                return jo.toString();
            }

            if (StringUtils.isBlank(userPhone)) {
                jo.put("message", "userPhoneIsNull");
                return jo.toString();
            }

            if (StringUtils.isBlank(withdrawalsPassword)) {
                jo.put("message", "withdrawalsPasswordIsNull");
                return jo.toString();
            }

            if (StringUtils.isBlank(withdrawalsPassword)) {
                jo.put("message", "codeIsNull");
                return jo.toString();
            }

            mapInfo.put("userId", userId);
            mapInfo.put("withdrawalsPassword",
                Md5Util.toMD5(withdrawalsPassword));

            String phone = NouserMapper.findUserPhoneById(Integer
                .valueOf(userId));

            if (StringUtils.isBlank(phone) || !userPhone.equals(phone)) {
                jo.put("message", "userPhoneIsDiffered");
                return jo.toString();
            }

            String str = Tools.getValByKey("time", "/sms.properties");// 获取失效时长

            if (request.getSession().getServletContext()
                .getAttribute(WITHDRAWALS_BZ + phone) != null) {

                Object message = request.getSession().getServletContext()
                    .getAttribute(WITHDRAWALS_BZ + phone);
                @SuppressWarnings("unchecked")
                Map<String, Object> sms = (Map<String, Object>) message;
                Date ds = new Date();
                ds.setTime(Long.parseLong(sms.get("time").toString()));

                // 比较时间
                long time = Tools.queryMarginTime(ds, new Date());

                if (code.equals(sms.get("code"))) {
                    // 是否大于设置的超时时间
                    if (time >= Long.parseLong(str)) {
                        request.getSession().getServletContext()
                        .removeAttribute(WITHDRAWALS_BZ + phone);
                        jo.put("message", "timeout");
                        return jo.toString();
                    } 
                } else {
                    jo.put("message", "invalidcode");
                    return jo.toString();
                }
            } else {
                jo.put("message", "error");
                return jo.toString();
            }

            Integer n = NouserMapper.modifyWithdrawalsPassword(mapInfo);

            if (n > 0) {
                jo.put("message", "success");
            } else {
                jo.put("message", "error");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jo.toString();
    }

    /**
     * 获取修改提现密码验证码
     */
    @Override
    public String withdrawalsCode(HttpServletRequest request) {
        String phone = request.getParameter("userPhone");
        org.json.simple.JSONObject jo = new org.json.simple.JSONObject();
        try {
            Nouser nouser = new Nouser();
            nouser.setUserPhone(phone);
            nouser = this.findNouserById(nouser);
            // 获取失效时长
            String str = Tools.getValByKey("time", "/sms.properties");
            if (null != nouser) {
                if (request.getSession().getServletContext()
                    .getAttribute(WITHDRAWALS_BZ + phone) != null) {

                    @SuppressWarnings("unchecked")
                    Map<String, Object> sms = (Map<String, Object>)request.getSession().getServletContext()
                    .getAttribute(WITHDRAWALS_BZ + phone);

                    Date ds = new Date();
                    ds.setTime(Long.parseLong(sms.get("time").toString()));
                    long time = Tools.queryMarginTime(ds, new Date());// 时间比较
                    if (time >= Long.parseLong(str)) { // 是否大于设置的超时时间
                        request.getSession().getServletContext()
                        .removeAttribute(WITHDRAWALS_BZ + phone);
                        // 重新发送
                        String code = Tools.createRandom(6);
                        Map<String, Object> smsInfo = new HashMap<String, Object>();
                        smsInfo.put("code", code);
                        smsInfo.put("time", new Date().getTime());
                        smsInfo.put("phone", phone);
                        SendMessage.withdrawalsPwdMsg(phone, code);

                        request.getSession().getServletContext()
                        .setAttribute(WITHDRAWALS_BZ + phone, smsInfo);
                    } else {

                        SendMessage.withdrawalsPwdMsg(sms.get("phone").toString(),
                            sms.get("code").toString());
                    }

                    jo.put("message", "success");
                } else {

                    String code = Tools.createRandom(6);

                    Map<String, Object> smsInfo = new HashMap<String, Object>();
                    smsInfo.put("code", code);
                    smsInfo.put("time", new Date().getTime());
                    smsInfo.put("phone", phone);
                    SendMessage.withdrawalsPwdMsg(phone, code);
                    request.getSession().getServletContext()
                    .setAttribute(WITHDRAWALS_BZ + phone, smsInfo);
                    jo.put("message", "success");
                }

            } else {
                // 用户信息不存在
                jo.put("message", "invalidInfo");
            }
        } catch (Exception e) {
            jo.put("message", "error");
            log.error("ServiceImpl Error NouserServiceImpl-> withdrawalsCode "
                + e.getMessage());
        }
        return jo.toJSONString();
    }

    /**
     * 根据userId查询该用户是否设置安全账户
     */
    @Override
    public String findSecurityUserInfo(Map<String, Object> mapInfo) {

        String result = "";
        // TODO Auto-generated method stub
        Map<String, Object> map = NouserMapper.findSecurityUserInfo(mapInfo);
        if ("no".equals(map.get("ali_pay"))
            || "-1".equals(map.get("withdrawals_password"))) {

            result = "error";
        } else {

            result = "success";

        }
        return result;
    }

    /**
     * 绑定操作
     */
    @Override
    public String updateUserOpenIdInfo(Nouser entity) {
        Map<String, Object> params = new HashMap<String, Object>();
        // TODO Auto-generated method stub
        org.json.simple.JSONObject jo = new org.json.simple.JSONObject();
        try {
            Nouser model = this.findNouserById(entity);
            if (null != model) {
                if (entity.getLoginType() == 0) {
                    params.put("userId", model.getUserId());
                    params.put("openId", entity.getOpenid());
                    // 将openid更新至用户信息表中
                    NouserMapper.updateUserOpenIdInfo(params);
                    jo.put("message", "success");
                } else {

                    jo.put("message", "error");
                }
            }

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            jo.put("message", "error");
        }
        return jo.toJSONString();
    }

}
