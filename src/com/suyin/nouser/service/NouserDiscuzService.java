/*
 * 文件名：NouserDiscuzService.java
 * 版权：Copyright by www.suyin.net
 * 描述：
 * 修改人：WX
 * 修改时间：2015-10-9
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.suyin.nouser.service;


import java.util.List;
import java.util.Map;

import com.suyin.nouser.model.NouserDiscuz;


public interface NouserDiscuzService
{

    Integer addNouserDiscuz(NouserDiscuz entity)
        throws Exception;

    
    /**
     * 根据产品id，活动详情id查询商户评论总数
     */
	public Integer findDiscussCount(Map<String, Object> map) throws Exception;


	/**
	 * 根据产品id，活动详情id分页查询商户评论信息
	 */
	public List<NouserDiscuz> findDiscussByPage(Map<String, Object> map)throws Exception;
	
	public List<Map<String,Object>> findDiscussA(NouserDiscuz dis);


}
