package com.xingyun.vueelementadminjavaapi.business.admin.api;

import com.xingyun.vueelementadminjavaapi.business.admin.model.VueElementAdminUserEntity;
import com.xingyun.vueelementadminjavaapi.business.admin.model.VueElementAdminUserLogin;
import com.xingyun.vueelementadminjavaapi.business.admin.model.VueElementAdminUserWebVO;
import com.xingyun.vueelementadminjavaapi.business.admin.model.VueWelcomeInfo;
import com.xingyun.vueelementadminjavaapi.business.admin.service.VueElementAdminUserService;
import com.xingyun.vueelementadminjavaapi.model.VueElementAdminResponse;
import com.xingyun.vueelementadminjavaapi.util.SmartStringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * VueElementAdmin 后台管理系统
 * @author qing-feng.zhao
 */
@Slf4j
@Api(value = "VueElementAdminEndPoint", tags = {"后台管理-用户管理模块API"})
@RequestMapping(value = "/vue-element-admin")
@RestController
public class VueElementAdminEndPoint {

    private final VueElementAdminUserService vueElementAdminUserService;
    private final VueElementAdminResponse vueElementAdminResponse;
    public VueElementAdminEndPoint(VueElementAdminUserService vueElementAdminUserService, VueElementAdminResponse vueElementAdminResponse) {
        this.vueElementAdminUserService = vueElementAdminUserService;
        this.vueElementAdminResponse = vueElementAdminResponse;
    }

    @ApiOperation(value = "登陆接口")
    @PostMapping(value = "/user/login")
    public VueElementAdminResponse loginAdmin(@RequestBody VueElementAdminUserLogin vueElementAdminUserLogin){
        this.vueElementAdminUserService.initVueElementAdmin();
        log.info("登录请求参数:{}",vueElementAdminUserLogin);
        //处理请求参数
        vueElementAdminUserLogin.setUsername(SmartStringUtils.trimToNull(vueElementAdminUserLogin.getUsername()));
        vueElementAdminUserLogin.setPassword(SmartStringUtils.trimToNull(vueElementAdminUserLogin.getPassword()));
        if(StringUtils.isEmpty(vueElementAdminUserLogin.getUsername())){
            this.vueElementAdminResponse.setCode(20001);
            this.vueElementAdminResponse.setMessage("登录账号不可为空");
            this.vueElementAdminResponse.setData(null);
        }
        if(StringUtils.isEmpty(vueElementAdminUserLogin.getPassword())){
            this.vueElementAdminResponse.setCode(20001);
            this.vueElementAdminResponse.setMessage("登录密码不可为空");
            this.vueElementAdminResponse.setData(null);
        }
        Optional<VueElementAdminUserEntity> vueElementAdminUserEntityOptional=this.vueElementAdminUserService.loginVueElementAdmin(vueElementAdminUserLogin);
        if(vueElementAdminUserEntityOptional.isPresent()){
            //生成Token
            String token=SmartStringUtils.getUuid();
            //更新Token
            VueElementAdminUserEntity vueElementAdminUserEntity=vueElementAdminUserEntityOptional.get();
            vueElementAdminUserEntity.setToken(token);
            this.vueElementAdminUserService.saveVueElementAdminUser(vueElementAdminUserEntity);

            //返回结果
            Map<String,Object> resultMap=new HashMap<>(1);
            resultMap.put("token",token);
            log.info("请求成功:{}",resultMap);
            this.vueElementAdminResponse.setMessage("登录成功");
            this.vueElementAdminResponse.setCode(20000);
            this.vueElementAdminResponse.setData(resultMap);
        }else{
            this.vueElementAdminResponse.setCode(60204);
            this.vueElementAdminResponse.setMessage("Account and password are incorrect.");
            this.vueElementAdminResponse.setData(null);
        }
        return this.vueElementAdminResponse;
    }

    @ApiOperation(value = "获取用户信息接口")
    @ApiImplicitParam(name = "X-Token", value = "登陆用户Token",required = true, dataType = "String",paramType="header")
    @GetMapping(value = "/user/info")
    public VueElementAdminResponse getVueElementAdminInfo(@RequestHeader(value = "X-Token")String token) {
        log.info("token={},查询用户信息",token);
        Optional<VueElementAdminUserEntity> vueElementAdminUserEntityOptional=this.vueElementAdminUserService.findVueElementAdminUserByToken(token);
        if(vueElementAdminUserEntityOptional.isPresent()){
            VueElementAdminUserEntity vueElementAdminUserEntity=vueElementAdminUserEntityOptional.get();
            VueElementAdminUserWebVO vueElementAdminUserWebVO=this.vueElementAdminUserService.convertToWebVO(vueElementAdminUserEntity);
            this.vueElementAdminResponse.setCode(20000);
            this.vueElementAdminResponse.setMessage("获取用户信息列表成功");
            this.vueElementAdminResponse.setData(vueElementAdminUserWebVO);
        }else{
            log.info("查找用户信息失败");
            this.vueElementAdminResponse.setCode(50008);
            this.vueElementAdminResponse.setMessage("Login failed, unable to get user details");
            this.vueElementAdminResponse.setData(null);
        }
        return vueElementAdminResponse;
    }

    @ApiOperation(value = "注销登陆接口")
    @PostMapping(value = "/user/logout")
    public VueElementAdminResponse logoutAdmin(){
        this.vueElementAdminResponse.setCode(20000);
        this.vueElementAdminResponse.setMessage("success");
        this.vueElementAdminResponse.setData(null);
        return this.vueElementAdminResponse;
    }

    @ApiOperation(value = "欢迎页数据接口")
    @GetMapping(value = "/transaction/list")
    public VueElementAdminResponse welcomeVue(){
        Map<String,Object>  resultMap=new HashMap<>(2);
        List<VueWelcomeInfo> vueWelcomeInfoList=this.vueElementAdminUserService.initVueWelcomeInfoList();
        resultMap.put("items",vueWelcomeInfoList);
        resultMap.put("total",20);
        this.vueElementAdminResponse.setData(resultMap);
        this.vueElementAdminResponse.setCode(20000);
        return this.vueElementAdminResponse;
    }
}
