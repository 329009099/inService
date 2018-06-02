package com.suyin.decoratevoucher.service.impl;

import java.util.List;
import java.util.Map;

import com.suyin.decoratevoucher.mapper.ExpDecorateVoucherMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suyin.decoratevoucher.model.*;
import com.suyin.decoratevoucher.service.*;



@Transactional
@Service("expDecorateVoucherService")
public class ExpDecorateVoucherServiceImpl implements ExpDecorateVoucherService{

    private final static Logger log=Logger.getLogger(ExpDecorateVoucherServiceImpl.class);
    
    @Autowired
    private ExpDecorateVoucherMapper ExpDecorateVoucherMapper; 


    /**
     * 查找信息列表(分页)
     * @param entity
     * @return
     */
    @Override
    public List<ExpDecorateVoucher> findExpDecorateVoucherByPage( Map<String,Object> condition){
        
        
        return ExpDecorateVoucherMapper.findExpDecorateVoucherByPage(condition);
    }

    /**
     * 根据id查询对应的信息
     * @param entity
     * @return
     */
    @Override
    public ExpDecorateVoucher findExpDecorateVoucherById(ExpDecorateVoucher entity){
        
        List<ExpDecorateVoucher> list=ExpDecorateVoucherMapper.findExpDecorateVoucher(entity);
        return list!=null&&!list.isEmpty()?list.get(0):null;
    }

	@Override
	public ExpDecorateVoucher findDetial(String id) {
		// TODO Auto-generated method stub
		return ExpDecorateVoucherMapper.findDetial(id);
	}

	@Override
	public List<ExpDecorateUserVoucher> findExpDecorateUserVoucherByPage(
			Map<String,Object> condition) {
		// TODO Auto-generated method stub
		return ExpDecorateVoucherMapper.findExpDecorateUserVoucherByPage(condition);
	}

	@Override
	public void saveUserVoucher(ExpDecorateUserVoucher voucherUser) {
		// TODO Auto-generated method stub
		ExpDecorateVoucherMapper.saveUserVoucher(voucherUser);
	}

	@Override
	public void updateRemNum(Integer id) {
		// TODO Auto-generated method stub
		ExpDecorateVoucherMapper.updateRemNum(id);
	}

	@Override
	public void updateVoucherPayState(Map<String,Object>params) {
		// TODO Auto-generated method stub
		ExpDecorateVoucherMapper.updateVoucherPayState(params);
	}
}
