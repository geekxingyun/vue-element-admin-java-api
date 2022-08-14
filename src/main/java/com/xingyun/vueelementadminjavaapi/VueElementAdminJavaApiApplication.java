package com.xingyun.vueelementadminjavaapi;

import com.xingyun.vueelementadminjavaapi.framework.model.constant.GlobalConstantValues;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VueElementAdminJavaApiApplication {

    public static void main(String[] args) {
        GlobalConstantValues.deployStartTime=System.currentTimeMillis();
        SpringApplication.run(VueElementAdminJavaApiApplication.class, args);
    }

}
