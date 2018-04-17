package com.suyin.actrecord.service;

import java.util.List;

import java.util.*;
import com.suyin.actrecord.model.*;
import com.suyin.actrecord.service.*;




public interface ActRecordService{
	
	/**
	 * 参与投票
	 * @param act
	 * @return
	 */
 public	Map<String,Object> insertJoinAct(ActRecord act);
}
