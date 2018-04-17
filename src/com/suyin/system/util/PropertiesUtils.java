/*
 * 文件名：PropertiesUtils.java
 * 版权：Copyright by www.suyin.net
 * 描述：Properties工具类
 * 修改人：cqm
 * 修改时间：2011-11-01
 * 跟踪单号：
 * 修改单号：
 * 修改内容：
 */

package com.suyin.system.util;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * 
 * 文件读取 
 * @version 2015-8-25
 * @see PropertiesUtils
 * @since
 */
public class PropertiesUtils
{

    /**
     * 日志
     */
    private static final Logger logger = Logger.getLogger(PropertiesUtils.class);

    /**
     * 
     * Description: 读入properties文件
     * 
     * @param pPath
     * @return Properties
     * @throws IOException 
     * @see
     */
    public Properties getProperties(String pPath)
    {
        Properties p = null;
        try
        {
            InputStream is = getClass().getResourceAsStream(pPath);
            if (is == null)
            {
                logger.error("properties file stream is null");
            }

            p = new Properties();
            p.load(is);
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }
        return p;
    }

    /**
     * 根据键获取配置文件的值
     * 
     * @param key
     * @param path 配置文件路径
     * @return 
     * @see
     */
    public static String getValByKey(String key, String path)
    {
        String val = "";
        Properties p = new Properties();
        //
        p = new PropertiesUtils().getProperties(path);
        val = p.getProperty(key);
        return val;
    }

}