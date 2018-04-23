package com.suyin.decorate.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suyin.common.Utils;
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
	public Map<String, Object>  withdrawCreateOrder(ExpDecorateUser entity, String withdrawPrice) {
		Map<String, Object> result=new HashMap<String, Object>();
		//先判断传入的密码是否正确
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("state","0");//未审核状态
		params.put("openId", entity.getOpenid());
		//根据openid查询用户是否存在还未审核通过的提现订单，提现审核通过后才可以创建，提交新的订单
		ExpDecorateOrder isDecorateUser=expDecorateOrderMapper.findUserIsReviewOrderInfo(params);
		if(null!=isDecorateUser){
				//未审核通过标识
				result.put("message", "notaudit");
		}else{
			//查询用户信息
			ExpDecorateUser expDecorateUser = this.expDecorateUserMapper.findUserInfoByUserIdOrOpenId(params);
			if( null!=expDecorateUser && entity.getPassword().equals(expDecorateUser.getPassword())){
				try{
					//用户当前账户余额
					BigDecimal balancePrice = expDecorateUser.getBalancePrice();	
					//本次提现金额
					DecimalFormat df= new DecimalFormat("######0.00");
					double convertPrice=Double.parseDouble(withdrawPrice);
					String commission= df.format(convertPrice);			
					BigDecimal withdrawPriceValue = new BigDecimal(commission);				
					//判断余额是否大于当前提现金额
					if(balancePrice.compareTo(withdrawPriceValue)>=0){
						//相减金额，当前金额-提现金额=现在金额
						double reckonPrice= Utils.subUserPrice(balancePrice.doubleValue(), withdrawPriceValue.doubleValue());					
						//创建提现订单信息
						ExpDecorateOrder expDecorateOrder = new ExpDecorateOrder();
						expDecorateOrder.setOpenid(expDecorateUser.getOpenid());
						expDecorateOrder.setUserId(expDecorateUser.getUserId());
						expDecorateOrder.setState(0);
						expDecorateOrder.setWithdrawPrice(withdrawPriceValue);
						int addorder=expDecorateOrderMapper.addExpDecorateOrder(expDecorateOrder);
						//修改余额			
						BigDecimal lastBalancePrice = new BigDecimal(reckonPrice);
						expDecorateUser.setBalancePrice(lastBalancePrice);					
						int updateUserInfo=expDecorateUserMapper.updateBalancePriceByOpendId(expDecorateUser);
						if(addorder>0 && updateUserInfo>0){
							result.put("message", "success");
						}
					}else{
						//余额不足
						result.put("message", "lessmoney");
					}				
				}catch(Exception ex){
					
					log.error("提现失败",ex);
				}
			}else{
				result.put("message", "invalidP");
			}
		}

		//提现失败
		return result;
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
				//查询用户总额是否封顶,封顶则不能继续
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
				entity.setUserState(0);
				result = expDecorateUserMapper.initSaveDecorateUser(entity);
			}
		} catch (Exception e) {

			log.error("通过Openid修改余额异常"+e.getMessage());
			new RuntimeException();
			e.printStackTrace();
		}
		return result;

	}

	public static void main(String[] args) {
		//用户余额
		BigDecimal balancePrice =new BigDecimal(0.2);	
		//本次提现金额		
		BigDecimal withdrawPriceValue = new BigDecimal(0.29);
		if(balancePrice.compareTo(withdrawPriceValue)>=0){
			System.out.println("大于");
		}else{
			System.out.println("不大于");

		}
	}
}
