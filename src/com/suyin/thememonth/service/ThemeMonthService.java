package com.suyin.thememonth.service;

import java.util.List;
import java.util.Map;

import java.util.*;
import com.suyin.thememonth.model.*;
import com.suyin.thememonth.service.*;




public interface ThemeMonthService{


    /**
     * 查询主题月信息
     */
    public Map<String, Object> findThemeMonth(Map<String, Object> themeInfo);
}
