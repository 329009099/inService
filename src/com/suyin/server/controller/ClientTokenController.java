package com.suyin.server.controller;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.suyin.nouser.model.Nouser;
import com.suyin.nouser.service.NouserService;
import com.suyin.server.service.ClientTokenService;
import com.suyin.system.util.ClientTokenUtil;

/**
 * 客户端请求标识符是否超时
 * 获取缓存中用户的唯一标示
 * @author lz
 *
 */
@Controller
@RequestMapping("/client")
public class ClientTokenController {

    private final static Logger log=Logger.getLogger(ClientTokenController.class);
    @Autowired
    private ClientTokenService clientTokenService;
    @Autowired
    private NouserService nouserService;
    /**
     * 客户端用户获取token
     * @return
     * @throws UnsupportedEncodingException 
     * @throws NoSuchAlgorithmException 
     * @throws InvalidKeyException 
     */
    @RequestMapping(value="/getToken")
    public @ResponseBody String getUserToken(HttpServletRequest request,Nouser entity){
        String result="";
        try
        {            
            if("0".equals(entity.getRegtype())){

                entity= nouserService.findNouserByOpenId(entity);
    
            }

            result=clientTokenService.getUserToken(entity);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            log.error("Controller Error ClientTokenController-> requestToken "+e.getMessage());
        }

        return result;
    }


    /**
     * 设置token
     * @param request
     * @param entity
     * @return 
     * @see
     */
    @RequestMapping(value="/resetToken")
    public @ResponseBody String resetToken(HttpServletRequest request,Nouser entity){

        String result="";
        try
        {  
            if("0".equals(entity.getRegtype())){

                entity= nouserService.findNouserByOpenId(entity);

            }
            result=clientTokenService.setUpToken(entity);
        }
        catch (Exception e)
        {
            // TODO Auto-generated catch block
            log.error("Controller Error ClientTokenController-> resetToken "+e.getMessage());
        }

        return result;
    }
}
