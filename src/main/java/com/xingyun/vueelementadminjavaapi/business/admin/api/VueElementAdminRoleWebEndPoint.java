package com.xingyun.vueelementadminjavaapi.business.admin.api;

import com.xingyun.vueelementadminjavaapi.business.admin.model.param.VueElementAdminUserRoleAddedParam;
import com.xingyun.vueelementadminjavaapi.business.admin.model.param.VueElementAdminUserRoleQueryParam;
import com.xingyun.vueelementadminjavaapi.business.admin.model.param.VueElementAdminUserRoleSaveParam;
import com.xingyun.vueelementadminjavaapi.business.admin.model.param.VueElementAdminUserRoleUpdateStatusParam;
import com.xingyun.vueelementadminjavaapi.business.admin.service.VueElementAdminUserRoleService;
import com.xingyun.vueelementadminjavaapi.framework.model.param.MyPageParam;
import com.xingyun.vueelementadminjavaapi.framework.model.vo.VueElementAdminResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author qing-feng.zhao
 */
@Slf4j
@Api(value = "VueElementAdminRoleWebEndPoint", tags = {"后台管理-用户角色管理模块API"})
@RequestMapping(value = "/vue-element-admin/vue-element-admin-user-role-service")
@RestController
public class VueElementAdminRoleWebEndPoint {

    private final VueElementAdminUserRoleService vueElementAdminUserRoleService;
    public VueElementAdminRoleWebEndPoint(VueElementAdminUserRoleService vueElementAdminUserRoleService) {
        this.vueElementAdminUserRoleService = vueElementAdminUserRoleService;
    }

    @ApiOperation(value = "获取用户角色分页列表",notes = "需要登陆权限的接口")
    @GetMapping(value = "/fetch-vue-element-admin-user-role-page-list.do")
    public VueElementAdminResponse findAllVueElementAdminUserRolePageList(@ModelAttribute VueElementAdminUserRoleQueryParam vueElementAdminUserRoleQueryParam, MyPageParam myPageParam){
        return this.vueElementAdminUserRoleService.findAllVueElementAdminUserRolePageListResponse(vueElementAdminUserRoleQueryParam,myPageParam);
    }

    @ApiOperation(value = "添加用户角色",notes = "需要登陆权限的接口")
    @PostMapping(value = "/added-vue-element-admin-user-role.do")
    public VueElementAdminResponse addedVueElementAdminUserRole(@RequestBody VueElementAdminUserRoleAddedParam vueElementAdminUserRoleAddedParam, @RequestHeader(value = "X-Token")String token){
        return this.vueElementAdminUserRoleService.addedVueElementAdminUserRole(vueElementAdminUserRoleAddedParam,token);
    }

    @ApiOperation(value = "修改用户角色",notes = "需要登陆权限的接口")
    @PostMapping(value = "/saved-vue-element-admin-user-role.do")
    public VueElementAdminResponse savedVueElementAdminUserRole(@RequestBody VueElementAdminUserRoleSaveParam vueElementAdminUserRoleSaveParam, @RequestHeader(value = "X-Token")String token){
        return this.vueElementAdminUserRoleService.savedVueElementAdminUserRoleEntity(vueElementAdminUserRoleSaveParam,token);
    }

    @ApiOperation(value = "删除用户角色",notes = "需要登陆权限的接口")
    @DeleteMapping(value = "/removed-vue-element-admin-user-role.do")
    public VueElementAdminResponse removedVueElementAdminUserRole(String id,@RequestHeader(value = "X-Token")String token){
        return this.vueElementAdminUserRoleService.removedVueElementAdminUserRoleEntity(id,token);
    }

    @ApiOperation(value = "彻底删除用户角色",notes = "需要登陆权限的接口")
    @DeleteMapping(value = "/delete-vue-element-admin-user-role.do")
    public VueElementAdminResponse deleteVueElementAdminUserRole(String id){
        return this.vueElementAdminUserRoleService.deleteVueElementAdminUserRoleEntity(id);
    }

    @ApiOperation(value = "更新用户角色状态",notes = "需要登陆权限的接口")
    @PostMapping(value = "/update-vue-element-admin-user-role-status.do")
    public VueElementAdminResponse updateVueElementAdminUserRoleStatus(@ModelAttribute VueElementAdminUserRoleUpdateStatusParam vueElementAdminUserRoleUpdateStatusParam, @RequestHeader(value = "X-Token")String token){
        return this.vueElementAdminUserRoleService.updateVueElementAdminUserRoleEntityStatus(vueElementAdminUserRoleUpdateStatusParam,token);
    }

    @ApiOperation(value = "查找所有用户角色列表",notes = "需要登陆权限的接口")
    @GetMapping(value = "/fetch-all-vue-element-admin-user-role-list.do")
    public VueElementAdminResponse fetchAllElementAdminUserRoleList(){
        return this.vueElementAdminUserRoleService.fetchAllElementAdminUserRoleList();
    }
}
