package com.xingyun.vueelementadminjavaapi.framework.util;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.ConfigurablePropertyResolver;
import org.springframework.core.env.PropertyResolver;

/**
 * Spring Boot 静态方法中获取application.properties 或application.yml 变量工具类
 * @author qing-feng.zhao
 */
@Configuration
public class SpringBootPropertyUtils extends PropertySourcesPlaceholderConfigurer {
    /**
     * 静态方法中获取Spring Boot 配置
     */
    private static PropertyResolver propertyResolver;
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, ConfigurablePropertyResolver propertyResolver) throws BeansException {
        super.processProperties(beanFactoryToProcess, propertyResolver);
        SpringBootPropertyUtils.propertyResolver=propertyResolver;
    }
    /**
     * 获取静态属性值
     * @param key
     * @return
     */
    public static String getPropertyValue(String key){
        return propertyResolver.getProperty(key);
    }
}
