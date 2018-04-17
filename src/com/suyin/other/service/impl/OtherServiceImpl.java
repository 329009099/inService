/*
 * 文件名：OtherServiceImpl.java
 * 版权：Copyright by www.isure.net
 * 描述：
 * 修改人：windows7
 * 修改时间：2015-12-22
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.suyin.other.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.suyin.other.mapper.OtherMapper;
import com.suyin.other.service.OtherService;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author lz
 * @version 2015-12-22
 * @see OtherServiceImpl
 * @since
 */
@Transactional
@Service("OtherService")
public class OtherServiceImpl implements OtherService
{
    @Autowired
    private OtherMapper otherMapper;

    /* (non-Javadoc)
     * @see com.suyin.other.service.OtherService#findAdvs(java.util.Map)
     */
    @Override
    public List<Map<String, String>> findAdvs(Map<String, Object> params)
    {
        // TODO Auto-generated method stub
        return otherMapper.findAdvs(params);
    }

    /* (non-Javadoc)
     * @see com.suyin.other.service.OtherService#findAbout(java.lang.String)
     */
    @Override
    public List<Map<String, String>> findAbout(String type)
    {
        // TODO Auto-generated method stub
        return otherMapper.findAbout(type);
    }

    /* (non-Javadoc)
     * @see com.suyin.other.service.OtherService#findActIntoDetail(java.lang.String)
     */
    @Override
    public Object findActIntoDetail(String type)
    {
        // TODO Auto-generated method stub
        return otherMapper.findActIntoDetail(type);
    }

    /* (non-Javadoc)
     * @see com.suyin.other.service.OtherService#findAdvsById(java.lang.String)
     */
    @Override
    public Map<String, String> findAdvsById(String id)
    {
        // TODO Auto-generated method stub
        return otherMapper.findAdvsById(id);
    }

    /* (non-Javadoc)
     * @see com.suyin.other.service.OtherService#get1()
     */
    @Override
    public List<Map<String, String>> get1()
    {
        // TODO Auto-generated method stub
        return otherMapper.get1();
    }

    /* (non-Javadoc)
     * @see com.suyin.other.service.OtherService#get2(java.lang.String)
     */
    @Override
    public List<Map<String, String>> get2(String k)
    {
        // TODO Auto-generated method stub
        return otherMapper.get2(k);
    }

}
