package com.xingyun.vueelementadminjavaapi;

import com.xingyun.vueelementadminjavaapi.framework.service.SystemInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/***
 * JobLauncherApplicationRunner
 * @author qingfeng.zhao
 * @date 2022/7/23
 * @apiNote
 */
@Slf4j
@Component
public class AfterStartupApplicationRunner implements ApplicationRunner {

    private final SystemInfoService systemInfoService;
    public AfterStartupApplicationRunner(SystemInfoService systemInfoService) {
        this.systemInfoService = systemInfoService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // do something at after your application startup
        // 1. print app visit info
        this.systemInfoService.displayAppVisitInfo();
    }
}
