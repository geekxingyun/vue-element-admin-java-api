package com.xingyun.vueelementadminjavaapi.business.admin.service;

import com.xingyun.vueelementadminjavaapi.business.admin.model.entity.VueElementAdminUserEntity;
import com.xingyun.vueelementadminjavaapi.business.admin.model.param.*;
import com.xingyun.vueelementadminjavaapi.business.admin.model.vo.VueElementAdminUserWebVO;
import com.xingyun.vueelementadminjavaapi.framework.model.param.MyPageParam;
import com.xingyun.vueelementadminjavaapi.framework.model.vo.VueElementAdminResponse;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;

/**
 * @author qing-feng.zhao
 */
public interface VueElementAdminUserService {
    ///////////////////////////////////服务之间调用///////////////////////////////////////////////////////////////////////
    /**
     * 根据token查询用户信息
     * @param token
     * @return
     */
    Optional<VueElementAdminUserEntity> findVueElementAdminUserByToken(String token);
    /**
     * 转换成用户页面对象
     * @param vueElementAdminUserEntity
     * @return
     */
    VueElementAdminUserWebVO convertToWebVO(VueElementAdminUserEntity vueElementAdminUserEntity);
    /**
     *
     * @param vueElementAdminUserEntityList
     * @return
     */
    List<VueElementAdminUserWebVO> convertToWebVOList(List<VueElementAdminUserEntity> vueElementAdminUserEntityList);
    /**
     * 添加一个系统管理员用户
     * @param vueElementAdminUserAddedParam
     * @param token
     * @return
     */
    VueElementAdminUserEntity addedVueElementAdminUser(VueElementAdminUserAddedParam vueElementAdminUserAddedParam, String token);
    /**
     * 更新用户信息
     * @param vueElementAdminUserSavedParam
     * @param token
     * @return
     */
    VueElementAdminUserEntity saveVueElementAdminUser(VueElementAdminUserSavedParam vueElementAdminUserSavedParam, String token);
    /**
     * 更新启用状态
     * @param adminUserUpdateStatusParam
     * @param token
     * @return
     */
    VueElementAdminUserEntity updateVueElementAdminUserStatus(AdminUserUpdateStatusParam adminUserUpdateStatusParam, String token);
    //////////////////////////////////////////////登陆模块调用////////////////////////////////////////////////////////////////////
    /**
     * 系统登陆适配器
     * @param vueElementAdminUserLoginParam
     * @return
     */
    VueElementAdminResponse loginSystemAdapter(@RequestBody VueElementAdminUserLoginParam vueElementAdminUserLoginParam);
    /**
     * 账号密码方式登陆系统
     * @param vueElementAdminUserLoginParam 登陆请求参数
     * @return
     */
    VueElementAdminResponse loginSystemWithUserNameAndPassword(@RequestBody VueElementAdminUserLoginParam vueElementAdminUserLoginParam);
    /**
     * 根据token获取系统用户信息
     * @param token
     * @return
     */
    VueElementAdminResponse getSystemUserInfo(String token);
    /**
     * 注销登陆
     * @return
     */
    VueElementAdminResponse logoutSystem();
    /**
     * 欢迎登陆信息
     * @return
     */
    VueElementAdminResponse welcomeInfo();
    /**
     * 初始化账号
     * @return
     */
    VueElementAdminResponse initVueElementAdmin();
    ///////////////////////////////////////后台管理系统调用///////////////////////////////////////////////////////////////////////////////////////
    /**
     * 添加一个系统管理员用户
     * @param vueElementAdminUserAddedParam
     * @return
     */
    VueElementAdminResponse addedVueElementAdminUserResponse(VueElementAdminUserAddedParam vueElementAdminUserAddedParam, String token);
    /**
     * 删除用户
     * @param id
     * @return
     */
    VueElementAdminResponse removedVueElementAdminUser(String id,String token);
    /**
     * 彻底删除用户
     * @param id
     * @return
     */
    VueElementAdminResponse deleteVueElementAdminUser(String id);
    /**
     * 更新用户信息
     * @param vueElementAdminUserSavedParam
     * @param token
     * @return
     */
    VueElementAdminResponse saveVueElementAdminUserResponse(VueElementAdminUserSavedParam vueElementAdminUserSavedParam, String token);
    /**
     * 更新启用状态
     * @param adminUserUpdateStatusParam
     * @param token
     * @return
     */
    VueElementAdminResponse updateVueElementAdminUserStatusResponse(AdminUserUpdateStatusParam adminUserUpdateStatusParam,String token);
    /**
     * 查找所有管理员用户
     * @param vueElementAdminUserWebQuery
     * @param myPageParam
     * @return
     */
    VueElementAdminResponse findAllAdminUserPageList(VueElementAdminUserWebQuery vueElementAdminUserWebQuery, MyPageParam myPageParam);
}
