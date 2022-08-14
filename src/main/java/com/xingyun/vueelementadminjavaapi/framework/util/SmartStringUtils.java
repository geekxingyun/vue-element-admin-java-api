package com.xingyun.vueelementadminjavaapi.framework.util;

import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author qing-feng.zhao
 */
@Slf4j
public class SmartStringUtils {
    /**
     * 获取雪花算法分布式Id
     * @return
     */
    public static Long getSnowFlakeId(){
        return SnowFlakeUtils.nextId();
    }
    /**
     * 获取雪花算法分布式Id字符串
     * @return
     */
    public static String getSnowFlakeStrId(){
        return String.valueOf(SnowFlakeUtils.nextId());
    }
    /**
     * 获取UUID
     * @return
     */
    public static String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    /**
     * 创建任意位数的UUID
     * @param digits
     * @return
     */
    public static String getCustomizedDigitsUuid(Integer digits){
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0,digits);
    }
    public static Boolean checkStrToLong(String contentStr){
        Boolean checkFlag;
        try {
            Long.valueOf(contentStr);
            checkFlag=true;
        } catch (NumberFormatException e) {
            log.error("contentStr:{},格式转化异常");
            checkFlag=false;
        }
        return checkFlag;
    }
    /**
     * 逗号分割字符串转集合
     * @param data
     * @return
     */
    public static List<String> splitStrConvertToList(String data){
        String[] matchIds=data.split(",");
        List<String> matchIdsArrayList = new ArrayList<>(matchIds.length);
        Collections.addAll(matchIdsArrayList,matchIds);
        return matchIdsArrayList;
    }
    /**
     * 流水号格式校验
     * @param swiftNumber
     * @return
     */
    public static Boolean checkSwiftNumberFormat(String swiftNumber){
        if(null==SmartStringUtils.trimToNull(swiftNumber)){
            return false;
        }
        String[] list= swiftNumber.split("_");
        if(null==list||list.length!=3){
            return false;
        }
        return true;
    }
    /**
     * 从流水号中获取ApiCode
     * @param swiftNumber 流水号
     * @return
     */
    public static String fetchApiCodeFromSwiftNumber(String swiftNumber){
        String[] list= swiftNumber.split("_");
        return list[0];
    }
    /**
     * 从流水号中获取请求日期 年月日十分秒
     * @param swiftNumber 流水号
     * @return
     */
    public static String fetchRequestDateFromSwiftNumber(String swiftNumber){
        String[] list= swiftNumber.split("_");
        return list[1];
    }
    public static String fetchStartTimeFromSwiftNumber(String swiftNumber){
        String[] list= swiftNumber.split("_");
        String startTime=list[1];
        return SmartDateUtils.changeDateStr(startTime,"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss");
    }

    public static String fetchEndTimeFromSwiftNumber(String swiftNumber){
        String[] list= swiftNumber.split("_");
        String endTime=list[1].substring(0,8)+"235959";
        return SmartDateUtils.changeDateStr(endTime,"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss");
    }
    /**
     * | 分割字符串并去重
     * @param originData
     * @return
     */
    public static Set<String> parseDataToSetList(String originData){
        String[] ipArray=originData.split("\\|");
        Set<String> ipList=new HashSet<>();
        for (String item:ipArray
        ) {
            ipList.add(item);
        }
        return ipList;
    }


    /**
     * 空字符串处理
     * @param str
     * @return
     */
    public static String trimToNull(String str) {
        String ts = trim(str);
        if(null!=ts && ts.equals("null")){
            return null;
        }
        return isEmpty(ts) ? null : ts;
    }
    private static String trim(String str) {
        return str == null ? null : str.trim();
    }
    private static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
    private SmartStringUtils(){}
}
