package com.suyin.decorate.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suyin.decorate.mapper.ExpDecorateOrderMapper;
import com.suyin.decorate.mapper.ExpDecorateUserMapper;
import com.suyin.decorate.model.ExpDecorateOrder;
import com.suyin.decorate.model.ExpDecorateUser;
import com.suyin.decorate.service.ExpDecorateUserService;



@Transactional
@Service("ExpDecorateUserService")
public class ExpDecorateUserServiceImpl implements ExpDecorateUserService{

    private final static Logger log=Logger.getLogger(ExpDecorateUserServiceImpl.class);
    
    @Autowired
    private ExpDecorateUserMapper expDecorateUserMapper; 
    
    @Autowired
    private ExpDecorateOrderMapper expDecorateOrderMapper;


    /**
	 * 通过用户ID和openId查询用户信息
	 * @param userId
	 * @param openId
	 * @return
	 */
	@Override
	public ExpDecorateUser findUserInfoByUserIdOrOpenId(String userId,String openId) {
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("userId",userId);
		params.put("openId",openId);
		return expDecorateUserMapper.findUserInfoByUserIdOrOpenId(params);
	}
    

	 /**
	     * 提现创建订单
	     */
	    @Override
		public Integer withdrawCreateOrder(ExpDecorateUser entity, String withdrawPrice) {
	    	//先判断传入的密码是否正确
	    	Map<String, Object> params = new HashMap<String, Object>();
	    	params.put("userId", entity.getUserId());
			ExpDecorateUser expDecorateUser = this.expDecorateUserMapper.findUserInfoByUserIdOrOpenId(params);
	    	if(expDecorateUser != null && entity.getPassword().equals(expDecorateUser.getPassword())){
	    		//创建提现订单信息
	    		ExpDecorateOrder expDecorateOrder = new ExpDecorateOrder();
	    		expDecorateOrder.setCreateTime(new Date());
	    		expDecorateOrder.setOpenid(entity.getOpenid());
	    		expDecorateOrder.setUserId(entity.getUserId());
	    		expDecorateOrder.setState(0);
	    		expDecorateOrder.setWithdrawPrice(Long.valueOf(withdrawPrice));
	    		expDecorateOrderMapper.addExpDecorateOrder(expDecorateOrder);
	    		//修改余额
	    		BigDecimal withdrawPriceValue = new BigDecimal(withdrawPrice);
	    		BigDecimal balancePrice = entity.getBalancePrice();
	    		balancePrice =balancePrice.subtract(withdrawPriceValue);  
	    		entity.setBalancePrice(balancePrice);
	    		expDecorateUserMapper.updateBalancePriceByOpendId(entity);
	    		return 1;
	    	}
	    
	    	//提现失败
			return 0;
		}
	    
    /**
     * 新增信息
     * @param entity
     * @return
     */
    @Override
    public Integer addExpDecorateUser(ExpDecorateUser entity){
        Integer result=0;
        try {

            if(entity==null){
                return result;
            }else{
                result = expDecorateUserMapper.addExpDecorateUser(entity);
            }

        } catch (Exception e) {

            new RuntimeException();
        }
        return result;

    }

    /**
     * 修改信息
     * @param entity
     * @return
     */
    @Override
    public Integer updateExpDecorateUser(ExpDecorateUser entity){

        Integer result=0;
        try {
            if(entity==null){

                return result;
            }else{

                result = expDecorateUserMapper.updateExpDecorateUser(entity);
            }
        } catch (Exception e) {
            
            log.error("ExpDecorateUser信息修改异常"+e.getMessage());
            new RuntimeException();
            e.printStackTrace();
        }
        return result;

    }
    /**
     * 通过Openid修改余额
     */
    @Override
    public Integer updateBalancePriceByOpendId(ExpDecorateUser entity){
    	
    	Integer result=0;
    	try {
    		if(entity==null){	
    			return result;
    		}else{
    			result = expDecorateUserMapper.updateBalancePriceByOpendId(entity);
    		}
    	} catch (Exception e) {
    		
    		log.error("通过Openid修改余额异常"+e.getMessage());
    		new RuntimeException();
    		e.printStackTrace();
    	}
    	return result;
    	
    }

    /**
     * 删除信息
     * @param id
     * @return
     */
    @Override
    public Integer deleteExpDecorateUser(String id){
        
        
        return expDecorateUserMapper.deleteExpDecorateUser(id);
    }

    /**
     * 查找信息列表
     * @param entity
     * @return
     */
    @Override
    public List<ExpDecorateUser> findExpDecorateUser(ExpDecorateUser entity){
        
        
        return expDecorateUserMapper.findExpDecorateUser(entity);
    }

    /**
     * 查找信息列表(分页)
     * @param entity
     * @return
     */
    @Override
    public List<ExpDecorateUser> findExpDecorateUserByPage(ExpDecorateUser entity){
        
        
        return expDecorateUserMapper.findExpDecorateUserByPage(entity);
    }

    /**
     * 根据id查询对应的信息
     * @param entity
     * @return
     */
    @Override
    public ExpDecorateUser findExpDecorateUserById(ExpDecorateUser entity){
        
        List<ExpDecorateUser> list=expDecorateUserMapper.findExpDecorateUser(entity);
        return list!=null&&!list.isEmpty()?list.get(0):null;
    }

	@Override
	public Integer initSaveDecorateUser(ExpDecorateUser entity) {
		// TODO Auto-generated method stub
	 	Integer result=0;
    	try {
    		if(entity==null){	
    			return result;
    		}else{
    			result = expDecorateUserMapper.initSaveDecorateUser(entity);
    		}
    	} catch (Exception e) {
    		
    		log.error("通过Openid修改余额异常"+e.getMessage());
    		new RuntimeException();
    		e.printStackTrace();
    	}
    	return result;
    	
	}

}
