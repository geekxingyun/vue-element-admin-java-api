package com.xingyun.vueelementadminjavaapi.util;

import java.util.UUID;

/**
 * @author qing-feng.zhao
 */
public class SmartStringUtils {
    private SmartStringUtils(){}
    public static String trimToNull(String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }
    public static String trim(String str) {
        return str == null ? null : str.trim();
    }
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }
    public static String getUuid(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
