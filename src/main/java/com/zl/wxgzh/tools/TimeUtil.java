package com.zl.wxgzh.tools;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeUtil {

    private static Logger logger = LoggerFactory.getLogger(TimeUnit.class);

    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 时间戳转时间
     * @param timestamp
     * @return
     */
    public static String getDate(Long timestamp){
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        String result = null;
        try{
            result = format.format(timestamp);
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return result;
    }

    /**
     * 字符串时间转时间戳
     * @param date
     * @return
     */
    public static Long getTime(String date){
        SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
        Long result = null;
        try{
            result = format.parse(date).getTime();
        } catch (Exception e){
            logger.error(e.getMessage());
        }
        return result;
    }
}
