package com.xiaomimanager.sms;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 工具类
 *@Copyright Copyright (c) 2012 - 2100
  *@author    Administrator
 *@create at 2013-5-9
 *@version 1.1.0
 */
public class Util {
    /**
     * 设置时间格式
     * @return
     */
    public static String getDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// 设置时间格式
        String strDate = format.format(date);
        return strDate;

    }
}
