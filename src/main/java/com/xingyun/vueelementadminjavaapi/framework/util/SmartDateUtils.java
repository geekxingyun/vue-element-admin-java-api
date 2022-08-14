package com.xingyun.vueelementadminjavaapi.framework.util;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author qing-feng.zhao
 */
@Slf4j
@Component
public class SmartDateUtils {

    private static final SimpleDateFormat YYYY_MM_DD_HH_MM_SS_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat YYYY_MM_DD_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat YYYY_MM_FORMAT = new SimpleDateFormat("yyyyMM");
    /**
     * 将字符串转换成年月日时分秒
     * @param strDate 2022-04-24 18:15:30
     * @return Date
     */
    public  static synchronized Date convertStrToYYYY_MM_DD_HH_MM_SS_Date(String strDate) {
        ParsePosition pos = new ParsePosition(0);
        Date stringToDate = YYYY_MM_DD_HH_MM_SS_FORMAT.parse(strDate, pos);
        return stringToDate;
    }
    /**
     * 将日期类型转字符串
     * @param date
     * @return
     */
    public static synchronized String convertDateToYYYY_MM_DD_HH_MM_SS_Str(Date date){
        return YYYY_MM_DD_HH_MM_SS_FORMAT.format(date);
    }
    /**
     * 将字符串转换成年月日时分秒
     * @param strDate 2022-04-24 18:15:30
     * @return Date
     */
    public  static synchronized Date convertStrToYYYY_MM_DD_Date(String strDate) {
        ParsePosition pos = new ParsePosition(0);
        Date stringToDate = YYYY_MM_DD_FORMAT.parse(strDate, pos);
        return stringToDate;
    }
    /**
     * 将日期类型转字符串
     * @param date
     * @return
     */
    public static synchronized String convertDateToYYYY_MM_DD_Str(Date date){
        return YYYY_MM_DD_FORMAT.format(date);
    }

    /**
     * 年年月月
     * @param date
     * @return
     */
    public static synchronized String convertDateToYYYY_MM_Str(Date date){
        return YYYY_MM_FORMAT.format(date);
    }

    /**
     * 获取当前日期和时间
     * @return
     */
    public static synchronized String getNowDateTimeStr(){
        return SmartDateUtils.convertDateToYYYY_MM_DD_HH_MM_SS_Str(new Date());
    }

    public synchronized static  String  getStartTime() {
        Calendar todayStart = Calendar.getInstance();
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        Date todayTime = todayStart.getTime();
        return SmartDateUtils.convertDateToYYYY_MM_DD_HH_MM_SS_Str(todayTime);
    }

    /**
     * 获取今天结束时间
     */
    public synchronized static  String getEndTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        Date todayTime = todayEnd.getTime();
        return SmartDateUtils.convertDateToYYYY_MM_DD_HH_MM_SS_Str(todayTime);
    }
    /**
     * 获取开始日期
     * @param startDate
     * @return
     */
    public static synchronized Date getStartDateFilter(Date startDate){
        if(null==startDate){
            return null;
        }
        String startDateString= convertDateToYYYY_MM_DD_Str(startDate)+" 00:00:00";
        return convertStrToYYYY_MM_DD_HH_MM_SS_Date(startDateString);
    }
    /**
     * 获取结束时间
     * @param endDate
     * @return
     */
    public static synchronized Date getEndDateFilter(Date endDate){
        if(null==endDate){
            return null;
        }
        String endDateString= convertDateToYYYY_MM_DD_Str(endDate)+" 23:59:59";
        return convertStrToYYYY_MM_DD_HH_MM_SS_Date(endDateString);
    }

    /**
     * 计算相差多少天
     * @param nowDate
     * @param endDate
     * @return
     */
    public  static synchronized Long timeDifferenceDay(Date nowDate,Date endDate){
        return (endDate.getTime() - nowDate.getTime()) / (1000L*3600L*24L);
    }
    /**
     * 将指定的日期字符串转换成日期
     * @param dateStr 日期字符串
     * @param pattern 格式
     * @return 日期对象
     */
    public static synchronized Date parseStringToDate(String dateStr, String pattern)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        Date date;
        try {
            date = sdf.parse(dateStr);
        } catch (ParseException e) {
            throw  new RuntimeException("日期转化错误");
        }
        return date;
    }
    /**
     * 检查当前日期是否是今天
     * @param date
     * @return
     */
    public static synchronized Boolean checkDateIsToday(Date date){
        // 获取当天凌晨0点0分0秒Date
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(calendar1.get(Calendar.YEAR), calendar1.get(Calendar.MONTH), calendar1.get(Calendar.DAY_OF_MONTH),
                0, 0, 0);
        Date beginOfDate = calendar1.getTime();
        // 获取当天23点59分59秒Date
        Calendar calendar2 = Calendar.getInstance();
        calendar1.set(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH),
                23, 59, 59);
        Date endOfDate = calendar1.getTime();
        if(date == null){
            return false;
        }
        if(date.getTime() >= beginOfDate.getTime() && date.getTime() <= endOfDate.getTime()){
            return true;
        }
        else {
            return false;
        }
    }
    /**
     * 字符串转Long类型
     * @param parseDateTime
     * @return
     */
    public static Long parseDateTimeToLong(String parseDateTime) {
        Date date = null;
        try {
            date = YYYY_MM_DD_HH_MM_SS_FORMAT.parse(parseDateTime);
        } catch (ParseException e) {
           log.error("日期格式出错",e);
        }
        return date.getTime();
    }

    /**
     * long 转日期时间类型
     * @param parseDateTime
     * @return
     */
    public static String parseLongToDateTime(Long parseDateTime) {
        Date date=new Date(parseDateTime);
        return YYYY_MM_DD_HH_MM_SS_FORMAT.format(date);
    }
    public static String changeDateStr(String dateStr,String oldFormat,String newFormat){
        try{
            Date date = new SimpleDateFormat(oldFormat).parse(dateStr);
            return new SimpleDateFormat(newFormat).format(date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 静态方法工具类应该禁用构造方法
     */
    private SmartDateUtils(){}
}
