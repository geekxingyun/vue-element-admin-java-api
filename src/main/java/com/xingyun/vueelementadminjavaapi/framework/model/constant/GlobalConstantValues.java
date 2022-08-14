package com.xingyun.vueelementadminjavaapi.framework.model.constant;

/***
 * @author qingfeng.zhao
 * @date 2022/4/24
 * @apiNote
 */
public class GlobalConstantValues {
    public static final String SNOW_FlAKE_ID_GENERATOR="com.xingyun.vueelementadminjavaapi.framework.customized.SnowFlakeIdGenerator";
    /**
     * 部署开始时间
     */
    public volatile static Long deployStartTime;
    /**
     * 部署结束时间
     */
    public volatile static Long deployEndTime;
    /**
     * 构建时间
     */
    public volatile static String buildDateTime;
    /**
     * 默认头像
     */
    public static final String DEFAULT_HEAD_PHOTO_URL="https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif";
}
