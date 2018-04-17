package com.suyin.server.service;

import com.suyin.nouser.model.Nouser;

/**
 * 客户端请求token接口定义
 * @author lz
 * @version 2015-8-28
 * @see ClientTokenService
 * @since
 */
public interface ClientTokenService
{

    /**
     * 根据可以代表用户的唯一的key
     * 生成token缓存至redis
     * @param request
     * @return 
     * @see
     */
    String setUpToken(Nouser entity);

    /**
     * 客户端用户获取token标示相关信息 
     * @param res
     * @return 
     * @see
     */
    String getUserToken(Nouser entity);
}
