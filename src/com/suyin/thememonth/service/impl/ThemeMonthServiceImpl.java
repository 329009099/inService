package com.suyin.thememonth.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.suyin.thememonth.mapper.ThemeMonthMapper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.suyin.system.util.Md5Util;

import java.util.*;
import com.suyin.thememonth.model.*;
import com.suyin.thememonth.service.*;



@Transactional
@Service("ThemeMonthService")
public class ThemeMonthServiceImpl implements ThemeMonthService{

    private final static Logger log=Logger.getLogger(ThemeMonthServiceImpl.class);

    @Autowired
    private ThemeMonthMapper ThemeMonthMapper;


    /**
     * 查询主题月信息
     */
    @Override
    public Map<String, Object> findThemeMonth(Map<String, Object> themeInfo)
    {
        // TODO Auto-generated method stub
        return ThemeMonthMapper.findThemeMonth(themeInfo);
    } 


}
