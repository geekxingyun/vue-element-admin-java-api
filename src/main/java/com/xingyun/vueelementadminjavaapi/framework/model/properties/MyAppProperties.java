package com.xingyun.vueelementadminjavaapi.framework.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/***
 * @author qingfeng.zhao
 * @date 2022/4/24
 * @apiNote
 */
@ConfigurationProperties(prefix="com.ning.vue-element-admin")
@Data
public class MyAppProperties {
    private String serviceEnName="default service name";
    private String serviceCnName="默认服务名称";
    private String superAdminUserName="superAdmin";
    private String superAdminUserPassword;
}
