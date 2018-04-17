package com.suyin.expzhuan.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suyin.common.mapper.AttachmentMapper;
import com.suyin.common.mapper.CoinLogMapper;
import com.suyin.common.mapper.MessageMapper;
import com.suyin.common.model.Attachment;
import com.suyin.common.model.CoinLog;
import com.suyin.expzhuan.mapper.ExperienceTaskMapper;
import com.suyin.expzhuan.model.ExperienceTaskOrder;
import com.suyin.expzhuan.service.ExperienceTaskService;
import com.suyin.nouser.mapper.NoUserPrototypeMapper;
import com.suyin.nouser.mapper.NouserMapper;
import com.suyin.nouser.model.Nouser;

@Service
public class ExperienceTaskServiceImpl implements ExperienceTaskService {


    @Autowired
    private ExperienceTaskMapper mapper;
    @Autowired
    private NouserMapper nouserMapper;

    @Autowired
    private AttachmentMapper attachmentMapper;

    @Autowired
    private MessageMapper messageMapper;

    @Autowired
    private CoinLogMapper coinLogMapper;

    @Autowired
    private NoUserPrototypeMapper noUserPrototypeMapper;

    @Override
    public List<Map<String,String>> findExperienceByTypeByPage(Map<String,Object> exp) {
        try {
            return this.mapper.findExperienceByTypeByPage(exp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 全名赚活动信息
     */
    @Override
	public List<Map<String, String>> findQuminzhuanExpByTypeByPage(Map<String, Object> exp) {
    	try{
    		return this.mapper.findQuminzhuanExpByTypeByPage(exp);
    	} catch(Exception e){
    		e.printStackTrace();
    		return null;
    	}
		
	}
    
    @Override
    public Map<String,String> findExpById(Map<String,Object> exp) {
        try {
            return this.mapper.findExpById(exp);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 参加全民赚活动,添加一个订单
     */
    @Override
    public JSONObject addExpZhuanOrder(Nouser user, String expId,String moduleType) {
        JSONObject jso=new JSONObject();
        String temp=user.getRegtype();
        synchronized (ExperienceTaskServiceImpl.class)
        {

            if((user=findNouserByPhoneOrByOpenid(user))==null) {
                jso.put("msg", "1"); //用户没有注册
                return jso;
            }

            int count = noUserPrototypeMapper.queryUserPrototypeAll(user.getUserId());
            if(count==0) {
                jso.put("msg", "5"); //用户资料没完成
                return jso;
            }

            ExperienceTaskOrder order=new ExperienceTaskOrder();
            order.setUserId(user.getUserId())
            .setExpId(Integer.parseInt(expId))
            .setModuleType(Integer.parseInt(moduleType)) 
            .setRegtype(Integer.parseInt(temp));
            if(order.getModuleType()==1){

                if(this.findExpCount(order)==1){

                    jso.put("msg", "11");//已经完了
                    return jso;
                }
            }
            if(isEverJoinedExp(order)) {

                jso.put("msg", "2");//用户已经参加过了
                return jso;
            }
            if(!isQualified(order)&&!"1".equals(moduleType)) {
			jso.put("msg", "4");//用户没有权限参加
			return jso;
		}
            if(joinExp(order)) {

                jso.put("msg", "0");// 参加成功
                return jso;
            }

        }
        return null;
    }

    private int findExpCount(ExperienceTaskOrder order){
        int result=0;
        Map<String, Object> exp=new HashMap<String, Object>();
        result=this.mapper.findExpCount(order);
        return result;

    }
    private boolean isQualified(ExperienceTaskOrder order) {
        return this.mapper.isExpTaskQualified(order)>0;
    }

    private Nouser findNouserByPhoneOrByOpenid(Nouser user) {
        if("0".equals(user.getRegtype())){
            user=this.nouserMapper.findNouserByOpenId(user);
        }else{
            user=this.nouserMapper.findNouserWhereByIphone(user);
        }
        return user;
    }

    /**
     * 判断用户是否参加过全民赚/齐心赚
     * @param user
     * @param expId
     * @return
     */
    private boolean isEverJoinedExp(ExperienceTaskOrder order) {
        return this.mapper.isEverJoinExp(order)>0;
    }
    /**
     * 参加全民赚/齐心赚,写入数据库
     * @return
     */
    @Transactional
    private boolean joinExp(ExperienceTaskOrder order) {
        try {
            this.mapper.addExpZhuanOrder(order);
            this.mapper.addJoinedUserCount(order);
            Map<String, String> message=new HashMap<String, String>();
            message.put("userId", order.getUserId().toString());
            message.put("expId", order.getExpId().toString());
            message.put("pattern", "您参加了【#】活动");
            this.messageMapper.addMessageForExpTask(message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 有人分享了齐心赚的活动，就要加钱。。。
     */
    @Override
    public void updateExpTaskOrderShareCoin(ExperienceTaskOrder order) {
        // 判断该活动是否正在进行中，不在进行中的不加钱
        if(getExpProgress(order)==0)  return ;
        int singleCoin=0;
        if((singleCoin=this.getSingleCoinForShare(order))==0)  return ;
        Map<String,Object> condition=new HashMap<String, Object>();
        condition.put("singleCoin", singleCoin);
        condition.put("userId", order.getUserId());
        condition.put("expId", order.getExpId());
        condition.put("expType", "0");
        Map<String,Object>resultMapInfo=this.mapper.findExpShareInfo(condition);
        //添加一个log
        CoinLog log=new CoinLog()
        .setUserId(order.getUserId())
        .setContent("参加了【"+resultMapInfo.get("title")+"】活动")
        .setDirection("1")
        .setGoldCoin(singleCoin);
        this.updateCoins(condition,log);
    }

    /**
     * 有人点击了齐心赚分享出去的链接之后，要给用户加钱啊。。。
     * @param order
     * @return
     */
    @Override
    public void updateExpZhuanOrderCoin(ExperienceTaskOrder order) {
        // 判断该活动是否正在进行中，不在进行中的不加钱
        if(getExpProgress(order)==0)  return ;
        int singleCoin=0;
        // 判断该次订单是否已经达到了用户所能获的金币的最大上限，达到了，就不加钱
        if((singleCoin=this.getSingleCoinForClick(order))==0)  return ;
        Map<String,Object> condition=new HashMap<String, Object>();
        condition.put("singleCoin", singleCoin);
        condition.put("orderId", order.getOrderId());
        condition.put("userId", order.getUserId());
        condition.put("expId", order.getExpId());
        condition.put("expType", "0");
        Map<String,Object>resultMapInfo=this.mapper.findExpShareInfo(condition);
        //根据活动id及微信用户userId查询是否已经分享
        CoinLog log=new CoinLog()
        .setUserId(order.getUserId())
        .setContent("朋友点击了你的【"+resultMapInfo.get("title")+"】分享")
        .setDirection("1")
        .setGoldCoin(singleCoin);
        this.updateCoins1(condition,log);
        //保存微信openid 标示
    }

    @Transactional
    private void updateCoins(Map<String,Object> condition,CoinLog coinLog) {
        if(this.mapper.updateExpTaskOrderCoin(condition)==0) return;
        this.mapper.updateExpTaskCoin(condition);
        this.mapper.updateUserCoin(condition);
        this.coinLogMapper.addCoinLog(coinLog);

    }

    @Transactional
    private void updateCoins1(Map<String,Object> condition,CoinLog coinLog) {
        this.mapper.updateExpTaskOrderCoin1(condition);
        this.mapper.updateExpTaskCoin(condition);
        this.mapper.updateUserCoin(condition);
        this.coinLogMapper.addCoinLog(coinLog);

    }

    /**
     * 获取活动的是否已经开始的状态
     * @param order
     * @return
     */
    private int getExpProgress(ExperienceTaskOrder order) {
        return this.mapper.getExpTaskProgress(order);
    }

    /**
     *  简单的说就是得到一次点击，用户获得多少金币； 不是简单的t_exp_taks中的user_browse_gold就可以的，还要判断是否超过了上限，
     *  还有判断是否到了剩余的下限,
     * @param order
     * @return 返回0，说明不要再改金币了，返回大于0，就要把用户的金币加上返回值，该订单的金币加上返回值，该活动的剩余金币减掉返回值
     */
    private int getSingleCoinForClick(ExperienceTaskOrder order) {
        Map<String,Integer> coinStatus=this.mapper.getExpAnOrderCoinStatus(order);
        int total=coinStatus.get("total_gold");   //该活动中，该用户已经获得的资产
        int browse=coinStatus.get("exp_gold_min");  //有人点击一次分享的链接，获得的资产数量
        int max=coinStatus.get("user_max_gold");  //该活动中，一个用户能够获得的最大资产
        int exp_remain_gold=coinStatus.get("exp_remain_gold");// 活动剩余资产
        if(total+browse<=max&&exp_remain_gold-browse>=0)  //既不超过个人上限，也不超过剩余下限
            return browse;
        else if(total+browse>max&&exp_remain_gold-browse>=0){ //超过个人上限，但不超过剩余下限
            return max-total;
        }else if(total+browse<=max&&exp_remain_gold-browse<0) //不超过个人上限，但超过剩余下限
            return exp_remain_gold;
        else if(total+browse>max&&exp_remain_gold-browse<0) { //超过个人上限，也超过剩余下限
            return 0;
        }else
            return 0;
    }

    /**
     *  简单的说就是用户分享出去获得多少金币； 不是简单的t_exp_taks中的user_browse_gold就可以的，还要判断是否超过了上限，
     *  还有判断是否到了剩余的下限,
     * @param order
     * @return 返回0，说明不要再改金币了，返回大于0，就要把用户的金币加上返回值，该订单的金币加上返回值，该活动的剩余金币减掉返回值
     */
    private int getSingleCoinForShare(ExperienceTaskOrder order) {
        Map<String,Integer> coinStatus=this.mapper.getExpAnOrderCoinStatus(order);
        int total=coinStatus.get("total_gold");   //该活动中，该用户已经获得的资产
        int share=coinStatus.get("user_share_gold");  //分享出去获得的资产
        int max=coinStatus.get("user_max_gold");  //该活动中，一个用户能够获得的最大资产
        int exp_remain_gold=coinStatus.get("exp_remain_gold");// 活动剩余资产
        if(total+share<=max&&exp_remain_gold-share>=0)  //既不超过个人上限，也不超过剩余下限
            return share;
        else if(total+share>max&&exp_remain_gold-share>=0){ //超过个人上限，但不超过剩余下限
            return max-total;
        }else if(total+share<=max&&exp_remain_gold-share<0) //不超过个人上限，但超过剩余下限
            return exp_remain_gold;
        else if(total+share>max&&exp_remain_gold-share<0) { //超过个人上限，也超过剩余下限
            return 0;
        }else
            return 0;
    }
    /**
     * 提交参加图片形式的全民赚
     */
    @Override
    @Transactional
    public void submitExpTaskA(ExperienceTaskOrder order,String attach) {
        this.mapper.updateExpTaskStatus(order);
        this.mapper.updateExpTaskImageUrl(order);
        Attachment a=new Attachment();
        a.setAttachment_ids(attach);
        a.setEntity(order.getOrderId());
      //  this.attachmentMapper.upAttachmentEntity(a);
    }

    /**
     * 保存全民赚form上传的答案
     */
    @Override
    public int addExpFormAnswer(Collection<HashMap> list,ExperienceTaskOrder order) {
        this.mapper.updateExpTaskStatus(order);
        return this.mapper.addExpFormAnswer(list);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> findExpForm(ExperienceTaskOrder order) {
        List<Map<String,Object>> list=this.mapper.findExpForm(order);
        Map<String,Object> empty=new HashMap<String, Object>();
        work(list,empty,0);
        return (List<Map<String, Object>>)empty.get("children");
    }

    @SuppressWarnings("unchecked")
    private void work(List<Map<String,Object>> list, Map<String,Object> empty, int pid){
        for(Map<String,Object> m:list) {
            if(Integer.parseInt(m.get("pdid").toString())==pid) {
                if(empty.get("children")==null) {
                    empty.put("children", new ArrayList<Map<String,Object>>());
                }
                ((ArrayList<Map<String, Object>>)empty.get("children")).add(m);
                work(list,m,Integer.parseInt(m.get("did").toString()));
            }
        }
    }

    @Override
    public Map<String, String> findExpZAppOrder(Map<String, Object>params) {
        return this.mapper.findExpZAppOrder(params);
    }

    @Override
    public List<Map<String, String>> getExpFormAnswer(Map<String, String> expId) {
        return this.mapper.getExpFormAnswer(expId);
    }

    /**
     * 查询全民赚app下载应用示例
     */
    @Override
    public Map<String, Object> findExpZAppDownInfo(Map<String, Object>params)
    {
        // TODO Auto-generated method stub
        return this.mapper.findExpZAppDownInfo(params);
    }

    /**
     * 查询齐心赚分享记录信息
     */
    @Override
    public  List<Map<String,Object>>  findExpTaskQinxinShareInfo(Map<String, Object> mapInfo)
    {
        // TODO Auto-generated method stub
        return this.mapper.findExpTaskQinxinShareInfo(mapInfo);
    }

    /**
     * 添加齐心赚分享记录
     */
    @Override
    public Integer addExpTaskQinxinShareInfo(Map<String, Object> mapInfo)
    {
        // TODO Auto-generated method stub
        Integer result=0;
        try
        {
            result=this.mapper.addExpTaskQinxinShareInfo(mapInfo);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 根据活动id查询当前活动的分享信息
     */
    @Override
    public net.sf.json.JSONObject findExpShareInfo(Map<String, Object> mapInfo)
    {
        mapInfo.put("expType", "0");
        // TODO Auto-generated method stub
        JSONObject js=new JSONObject();
        Map<String, Object> map=new HashMap<String, Object>();
        map=this.mapper.findExpShareInfo(mapInfo);
        if(null!=map){
            js.put("message", "success");
            js.put("data", map);  
        }else{

            js.put("message", "error");
        }
        return js;
    }

    @Override
    public Map<String, String> findQixinZhuanODetail(int id) {

        return this.mapper.findQixinZhuanODetail(id);
    }

}
