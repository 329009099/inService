package com.suyin.actrecord.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.suyin.actrecord.mapper.ActRecordMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suyin.participator.mapper.ParticipatorMapper;
import com.suyin.participator.model.Participator;
import com.suyin.system.util.Md5Util;

import java.util.*;

import com.suyin.actrecord.model.*;
import com.suyin.actrecord.service.*;



@Transactional
@Service("actRecordService")
public class ActRecordServiceImpl implements ActRecordService{

    private final static Logger log=Logger.getLogger(ActRecordServiceImpl.class);
    
    @Autowired
    private ActRecordMapper actRecordMapper;

    @Autowired
    private ParticipatorMapper ParticipatorMapper; 
    
	@Override
	public Map<String,Object> insertJoinAct(ActRecord act) {
		// TODO Auto-generated method stu
		Map<String,Object>result=new HashMap<String, Object>();
		//根据活动id查询活动是否已经开始或结束
		String state=actRecordMapper.findActstate();
		if("0".equals(state)){
			Map<String,Object>params=new HashMap<String, Object>();
			params.put("openId", act.getOpenid());
			Integer partId=act.getPartId();
			if(null!=partId){
			    Participator participator=ParticipatorMapper.findParticipatorDetailById(partId);
			    if(null!=participator){
					params.put("partId", partId);
					ActRecord actRecd= actRecordMapper.findIsActRecord(params);
					if(null!=actRecd){
						//已经参与过了 提示
					     result.put("status", "invalid");
					}else{
						try {
							//记录新的数据
							act.setIsCord(1);
							act.setActId(113);//活动Id 暂时先固定
							actRecordMapper.insertJoinAct(act);
							//更新当前选手的票数
							ParticipatorMapper.updatePartNumber(act.getPartId());
							result.put("status", "success");
						} catch (Exception e) {
							// TODO: handle exception
							log.error("参与投票出现异常:",e);
						}
						
					}
			    }else{
			    	//非法参数，请从首页进入为选手投票
			    	result.put("status", "nopart");	
			    }
			}else{
				//非法入口 没有参数
				result.put("status", "invalidmain");
			}
		}else if("1".equals(state)){
			  result.put("status", "nobegin");
		}else if("2".equals(state)){
			  result.put("status", "end");
		}else{
			  result.put("status", "other");
		}
		return result;
				
	} 

}
