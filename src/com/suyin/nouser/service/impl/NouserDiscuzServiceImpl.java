package com.suyin.nouser.service.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suyin.nouser.mapper.NouserDiscuzMapper;
import com.suyin.nouser.model.NouserDiscuz;
import com.suyin.nouser.service.NouserDiscuzService;


@Transactional
@Service("NouserDiscuzService")
public class NouserDiscuzServiceImpl implements NouserDiscuzService
{

    private final static Logger log = Logger.getLogger(NouserDiscuzServiceImpl.class);

    @Autowired
    private NouserDiscuzMapper nouserDiscuzMapper;

    @Override
    public Integer addNouserDiscuz(NouserDiscuz entity)
        throws Exception
    {
        Integer result = 0;
        try
        {

            if (entity == null){
                return result;
            }else{
            	
            	result =nouserDiscuzMapper.queryDisUser(entity);
            	if(result!=1){
	                result = nouserDiscuzMapper.addNouserDiscuz(entity);
	                result= nouserDiscuzMapper.updateDiscussStatus(entity);
            	}else{
            		result=2;
            	}
            }
            
        }
        catch (Exception e)
        {
            throw new RuntimeException();
        }
        return result;
    }

	@Override
	public Integer findDiscussCount(Map<String, Object> map) throws Exception {
		Integer result=0;
		result= nouserDiscuzMapper.findDiscussCount(map);
		return result;
	}

	@Override
	public List<NouserDiscuz> findDiscussByPage(Map<String, Object> map) throws Exception {
		List<NouserDiscuz> list = new ArrayList<NouserDiscuz>();
		try {
			list=nouserDiscuzMapper.findDiscussByPage(map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list; 
	}

	@Override
	public List<Map<String,Object>> findDiscussA(NouserDiscuz dis){
		// TODO Auto-generated method stub
		return nouserDiscuzMapper.findDiscussA(dis);
	}

}
