package com.suyin.exporder.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.suyin.exporder.mapper.ExpVoucherMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.suyin.system.util.Md5Util;

import java.util.*;
import com.suyin.exporder.model.*;
import com.suyin.exporder.service.*;



@Transactional
@Service("ExpVoucherService")
public class ExpVoucherServiceImpl implements ExpVoucherService{

    private final static Logger log=Logger.getLogger(ExpVoucherServiceImpl.class);

    @Autowired
    private ExpVoucherMapper ExpVoucherMapper; 

    /**
     * 新增信息
     * @param entity
     * @return
     */
    @Override
    public Integer addExpVoucher(ExpVoucher entity){
        Integer result=0;
        try {

            if(entity==null){
                return result;
            }else{
                result = ExpVoucherMapper.addExpVoucher(entity);
            }

        } catch (Exception e) {

            new RuntimeException();
        }
        return result;

    }
}
