package com.xingyun.vueelementadminjavaapi.business.admin.service.impl;

import com.xingyun.vueelementadminjavaapi.business.admin.dao.user.VueElementAdminUserDao;
import com.xingyun.vueelementadminjavaapi.business.admin.model.entity.VueElementAdminUserEntity;
import com.xingyun.vueelementadminjavaapi.business.admin.model.entity.VueElementAdminUserRoleEntity;
import com.xingyun.vueelementadminjavaapi.business.admin.model.enums.LoginTypeEnum;
import com.xingyun.vueelementadminjavaapi.business.admin.model.param.*;
import com.xingyun.vueelementadminjavaapi.business.admin.model.vo.VueElementAdminUserLoginVO;
import com.xingyun.vueelementadminjavaapi.business.admin.model.vo.VueElementAdminUserWebVO;
import com.xingyun.vueelementadminjavaapi.business.admin.model.vo.VueWelcomeInfo;
import com.xingyun.vueelementadminjavaapi.business.admin.service.VueElementAdminUserRoleService;
import com.xingyun.vueelementadminjavaapi.business.admin.service.VueElementAdminUserService;
import com.xingyun.vueelementadminjavaapi.framework.model.properties.MyAppProperties;
import com.xingyun.vueelementadminjavaapi.framework.model.constant.GlobalConstantValues;
import com.xingyun.vueelementadminjavaapi.framework.model.enumvalue.ResponseCodeEnum;
import com.xingyun.vueelementadminjavaapi.framework.model.param.MyPageParam;
import com.xingyun.vueelementadminjavaapi.framework.model.vo.BackPageAdapterVO;
import com.xingyun.vueelementadminjavaapi.framework.model.vo.VueElementAdminResponse;
import com.xingyun.vueelementadminjavaapi.framework.util.SmartDateUtils;
import com.xingyun.vueelementadminjavaapi.framework.util.SmartStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author qing-feng.zhao
 */
@Primary
@Qualifier(value = "vueElementAdminUserJpaServiceImpl")
@Slf4j
@Service
public class VueElementAdminUserServiceImpl implements VueElementAdminUserService {
    private final MyAppProperties myAppProperties;
    @Autowired
    private VueElementAdminUserRoleService vueElementAdminUserRoleService;
    private final VueElementAdminUserDao vueElementAdminUserDao;
    private final PasswordEncoder passwordEncoder;


