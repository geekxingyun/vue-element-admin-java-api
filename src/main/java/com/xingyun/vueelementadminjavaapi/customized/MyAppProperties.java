package com.xingyun.vueelementadminjavaapi.customized;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author qing-feng.zhao
 */
@ConfigurationProperties(prefix="com.xingyun.vue.element.admin")
@Data
public class MyAppProperties {
    private String adminUserName;
    private String adminUserPassword;
    private String editorUserName;
    private String editorUserPassword;
}
