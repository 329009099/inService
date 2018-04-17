/*
 * 文件名：NouserDiscuzMapper.java
 * 版权：Copyright by www.suyin.net
 * 描述：
 * 修改人：WX
 * 修改时间：2015-10-9
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.suyin.nouser.mapper;


import java.util.List;
import java.util.Map;

import com.suyin.nouser.model.NouserDiscuz;


public interface NouserDiscuzMapper
{

    Integer addNouserDiscuz(NouserDiscuz entity);

    /**
     * 根据产品id，活动详情id查询商户评论总行数
     */
	public Integer findDiscussCount(Map<String, Object> map);

	/**
	 * 根据商户id查询商户评论信息
	 */
	public List<NouserDiscuz> findDiscussByPage(Map<String, Object> map);
	public List<Map<String,Object>> findDiscussA(NouserDiscuz dis);
	
	/**
	 * 评价完修改评价状态
	 */
	public Integer updateDiscussStatus(NouserDiscuz entity);

	/**
	 * 查询该用户是否评价过
	 */
	public Integer queryDisUser(NouserDiscuz entity);
}
