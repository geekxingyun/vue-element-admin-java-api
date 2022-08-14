package com.xingyun.vueelementadminjavaapi.framework.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * 引入外部的XML
 * @author qing-feng.zhao
 */
@ImportResource(locations = {
        "classpath:spring-config/spring-white-website.xml",
})
@Configuration
public class SpringXmlConfig {
}
