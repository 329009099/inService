package com.suyin.thememonth.mapper;


import java.util.List;

import java.util.*;
import com.suyin.thememonth.model.*;
import com.suyin.thememonth.service.*;




public interface ThemeMonthMapper {



    /**
     * 查询主题月信息
     */
    public Map<String, Object> findThemeMonth(Map<String, Object> themeInfo);



}
