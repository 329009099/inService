package com.suyin.common;

import java.text.DecimalFormat;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

public class Utils {
	public static void main(String[] args) throws Exception {
        double max = 0.08;
        double min = 1.08;
        DecimalFormat    df   = new DecimalFormat("######0.00");
        for (int i = 0; i < 10; i++) {
            System.out.println(df.format(Utils.nextDouble(min - 1, max + 1)));
		}

    }
	/**
	 * 浮点数 金额 区间随机
	 * @param min
	 * @param max
	 * @return
	 * @throws Exception
	 */
    public static double nextDouble(final double min, final double max) throws Exception {
        if (max < min) {
            throw new Exception("min < max");
        }
        if (min == max) {
            return min;
        }
        
        return min + ((max - min) * new Random().nextDouble());
    }
    /**
     * 统一的参数接受
     * @param request
     * @param result
     */
	public static void fillResult(HttpServletRequest request, ModelMap result) {
		result.put("module", request.getParameter("module"));
		result.put("regtype", request.getParameter("regtype"));
		result.put("version", request.getParameter("version"));
		if(null!=request.getParameter("userId"))
		{
		    result.put("userid", request.getParameter("userId"));
		}
	}
}
