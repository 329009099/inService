package com.suyin.server.service.impl;


import java.util.Date;
import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.suyin.nouser.model.Nouser;
import com.suyin.server.redis.JedisUtil;
import com.suyin.server.service.ClientTokenService;
import com.suyin.system.util.ClientTokenUtil;
import com.suyin.system.util.Tools;

/**
 * 客户端获取Token实现类
 * @author lz
 * @version 2015-8-28
 * @see ClientTokenServiceImpl
 * @since
 */
@Transactional
@Service("ClientTokenService")
public class ClientTokenServiceImpl implements ClientTokenService
{
    private final static Logger log=Logger.getLogger(ClientTokenServiceImpl.class);
    @Autowired(required=false)
    private JedisUtil redisTemplate;


    /**
     * 设置token
     */
    @SuppressWarnings("unchecked")
    @Override
    public String setUpToken(Nouser entity)
    {
        // TODO Auto-generated method stub
        JSONObject jo=new JSONObject();
        try
        {
            if(entity!=null){
                String str=Tools.getValByKey("tokenTime", "/token.properties");//获取失效时长  
                String token= ClientTokenUtil.getToken(entity.getUserPhone(), entity.getVersion());
                long thisTime=new Date().getTime();
                long addTime=Long.parseLong(str);
                long countTime=thisTime+addTime;             
                jo.put("token",token);
                jo.put("time",countTime);     
                
                boolean falg=redisTemplate.setRedisStrValue(entity.getUserPhone(),jo.toJSONString());
                if(falg){

                    jo.put("message", "success");
                }else{

                    jo.put("message", "invalidtoken");
                }
            }else{

                jo.put("message", "invalidInfo");
            }

        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            log.error("ServiceImpl Error ClientTokenServiceImpl-> setUpToken "+e.getMessage());

        }
        return jo.toJSONString();
    }


    /**
     * 获取用户token
     */
    @SuppressWarnings("unchecked")
    @Override
    public String getUserToken(Nouser entity)
    {
        // TODO Auto-generated method stub
        JSONObject jo=new JSONObject();

        try
        {
            if(null!=entity){

                String str=redisTemplate.getRedisStrValue(entity.getUserPhone());
                if(!"".equals(str) && null!=str){

                    org.json.JSONObject j=new org.json.JSONObject(str);
                    Date d=new Date();
                    if(d.getTime()>Long.parseLong(j.get("time").toString())){

                        jo.put("message", "timeout");
                    }else{

                        jo.put("token",j.get("token"));
                        jo.put("message", "success");
                    }

                }else{

                    jo.put("message", "isemptytoken");
                }
            }else{

                jo.put("message", "invalidInfo");
            }
        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            log.error("ServiceImpl Error ClientTokenServiceImpl-> getUserToken "+e.getMessage());

        }
        return jo.toJSONString();
    }

}
