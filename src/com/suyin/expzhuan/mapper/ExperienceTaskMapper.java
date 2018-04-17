package com.suyin.expzhuan.mapper;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.suyin.expzhuan.model.ExperienceTaskOrder;

public interface ExperienceTaskMapper {

	/**查询齐心赚活动信息*/
    public List<Map<String,String>> findExperienceByTypeByPage(Map<String,Object> exp);
    /**查询全名赚活动信息*/
    public List<Map<String,String>> findQuminzhuanExpByTypeByPage(Map<String,Object> exp);
    
    public Map<String,String> findExpById(Map<String,Object> exp);

    /**判断用户是否参加过两个赚的某个活动*/
    public int isEverJoinExp(ExperienceTaskOrder order);

   
    public int isQualified(ExperienceTaskOrder order);
    /**判断一个用户是否有资格参加全面赚的活动，结果等于0表示有资格，大于0表示没有资格*/
    public int isExpTaskQualified(ExperienceTaskOrder order);

    /**参加两个赚的活动，就是往t_exp_task_order表里添一条数据*/
    public int addExpZhuanOrder(ExperienceTaskOrder order);
    /**参加成功了就要更新一下参与人数*/
    public int addJoinedUserCount(ExperienceTaskOrder order);

    /**获取活动的状态（开始，结束什么的）*/
    public int getExpTaskProgress(ExperienceTaskOrder order);

    /**获取用户已经获取的金币情况和该项活动（目前只有齐心赚）的剩余金币，最大上限的情况，以便判断时候可以加上金币 */
    public Map<String,Integer> getExpAnOrderCoinStatus(ExperienceTaskOrder order);

    /**给用户加金币*/
    public int updateUserCoin(Map<String, Object> conditon);
    /**改变活动的剩余金币*/
    public int updateExpTaskCoin(Map<String, Object> conditon);
    /**改变该订单已获得的金币数量*/
    public int updateExpTaskOrderCoin(Map<String, Object> conditon);

    /**参加全民赚活动后，要上传一个验证图片*/
    public int updateExpTaskImageUrl(ExperienceTaskOrder order);

    /**全民赚上传验证图片/form表单 后修改order状态为“已提交”,需要orderId*/
    public int updateExpTaskStatus(ExperienceTaskOrder order);

    /**查找全民赚的form表单*/
    public List<Map<String,Object>> findExpForm(ExperienceTaskOrder order);

    /**保存全民赚form上传的答案*/
    public int addExpFormAnswer(Collection<HashMap> list);

    public Map<String, String> findExpZAppOrder(Map<String, Object>params);
    /**查询全民赚app下载图片示例*/
    public Map<String,Object>findExpZAppDownInfo( Map<String, Object>params);

    public List<Map<String, String>> getExpFormAnswer(Map<String, String> expId);

    /**
     * 更新金币任务金币
     * @param condition 
     * @see
     */
    public void updateExpTaskOrderCoin1(Map<String, Object> condition);
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
     * 查询数量是否满足条件
     * @param order
     * @return 
     * @see
     */
    public Integer findExpCount(ExperienceTaskOrder order);
    /**
     * 根据活动id和userId查询订单信息
     * @param order
     * @return 
     * @see
     */
    public Map<String,Object>findUserTaskInvolv(ExperienceTaskOrder order);
    /**
     * 根据活动id查询该活动的分享星系
     * @param mapInfo
     * @return 
     * @see
     */
    public Map<String,Object>findExpShareInfo(Map<String, Object> mapInfo);

	public Map<String, String> findQixinZhuanODetail(int id);
}
