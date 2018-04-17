package com.suyin.other.mapper;

import java.util.List;
import java.util.Map;

public interface OtherMapper {

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
