package com.xingyun.vueelementadminjavaapi.framework.model.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;

/***
 * @author qingfeng.zhao
 * @date 2022/7/23
 * @apiNote
 */
@ConfigurationProperties(prefix="com.ning.my-security-account.config")
@Data
public class MySpringSecurityAccountProperties implements Serializable {
    private String swaggerAccount="default-swagger-account";
    private String swaggerPassword;
    private String h2Account="default-h2-account";
    private String h2Password;
}
