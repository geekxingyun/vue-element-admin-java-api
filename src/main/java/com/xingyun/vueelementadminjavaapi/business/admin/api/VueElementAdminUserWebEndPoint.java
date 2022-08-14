package com.xingyun.vueelementadminjavaapi.business.admin.api;

import com.xingyun.vueelementadminjavaapi.business.admin.model.param.*;
import com.xingyun.vueelementadminjavaapi.business.admin.service.VueElementAdminUserService;
import com.xingyun.vueelementadminjavaapi.framework.model.param.MyPageParam;
import com.xingyun.vueelementadminjavaapi.framework.model.vo.VueElementAdminResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * VueElementAdmin 后台管理系统
 * @author qing-feng.zhao
 */
@Slf4j
@Api(value = "VueElementAdminUserWebEndPoint", tags = {"后台管理-用户管理模块API"})
@RequestMapping(value = "/vue-element-admin")
@RestController
public class VueElementAdminUserWebEndPoint {

    private final VueElementAdminUserService vueElementAdminUserService;
    public VueElementAdminUserWebEndPoint(VueElementAdminUserService vueElementAdminUserService) {
        this.vueElementAdminUserService = vueElementAdminUserService;
    }

    @ApiOperation(value = "系统登陆接口")
    @PostMapping(value = "/user/login")
    public VueElementAdminResponse loginSystemAdapter(@RequestBody VueElementAdminUserLoginParam vueElementAdminUserLoginParam){
        return this.vueElementAdminUserService.loginSystemAdapter(vueElementAdminUserLoginParam);
    }

    @ApiOperation(value = "系统注销登陆接口")
    @PostMapping(value = "/user/logout")
    public VueElementAdminResponse logoutSystem(){
        return this.vueElementAdminUserService.logoutSystem();
    }

    @ApiOperation(value = "根据token获取登陆用户信息接口")
    @GetMapping(value = "/user/info")
    public VueElementAdminResponse getSystemUserInfo(String token) {
        return this.vueElementAdminUserService.getSystemUserInfo(token);
    }
    @ApiOperation(value = "欢迎页数据接口")
    @GetMapping(value = "/transaction/list")
    public VueElementAdminResponse welcomeInfo(@RequestHeader(value = "X-Token")String token){
        return this.vueElementAdminUserService.welcomeInfo();
    }

    ///////////////////////////////////////后台管理系统模块/////////////////////////////////////////////////////////////////////
    /**
     *
     * @param vueElementAdminUserWebQuery
     * @param myPageParam
     * @return
     */
    @ApiOperation(value = "后台管理系统-管理员账号管理-管理员分页列表",notes = "需要登陆权限的接口")
    @GetMapping(value = "/admin-user-service/fetch-admin-user-page-list.do")
    public VueElementAdminResponse findAllAdminUserPageList(VueElementAdminUserWebQuery vueElementAdminUserWebQuery, MyPageParam myPageParam){
        return this.vueElementAdminUserService.findAllAdminUserPageList(vueElementAdminUserWebQuery,myPageParam);
    }

    @ApiOperation(value = "后台管理系统-管理员账号管理-添加一个管理员",notes = "需要登陆权限的接口")
    @PostMapping(value = "/admin-user-service/added-admin-user.do")
    public VueElementAdminResponse addedVueElementAdminUser(@RequestBody VueElementAdminUserAddedParam vueElementAdminUserAddedParam, @RequestHeader(value = "X-Token")String token){
        return this.vueElementAdminUserService.addedVueElementAdminUserResponse(vueElementAdminUserAddedParam,token);
    }

    @ApiOperation(value = "后台管理系统-管理员账号管理-保存一个管理员",notes = "需要登陆权限的接口")
    @PostMapping(value = "/admin-user-service/save-admin-user.do")
    @ApiImplicitParam(name = "X-Token", value = "登陆用户Token",required = true, dataType = "String",paramType="header")
    public VueElementAdminResponse saveVueElementAdminUser(@RequestBody VueElementAdminUserSavedParam vueElementAdminUserSavedParam, @RequestHeader(value = "X-Token")String token){
        return this.vueElementAdminUserService.saveVueElementAdminUserResponse(vueElementAdminUserSavedParam,token);
    }

    @ApiOperation(value = "后台管理系统-管理员账号管理-更新一个管理员账号启用状态",notes = "需要登陆权限的接口")
    @PostMapping(value = "/admin-user-service/update-admin-user-status.do")
    @ApiImplicitParam(name = "X-Token", value = "登陆用户Token",required = true, dataType = "String",paramType="header")
    public VueElementAdminResponse updateVueElementAdminUserStatus(@ModelAttribute AdminUserUpdateStatusParam adminUserUpdateStatusParam, @RequestHeader(value = "X-Token")String token){
        return this.vueElementAdminUserService.updateVueElementAdminUserStatusResponse(adminUserUpdateStatusParam,token);
    }

    @ApiOperation(value = "后台管理系统-管理员账号管理-逻辑删除一个管理员账号",notes = "需要登陆权限的接口")
    @DeleteMapping(value = "/admin-user-service/removed-admin-user.do")
    public VueElementAdminResponse removedVueElementAdminUser(String id,@RequestHeader(value = "X-Token")String token){
        return this.vueElementAdminUserService.removedVueElementAdminUser(id,token);
    }

    @ApiOperation(value = "后台管理系统-管理员账号管理-彻底删除一个管理员账号",notes = "需要登陆权限的接口")
    @DeleteMapping(value = "/admin-user-service/delete-admin-user.do")
    public VueElementAdminResponse deleteVueElementAdminUser(String id){
        return this.vueElementAdminUserService.deleteVueElementAdminUser(id);
    }
}
