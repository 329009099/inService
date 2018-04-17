/*
 * 文件名：OtherService.java
 * 版权：Copyright by www.isure.net
 * 描述：
 * 修改人：windows7
 * 修改时间：2015-12-22
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.suyin.other.service;

import java.util.List;
import java.util.Map;

/**
 * 〈一句话功能简述〉
 * 〈功能详细描述〉
 * @author lz
 * @version 2015-12-22
 * @see OtherService
 * @since
 */

public interface OtherService
{
    /**
     * 查找广告，type==0是首页广告，type==1是发现广告
     * 
     * @param type
     * @return
     */
    public List<Map<String, String>> findAdvs(Map<String,Object> params);

    public List<Map<String, String>> findAbout(String type);

    public Object findActIntoDetail(String type);
    
    public Map<String,String> findAdvsById(String id);
    
    public List<Map<String,String>> get1();
    
    public List<Map<String,String>> get2(String k);

}
