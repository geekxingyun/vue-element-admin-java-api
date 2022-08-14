package com.xingyun.vueelementadminjavaapi.framework.service.impl;

import com.xingyun.vueelementadminjavaapi.framework.model.constant.GlobalConstantValues;
import com.xingyun.vueelementadminjavaapi.framework.model.properties.MyAppProperties;
import com.xingyun.vueelementadminjavaapi.framework.service.SystemInfoService;
import com.xingyun.vueelementadminjavaapi.framework.util.SmartDateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Service;

import java.util.Date;

/***
 * @author qingfeng.zhao
 * @date 2022/7/23
 * @apiNote
 */
@Slf4j
@Service
public class AppVisitWebServiceImpl implements SystemInfoService {

    private final MyAppProperties myAppProperties;
    private final ServerProperties serverProperties;
    public AppVisitWebServiceImpl(MyAppProperties myAppProperties, ServerProperties serverProperties) {
        this.myAppProperties = myAppProperties;
        this.serverProperties = serverProperties;
    }

    @Override
    public void displayAppVisitInfo() {
        displayAppVisitBaseInfo();
        displayBuildDateTimeInfo();
    }

    private void displayAppVisitBaseInfo(){
        StringBuilder localVisitUrlStringBuilder=new StringBuilder();
        localVisitUrlStringBuilder.append("http://localhost");
        localVisitUrlStringBuilder.append(":");
        if(null!=serverProperties.getPort()){
            localVisitUrlStringBuilder.append(serverProperties.getPort());
        }else{
            localVisitUrlStringBuilder.append(8080);
        }
        if(null!=serverProperties.getServlet().getContextPath()){
            localVisitUrlStringBuilder.append(serverProperties.getServlet().getContextPath());
        }else{
            localVisitUrlStringBuilder.append("/");
        }
        log.info("\r\nlocal visit url:{}",localVisitUrlStringBuilder);
    }
    public void displayBuildDateTimeInfo(){
        // 构建日期时间
        Date buildDate=new Date();
        GlobalConstantValues.buildDateTime= SmartDateUtils.convertDateToYYYY_MM_DD_HH_MM_SS_Str(buildDate);
        // 计算部署耗时
        GlobalConstantValues.deployEndTime=System.currentTimeMillis();
        Long deployCountTime=GlobalConstantValues.deployEndTime- GlobalConstantValues.deployStartTime;
        //部署花费时间
        String deployCountTimeDisplay=deployCountTime/1000+"秒";
        log.info("服务英文名称:{},服务中文名称:{},启动耗时:{}",this.myAppProperties.getServiceEnName(),this.myAppProperties.getServiceCnName(),deployCountTimeDisplay);
    }
}
