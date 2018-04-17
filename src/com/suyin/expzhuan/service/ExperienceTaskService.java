package com.suyin.expzhuan.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.suyin.expzhuan.model.ExperienceTaskOrder;
import com.suyin.nouser.model.Nouser;

public interface ExperienceTaskService {

    public List<Map<String,String>> findExperienceByTypeByPage(Map<String,Object> exp);

    public List<Map<String,String>> findQuminzhuanExpByTypeByPage(Map<String,Object> exp);
    
    public Map<String,String> findExpById(Map<String,Object> exp);



    /**
     * 参加全民赚活动,齐心赚活动
     * @param user
     * @param parameter
     * @param string 
     * @return
     */
    public JSONObject addExpZhuanOrder(Nouser user, String parameter, String string);


    public void updateExpTaskOrderShareCoin(ExperienceTaskOrder order);
    /**
     * 有人点击了齐心赚分享出去的链接之后，要给用户加钱啊。。。
     * @param order
     * @return
     */
    public void updateExpZhuanOrderCoin(ExperienceTaskOrder order);

    public void submitExpTaskA(ExperienceTaskOrder order, String attach);

    /**查找全民赚的form表单*/
    public List<Map<String,Object>> findExpForm(ExperienceTaskOrder order);

    /**保存全民赚form上传的答案
     * @param order */
    public int addExpFormAnswer(Collection<HashMap> list, ExperienceTaskOrder order);

    public Map<String, String> findExpZAppOrder(Map<String, Object> map);

    public Map<String,Object>findExpZAppDownInfo( Map<String, Object>params);

    public List<Map<String, String>> getExpFormAnswer(Map<String, String> expId);
    /**
     * 齐心赚分享助力查询
     * @param mapInfo
     * @return 
     * @see
     */
    public  List<Map<String,Object>> findExpTaskQinxinShareInfo(Map<String,Object>mapInfo);
    /**
     * 添加记录齐心赚分享助力信息
     * @param mapInfo
     * @return 
     * @see
     */
    public Integer addExpTaskQinxinShareInfo(Map<String, Object>mapInfo);

    /**
     * 根据活动id查询该活动的分享星系
     * @param mapInfo
     * @return 
     * @see
     */
    public net.sf.json.JSONObject findExpShareInfo(Map<String, Object> mapInfo);

	public Map<String, String> findQixinZhuanODetail(int id);
}