    public VueElementAdminUserServiceImpl(MyAppProperties myAppProperties, VueElementAdminUserDao vueElementAdminUserDao, PasswordEncoder passwordEncoder) {
        this.myAppProperties = myAppProperties;
        this.vueElementAdminUserDao = vueElementAdminUserDao;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public VueElementAdminResponse loginSystemWithUserNameAndPassword(VueElementAdminUserLoginParam vueElementAdminUserLoginParam) {
        VueElementAdminResponse vueElementAdminResponse=new VueElementAdminResponse();
        // 处理请求参数
        vueElementAdminUserLoginParam.setUsername(SmartStringUtils.trimToNull(vueElementAdminUserLoginParam.getUsername()));
        vueElementAdminUserLoginParam.setPassword(SmartStringUtils.trimToNull(vueElementAdminUserLoginParam.getPassword()));
        if(null==vueElementAdminUserLoginParam.getUsername()){
            vueElementAdminResponse.setCode(20001);
            vueElementAdminResponse.setMessage("登录账号不可为空");
            vueElementAdminResponse.setData(null);
        }
        if(null==vueElementAdminUserLoginParam.getPassword()){
            vueElementAdminResponse.setCode(20001);
            vueElementAdminResponse.setMessage("登录密码不可为空");
            vueElementAdminResponse.setData(null);
        }
        Optional<VueElementAdminUserEntity> vueElementAdminUserEntityOptional=this.vueElementAdminUserDao.findByUserName(vueElementAdminUserLoginParam.getUsername());
        if(vueElementAdminUserEntityOptional.isPresent()){
            //更新Token
            VueElementAdminUserEntity vueElementAdminUserEntity=vueElementAdminUserEntityOptional.get();
            String currentLoginPassword=vueElementAdminUserLoginParam.getPassword();
            String currentDbPassword=vueElementAdminUserEntity.getPassword();
            // 明文密码和密文密码匹配
            Boolean checkPasswordFlag= this.passwordEncoder.matches(currentLoginPassword,currentDbPassword);
            if(checkPasswordFlag){
                //生成Token
                String token=SmartStringUtils.getUuid();
                vueElementAdminUserEntity.setToken(token);
                this.vueElementAdminUserDao.save(vueElementAdminUserEntity);
                //返回结果
                Map<String,Object> resultMap=new HashMap<>(1);
                resultMap.put("token",token);
                vueElementAdminResponse.setMessage("登录成功");
                vueElementAdminResponse.setCode(ResponseCodeEnum.OK.getCode());
                vueElementAdminResponse.setData(resultMap);
            }else{
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_ACCOUNT_OR_PASSWORD.getCode());
                vueElementAdminResponse.setMessage(ResponseCodeEnum.INVALID_ACCOUNT_OR_PASSWORD.getMessage());
                vueElementAdminResponse.setData(null);
            }
        }else{
            vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_ACCOUNT_OR_PASSWORD.getCode());
            vueElementAdminResponse.setMessage(ResponseCodeEnum.INVALID_ACCOUNT_OR_PASSWORD.getMessage());
            vueElementAdminResponse.setData(null);
        }
        return vueElementAdminResponse;
    }

    @Override
    public VueElementAdminResponse getSystemUserInfo(String token) {
        VueElementAdminResponse vueElementAdminResponse=new VueElementAdminResponse();
        log.debug("根据token获取登陆用户信息,token={},",token);
        Optional<VueElementAdminUserEntity> vueElementAdminUserEntityOptional=this.vueElementAdminUserDao.findByToken(token);
        if(vueElementAdminUserEntityOptional.isPresent()){
            VueElementAdminUserEntity vueElementAdminUserEntity=vueElementAdminUserEntityOptional.get();
            VueElementAdminUserLoginVO vueElementAdminUserLoginVO=new VueElementAdminUserLoginVO();
            // 返回用户昵称
            vueElementAdminUserLoginVO.setName(vueElementAdminUserEntity.getName());
            // 返回用户头像
            vueElementAdminUserLoginVO.setAvatar(vueElementAdminUserEntity.getAvatar());
            // 返回用户简介
            vueElementAdminUserLoginVO.setIntroduction(vueElementAdminUserEntity.getIntroduction());
            // 获取用户拥有角色名称列表
            List<String> roleNameList=SmartStringUtils.splitStrConvertToList(vueElementAdminUserEntity.getRoles());
            // 用户角色列表
            vueElementAdminUserLoginVO.setRoles(roleNameList);
            vueElementAdminResponse.setCode(ResponseCodeEnum.OK.getCode());
            vueElementAdminResponse.setMessage("获取用户信息列表成功");
            vueElementAdminResponse.setData(vueElementAdminUserLoginVO);
        }else{
            log.warn("无效的登陆token={}",token);
            vueElementAdminResponse.setCode(ResponseCodeEnum.LOGIN_FAIL.getCode());
            vueElementAdminResponse.setMessage(ResponseCodeEnum.LOGIN_FAIL.getMessage());
            vueElementAdminResponse.setData(null);
        }
        return vueElementAdminResponse;
    }

    @Override
    public VueElementAdminResponse logoutSystem() {
        VueElementAdminResponse vueElementAdminResponse=new VueElementAdminResponse();
        vueElementAdminResponse.setCode(ResponseCodeEnum.OK.getCode());
        vueElementAdminResponse.setMessage("注销登录成功");
        vueElementAdminResponse.setData(null);
        return vueElementAdminResponse;
    }

    @Override
    public VueElementAdminResponse welcomeInfo() {
        VueElementAdminResponse vueElementAdminResponse=new VueElementAdminResponse();
        Map<String,Object>  resultMap=new HashMap<>(2);
        List<VueWelcomeInfo> vueWelcomeInfoList=this.initVueWelcomeInfoList();
        resultMap.put("items",vueWelcomeInfoList);
        resultMap.put("total",20);
        vueElementAdminResponse.setData(resultMap);
        vueElementAdminResponse.setCode(20000);
        return vueElementAdminResponse;
    }

    @Override
    public VueElementAdminResponse initVueElementAdmin() {
        VueElementAdminResponse vueElementAdminResponse=new VueElementAdminResponse();
        long currentUserData=this.vueElementAdminUserDao.count();
        if(currentUserData<=0){
            // 创建超级管理员用户角色
            VueElementAdminUserRoleEntity vueElementAdminUserRoleEntity=new VueElementAdminUserRoleEntity();
            vueElementAdminUserRoleEntity.setRoleName("superAdmin");
            vueElementAdminUserRoleEntity.setRoleDisplayName("超级管理员");
            vueElementAdminUserRoleEntity.setDelFlag(false);
            vueElementAdminUserRoleEntity.setStatus(true);
            vueElementAdminUserRoleEntity.setCreateTime(new Date());
            vueElementAdminUserRoleEntity.setUpdateTime(new Date());
            vueElementAdminUserRoleEntity.setRemarks("拥有系统最高权限");
            this.vueElementAdminUserRoleService.addedVueElementAdminUserRoleEntity(vueElementAdminUserRoleEntity);
            // 初始化管理员角色
            VueElementAdminUserRoleEntity adminUserRoleEntity=new VueElementAdminUserRoleEntity();
            adminUserRoleEntity.setRoleName("admin");
            adminUserRoleEntity.setRoleDisplayName("管理员");
            adminUserRoleEntity.setDelFlag(false);
            adminUserRoleEntity.setStatus(true);
            adminUserRoleEntity.setCreateTime(new Date());
            adminUserRoleEntity.setUpdateTime(new Date());
            adminUserRoleEntity.setRemarks("拥有系统管理权限");
            this.vueElementAdminUserRoleService.addedVueElementAdminUserRoleEntity(adminUserRoleEntity);
            // 初始化base-api小组研发权限
            VueElementAdminUserRoleEntity baseApiGroupDeveloperRoleEntity=new VueElementAdminUserRoleEntity();
            baseApiGroupDeveloperRoleEntity.setRoleName("baseApiGroupDeveloper");
            baseApiGroupDeveloperRoleEntity.setRoleDisplayName("base-api小组研发");
            baseApiGroupDeveloperRoleEntity.setDelFlag(false);
            baseApiGroupDeveloperRoleEntity.setStatus(true);
            baseApiGroupDeveloperRoleEntity.setCreateTime(new Date());
            baseApiGroupDeveloperRoleEntity.setUpdateTime(new Date());
            baseApiGroupDeveloperRoleEntity.setRemarks("拥有基础数据维护权限");
            this.vueElementAdminUserRoleService.addedVueElementAdminUserRoleEntity(baseApiGroupDeveloperRoleEntity);
            // 初始化一般用户角色
            VueElementAdminUserRoleEntity normalUserRoleEntity=new VueElementAdminUserRoleEntity();
            normalUserRoleEntity.setRoleName("common_user");
            normalUserRoleEntity.setRoleDisplayName("一般用户");
            normalUserRoleEntity.setDelFlag(false);
            normalUserRoleEntity.setStatus(true);
            normalUserRoleEntity.setCreateTime(new Date());
            normalUserRoleEntity.setUpdateTime(new Date());
            normalUserRoleEntity.setRemarks("拥有一般用户访问权限");
            this.vueElementAdminUserRoleService.addedVueElementAdminUserRoleEntity(normalUserRoleEntity);
            // 创建超级管理员用户
            VueElementAdminUserEntity vueElementAdminUserEntity=new VueElementAdminUserEntity();
            vueElementAdminUserEntity.setUsername(this.myAppProperties.getSuperAdminUserName());
            vueElementAdminUserEntity.setPassword(this.passwordEncoder.encode(this.myAppProperties.getSuperAdminUserPassword()));
            vueElementAdminUserEntity.setRoles("superAdmin");
            vueElementAdminUserEntity.setIntroduction("I am a super administrator");
            vueElementAdminUserEntity.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            vueElementAdminUserEntity.setName("Super Admin");
            vueElementAdminUserEntity.setCreateTime(new Date());
            vueElementAdminUserEntity.setUpdateTime(new Date());
            vueElementAdminUserEntity.setDelFlag(false);
            vueElementAdminUserEntity.setStatus(true);
            this.vueElementAdminUserDao.save(vueElementAdminUserEntity);

            vueElementAdminResponse.setCode(ResponseCodeEnum.OK.getCode());
            vueElementAdminResponse.setMessage("账号初始化完成");
            vueElementAdminResponse.setData(null);
        }else{
            vueElementAdminResponse.setCode(ResponseCodeEnum.OK.getCode());
            vueElementAdminResponse.setMessage("账号已初始化");
            vueElementAdminResponse.setData(null);
        }
        return vueElementAdminResponse;
    }

    private List<VueWelcomeInfo> initVueWelcomeInfoList() {
        List<VueWelcomeInfo> vueWelcomeInfoList=new ArrayList<>(24);
        VueWelcomeInfo vueWelcomeInfo;
        int initDataCount=20;
        for (int i = 0; i < initDataCount; i++) {
            vueWelcomeInfo=new VueWelcomeInfo();
            vueWelcomeInfo.setOrder_no("3BC7bD0E-E351-EcC2-eB73-b57c54cEfA4e");
            vueWelcomeInfo.setPrice(4501.78);
            vueWelcomeInfo.setTimestamp(262597322851L);
            vueWelcomeInfo.setStatus("pending");
            vueWelcomeInfo.setUsername("Sharon Robinson");
            vueWelcomeInfoList.add(vueWelcomeInfo);
        }
        return vueWelcomeInfoList;
    }

    @Override
    public VueElementAdminUserEntity addedVueElementAdminUser(VueElementAdminUserAddedParam vueElementAdminUserAddedParam, String token) {
        VueElementAdminUserEntity vueElementAdminUserEntity=new VueElementAdminUserEntity();
        BeanUtils.copyProperties(vueElementAdminUserAddedParam,vueElementAdminUserEntity);
        //查询操作者Id
        Optional<VueElementAdminUserEntity> currentVueElementAdminUserEntityOptional=this.vueElementAdminUserDao.findByToken(token);
        if(currentVueElementAdminUserEntityOptional.isPresent()){
            VueElementAdminUserEntity currentAdminUser=currentVueElementAdminUserEntityOptional.get();
            vueElementAdminUserEntity.setCreateBy(String.valueOf(currentAdminUser.getId()));
            vueElementAdminUserEntity.setUpdateBy(String.valueOf(currentAdminUser.getId()));
        }
        if(null!=vueElementAdminUserAddedParam.getRoleIds()&&vueElementAdminUserAddedParam.getRoleIds().size()>0){
            StringBuilder roleNameStringBuilder=new StringBuilder();
            StringBuilder roleIdStringBuilder=new StringBuilder();
            for (String roleId:vueElementAdminUserAddedParam.getRoleIds()
                 ) {
                Optional<VueElementAdminUserRoleEntity> adminUserRoleEntityOptional=this.vueElementAdminUserRoleService.findVueElementAdminUserRoleEntityById(roleId);
                if(adminUserRoleEntityOptional.isPresent()){
                    VueElementAdminUserRoleEntity vueElementAdminUserRoleEntity =adminUserRoleEntityOptional.get();
                    roleNameStringBuilder.append(vueElementAdminUserRoleEntity.getRoleName());
                    roleNameStringBuilder.append(",");
                    roleIdStringBuilder.append(roleId);
                    roleIdStringBuilder.append(",");
                }
            }
            vueElementAdminUserEntity.setRoles(roleNameStringBuilder.substring(0,roleNameStringBuilder.toString().length()-1));
            vueElementAdminUserEntity.setRoleIds(roleIdStringBuilder.substring(0,roleIdStringBuilder.toString().length()-1));
        }
        if(null!=SmartStringUtils.trimToNull(vueElementAdminUserAddedParam.getPassword())){
            vueElementAdminUserEntity.setPassword(this.passwordEncoder.encode(vueElementAdminUserAddedParam.getPassword()));
        }
        vueElementAdminUserEntity.setAvatar(GlobalConstantValues.DEFAULT_HEAD_PHOTO_URL);
        vueElementAdminUserEntity.setCreateTime(new Date());
        vueElementAdminUserEntity.setUpdateTime(new Date());
        vueElementAdminUserEntity.setDelFlag(false);
        return this.vueElementAdminUserDao.insert(vueElementAdminUserEntity);
    }

    @Override
    public VueElementAdminResponse addedVueElementAdminUserResponse(VueElementAdminUserAddedParam vueElementAdminUserAddedParam, String token) {
        VueElementAdminResponse vueElementAdminResponse=new VueElementAdminResponse();
        log.debug("添加管理员用户请求参数{}",vueElementAdminUserAddedParam);
        try {
            Boolean existRecordFlag=this.vueElementAdminUserDao.existsAllByUsername(vueElementAdminUserAddedParam.getUsername());
            if(existRecordFlag){
                vueElementAdminResponse.setCode(ResponseCodeEnum.EXIST_ACCOUNT.getCode());
                vueElementAdminResponse.setMessage(ResponseCodeEnum.EXIST_ACCOUNT.getMessage());
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            if(null==vueElementAdminUserAddedParam.getUsername()){
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                vueElementAdminResponse.setMessage("登陆账号不可为空");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            if(null==vueElementAdminUserAddedParam.getPassword()){
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                vueElementAdminResponse.setMessage("登陆密码不可为空");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            if(null==vueElementAdminUserAddedParam.getName()){
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                vueElementAdminResponse.setMessage("用户昵称不可为空");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            if(null==vueElementAdminUserAddedParam.getStatus()){
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                vueElementAdminResponse.setMessage("启用状态不可为空");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            VueElementAdminUserEntity vueElementAdminUserEntity=this.addedVueElementAdminUser(vueElementAdminUserAddedParam,token);
            if(null!=vueElementAdminUserEntity){
                vueElementAdminResponse.setCode(ResponseCodeEnum.OK.getCode());
                vueElementAdminResponse.setMessage("添加用户成功");
                vueElementAdminResponse.setData(null);
            }else{
                log.error("添加管理员用户失败,请求参数:{}",vueElementAdminUserAddedParam);
                vueElementAdminResponse.setCode(ResponseCodeEnum.OPERATOR_FAIL.getCode());
                vueElementAdminResponse.setMessage(ResponseCodeEnum.OPERATOR_FAIL.getMessage());
                vueElementAdminResponse.setData(null);
            }
        } catch (Exception e) {
            log.error("添加用户异常",e);
            vueElementAdminResponse.setCode(50000);
            vueElementAdminResponse.setMessage("添加用户异常");
            vueElementAdminResponse.setData(null);
        }
        return vueElementAdminResponse;
    }

    @Override
    public VueElementAdminResponse removedVueElementAdminUser(String id, String token) {
        return null;
    }

    @Override
    public VueElementAdminResponse deleteVueElementAdminUser(String id) {
        return null;
    }

    @Override
    public VueElementAdminUserEntity saveVueElementAdminUser(VueElementAdminUserSavedParam vueElementAdminUserSavedParam, String token) {
        // 根据Id
        Optional<VueElementAdminUserEntity> vueElementAdminUserEntityOptional=this.vueElementAdminUserDao.findById(Long.valueOf(vueElementAdminUserSavedParam.getId()));
        if(vueElementAdminUserEntityOptional.isPresent()){
            VueElementAdminUserEntity vueElementAdminUserEntity=vueElementAdminUserEntityOptional.get();
            // 原始密码
            String oldPassword=vueElementAdminUserEntity.getPassword();
            // 对象属性复制
            BeanUtils.copyProperties(vueElementAdminUserSavedParam,vueElementAdminUserEntity);
            // 新密码
            String newPassword=SmartStringUtils.trimToNull(vueElementAdminUserSavedParam.getPassword());

            if(null==newPassword){
                vueElementAdminUserEntity.setPassword(oldPassword);
            }else{
                vueElementAdminUserEntity.setPassword(this.passwordEncoder.encode(newPassword));
            }
            Optional<VueElementAdminUserEntity> currentVueElementAdminUserEntityOptional=this.vueElementAdminUserDao.findByToken(token);
            if(currentVueElementAdminUserEntityOptional.isPresent()){
                VueElementAdminUserEntity currentAdminUser=currentVueElementAdminUserEntityOptional.get();
                vueElementAdminUserEntity.setUpdateBy(String.valueOf(currentAdminUser.getId()));
            }
            if(null!=vueElementAdminUserSavedParam.getRoleIds()&&vueElementAdminUserSavedParam.getRoleIds().size()>0){
                StringBuilder roleNameStringBuilder=new StringBuilder();
                StringBuilder roleIdStringBuilder=new StringBuilder();
                for (String roleId:vueElementAdminUserSavedParam.getRoleIds()
                ) {
                    Optional<VueElementAdminUserRoleEntity> adminUserRoleEntityOptional=this.vueElementAdminUserRoleService.findVueElementAdminUserRoleEntityById(roleId);
                    if(adminUserRoleEntityOptional.isPresent()){
                        VueElementAdminUserRoleEntity vueElementAdminUserRoleEntity =adminUserRoleEntityOptional.get();
                        roleNameStringBuilder.append(vueElementAdminUserRoleEntity.getRoleName());
                        roleNameStringBuilder.append(",");
                        roleIdStringBuilder.append(roleId);
                        roleIdStringBuilder.append(",");
                    }
                }
                vueElementAdminUserEntity.setRoles(roleNameStringBuilder.substring(0,roleNameStringBuilder.toString().length()-1));
                vueElementAdminUserEntity.setRoleIds(roleIdStringBuilder.substring(0,roleIdStringBuilder.toString().length()-1));
            }
            vueElementAdminUserEntity.setUpdateTime(new Date());
            return this.vueElementAdminUserDao.save(vueElementAdminUserEntity);
        }
        return null;
    }

    @Override
    public VueElementAdminResponse saveVueElementAdminUserResponse(VueElementAdminUserSavedParam vueElementAdminUserSavedParam, String token) {
        VueElementAdminResponse vueElementAdminResponse=new VueElementAdminResponse();
        log.debug("修改管理员用户分页列表查询参数{}",vueElementAdminUserSavedParam);
        Optional<VueElementAdminUserEntity> vueElementAdminUserEntityOptional=this.vueElementAdminUserDao.findById(Long.valueOf(vueElementAdminUserSavedParam.getId()));
        if(vueElementAdminUserEntityOptional.isPresent()){
            VueElementAdminUserEntity vueElementAdminUserEntity=vueElementAdminUserEntityOptional.get();
            if(!vueElementAdminUserEntity.getUsername().equals(vueElementAdminUserSavedParam.getUsername())){
                Boolean existRecordFlag=this.vueElementAdminUserDao.existsAllByUsername(vueElementAdminUserSavedParam.getUsername());
                if(existRecordFlag){
                    vueElementAdminResponse.setCode(40001);
                    vueElementAdminResponse.setMessage("该账号已存在");
                    vueElementAdminResponse.setData(null);
                    return vueElementAdminResponse;
                }
            }
        }
        VueElementAdminUserEntity vueElementAdminUserEntity=this.saveVueElementAdminUser(vueElementAdminUserSavedParam,token);
        if(null!=vueElementAdminUserEntity){
            vueElementAdminResponse.setCode(20000);
            vueElementAdminResponse.setMessage("系统用户修改成功");
            vueElementAdminResponse.setData(null);
        }else{
            vueElementAdminResponse.setCode(50000);
            vueElementAdminResponse.setMessage("系统用户修改失败");
            vueElementAdminResponse.setData(null);
        }
        return vueElementAdminResponse;
    }

    @Override
    public VueElementAdminUserEntity updateVueElementAdminUserStatus(AdminUserUpdateStatusParam adminUserUpdateStatusParam, String token) {
        Optional<VueElementAdminUserEntity> vueElementAdminUserEntityOptional=this.vueElementAdminUserDao.findById(Long.valueOf(adminUserUpdateStatusParam.getId()));
        if(vueElementAdminUserEntityOptional.isPresent()){
            VueElementAdminUserEntity vueElementAdminUserEntity=vueElementAdminUserEntityOptional.get();
            vueElementAdminUserEntity.setStatus(adminUserUpdateStatusParam.getStatus());
            Optional<VueElementAdminUserEntity> currentVueElementAdminUserEntityOptional=this.vueElementAdminUserDao.findByToken(token);
            if(currentVueElementAdminUserEntityOptional.isPresent()){
                VueElementAdminUserEntity currentAdminUser=currentVueElementAdminUserEntityOptional.get();
                vueElementAdminUserEntity.setUpdateBy(String.valueOf(currentAdminUser.getId()));
            }
            vueElementAdminUserEntity.setUpdateTime(new Date());
            return this.vueElementAdminUserDao.save(vueElementAdminUserEntity);
        }
        return null;
    }

    @Override
    public VueElementAdminResponse loginSystemAdapter(VueElementAdminUserLoginParam vueElementAdminUserLoginParam) {
        log.info("系统登录请求参数:{}", vueElementAdminUserLoginParam);
        if(null==vueElementAdminUserLoginParam.getLoginType()||vueElementAdminUserLoginParam.getLoginType().equals(LoginTypeEnum.PASSWORD.getDisplayName())){
            return loginSystemWithUserNameAndPassword(vueElementAdminUserLoginParam);
        }else{
            VueElementAdminResponse vueElementAdminResponse=new VueElementAdminResponse();
            vueElementAdminResponse.setCode(40001);
            vueElementAdminResponse.setMessage("不支持的登陆类型");
            vueElementAdminResponse.setData(null);
            return vueElementAdminResponse;
        }
    }

    @Override
    public VueElementAdminResponse updateVueElementAdminUserStatusResponse(AdminUserUpdateStatusParam adminUserUpdateStatusParam, String token) {
        VueElementAdminResponse vueElementAdminResponse=new VueElementAdminResponse();
        VueElementAdminUserEntity vueElementAdminUserEntity=this.updateVueElementAdminUserStatus(adminUserUpdateStatusParam,token);
        if(null!=vueElementAdminUserEntity){
            vueElementAdminResponse.setCode(20000);
            vueElementAdminResponse.setMessage("系统用户状态修改成功");
            vueElementAdminResponse.setData(null);
        }else{
            vueElementAdminResponse.setCode(50000);
            vueElementAdminResponse.setMessage("系统用户状态修改失败");
            vueElementAdminResponse.setData(null);
        }
        return vueElementAdminResponse;
    }

    @Override
    public VueElementAdminResponse findAllAdminUserPageList(VueElementAdminUserWebQuery vueElementAdminUserWebQuery, MyPageParam myPageParam) {
        VueElementAdminResponse vueElementAdminResponse=new VueElementAdminResponse();
        try {
            log.debug("获取管理员用户分页列表查询参数{}",vueElementAdminUserWebQuery);
            log.debug("获取管理员用户分页列表分页参数{}",myPageParam);
            BackPageAdapterVO<VueElementAdminUserEntity> vueElementAdminUserEntityPage=this.vueElementAdminUserDao.findAllVueElementAdminUserPageList(vueElementAdminUserWebQuery,myPageParam);
            List<VueElementAdminUserEntity> vueElementAdminUserEntityList=vueElementAdminUserEntityPage.getContent();
            List<VueElementAdminUserWebVO> vueElementAdminUserWebVOList=this.convertToWebVOList(vueElementAdminUserEntityList);
            long total=vueElementAdminUserEntityPage.getTotalElements();
            Map<String,Object> resultMap=new HashMap<>(4);
            resultMap.put("items",vueElementAdminUserWebVOList);
            resultMap.put("total",total);
            vueElementAdminResponse.setCode(ResponseCodeEnum.OK.getCode());
            vueElementAdminResponse.setMessage("获取系统用户分页列表成功");
            vueElementAdminResponse.setData(resultMap);
        } catch (Exception e) {
            log.error("获取系统用户分页列表异常,请求参数={},分页参数:{},异常信息:",vueElementAdminUserWebQuery,myPageParam,e);
            vueElementAdminResponse.setCode(ResponseCodeEnum.OTHER_SERVER_EXCEPTION.getCode());
            vueElementAdminResponse.setMessage(ResponseCodeEnum.OTHER_SERVER_EXCEPTION.getMessage());
            vueElementAdminResponse.setData(null);
        }
        return vueElementAdminResponse;
    }

    @Override
    public Optional<VueElementAdminUserEntity> findVueElementAdminUserByToken(String token) {
        return this.vueElementAdminUserDao.findByToken(token);
    }

    @Override
    public VueElementAdminUserWebVO convertToWebVO(VueElementAdminUserEntity vueElementAdminUserEntity) {
        VueElementAdminUserWebVO vueElementAdminUserWebVO=new VueElementAdminUserWebVO();
        BeanUtils.copyProperties(vueElementAdminUserEntity,vueElementAdminUserWebVO);
        if(null!=vueElementAdminUserEntity.getId()){
            vueElementAdminUserWebVO.setId(String.valueOf(vueElementAdminUserEntity.getId()));
        }
        if(null!=vueElementAdminUserEntity.getCreateBy()){
            Optional<VueElementAdminUserEntity> vueElementAdminUserEntityOptional=this.vueElementAdminUserDao.findById(Long.valueOf(vueElementAdminUserEntity.getCreateBy()));
            if(vueElementAdminUserEntityOptional.isPresent()){
                VueElementAdminUserEntity createUserInfo=vueElementAdminUserEntityOptional.get();
                vueElementAdminUserWebVO.setCreateBy(createUserInfo.getName());
            }
        }
        if(null!=vueElementAdminUserEntity.getUpdateBy()){
            Optional<VueElementAdminUserEntity> vueElementAdminUserEntityOptional=this.vueElementAdminUserDao.findById(Long.valueOf(vueElementAdminUserEntity.getUpdateBy()));
            if(vueElementAdminUserEntityOptional.isPresent()){
                VueElementAdminUserEntity updateUserInfo=vueElementAdminUserEntityOptional.get();
                vueElementAdminUserWebVO.setUpdateBy(updateUserInfo.getName());
            }
        }
        if(null!=vueElementAdminUserEntity.getCreateTime()){
            vueElementAdminUserWebVO.setCreateDate(SmartDateUtils.convertDateToYYYY_MM_DD_HH_MM_SS_Str(vueElementAdminUserEntity.getCreateTime()));
        }
        if(null!=vueElementAdminUserEntity.getUpdateTime()){
            vueElementAdminUserWebVO.setUpdateDate(SmartDateUtils.convertDateToYYYY_MM_DD_HH_MM_SS_Str(vueElementAdminUserEntity.getUpdateTime()));
        }
        if(null!=vueElementAdminUserEntity.getRoleIds()){
            List<String> roleIds=SmartStringUtils.splitStrConvertToList(vueElementAdminUserEntity.getRoleIds());
            vueElementAdminUserWebVO.setRoleIds(roleIds);
        }
        return vueElementAdminUserWebVO;
    }

    @Override
    public List<VueElementAdminUserWebVO> convertToWebVOList(List<VueElementAdminUserEntity> vueElementAdminUserEntityList) {
        if(null==vueElementAdminUserEntityList||vueElementAdminUserEntityList.size()<=0){
            return Collections.EMPTY_LIST;
        }
        List<VueElementAdminUserWebVO> vueElementAdminUserWebVOList=new ArrayList<>(vueElementAdminUserEntityList.size());
        VueElementAdminUserWebVO vueElementAdminUserWebVO;
        Integer numberId=1;
        for (VueElementAdminUserEntity vueElementAdminUserEntity:vueElementAdminUserEntityList
        ) {
            vueElementAdminUserWebVO=this.convertToWebVO(vueElementAdminUserEntity);
            vueElementAdminUserWebVO.setNumberId(numberId);
            vueElementAdminUserWebVOList.add(vueElementAdminUserWebVO);
            numberId++;
        }
        return vueElementAdminUserWebVOList;
    }
}
