package com.xingyun.vueelementadminjavaapi.business.admin.service.impl;

import com.xingyun.vueelementadminjavaapi.business.admin.dao.role.VueElementAdminUserRoleDao;
import com.xingyun.vueelementadminjavaapi.business.admin.dao.user.VueElementAdminUserDao;
import com.xingyun.vueelementadminjavaapi.business.admin.model.entity.VueElementAdminUserEntity;
import com.xingyun.vueelementadminjavaapi.business.admin.model.entity.VueElementAdminUserRoleEntity;
import com.xingyun.vueelementadminjavaapi.business.admin.model.param.VueElementAdminUserRoleAddedParam;
import com.xingyun.vueelementadminjavaapi.business.admin.model.param.VueElementAdminUserRoleQueryParam;
import com.xingyun.vueelementadminjavaapi.business.admin.model.param.VueElementAdminUserRoleSaveParam;
import com.xingyun.vueelementadminjavaapi.business.admin.model.param.VueElementAdminUserRoleUpdateStatusParam;
import com.xingyun.vueelementadminjavaapi.business.admin.model.vo.VueElementAdminUserRoleWebVO;
import com.xingyun.vueelementadminjavaapi.business.admin.service.VueElementAdminUserRoleService;
import com.xingyun.vueelementadminjavaapi.framework.model.enumvalue.ResponseCodeEnum;
import com.xingyun.vueelementadminjavaapi.framework.model.param.MyPageParam;
import com.xingyun.vueelementadminjavaapi.framework.model.vo.BackPageAdapterVO;
import com.xingyun.vueelementadminjavaapi.framework.model.vo.VueElementAdminResponse;
import com.xingyun.vueelementadminjavaapi.framework.util.SmartDateUtils;
import com.xingyun.vueelementadminjavaapi.framework.util.SmartStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author qing-feng.zhao
 */
@Slf4j
@Service
public class VueElementAdminUserRoleServiceImpl implements VueElementAdminUserRoleService {
    private final VueElementAdminUserRoleDao vueElementAdminUserRoleDao;
    @Autowired
    private VueElementAdminUserDao vueElementAdminUserDao;

    public VueElementAdminUserRoleServiceImpl(VueElementAdminUserRoleDao vueElementAdminUserRoleDao) {
        this.vueElementAdminUserRoleDao = vueElementAdminUserRoleDao;
    }


    @Override
    public BackPageAdapterVO<VueElementAdminUserRoleEntity> findAllVueElementAdminUserRoleEntityPageList(VueElementAdminUserRoleQueryParam vueElementAdminUserRoleQueryParam, MyPageParam myPageParam) {
        return this.vueElementAdminUserRoleDao.findAll(vueElementAdminUserRoleQueryParam,myPageParam);
    }

    @Override
    public VueElementAdminResponse findAllVueElementAdminUserRolePageListResponse(VueElementAdminUserRoleQueryParam vueElementAdminUserRoleQueryParam, MyPageParam myPageParam) {
        VueElementAdminResponse vueElementAdminResponse=new VueElementAdminResponse();
        try {
            BackPageAdapterVO<VueElementAdminUserRoleEntity> vueElementAdminUserRoleEntityPage=this.findAllVueElementAdminUserRoleEntityPageList(vueElementAdminUserRoleQueryParam,myPageParam);
            List<VueElementAdminUserRoleEntity> vueElementAdminUserRoleEntityList=vueElementAdminUserRoleEntityPage.getContent();
            List<VueElementAdminUserRoleWebVO> vueElementAdminUserRoleWebVOList=this.convertToWebVOList(vueElementAdminUserRoleEntityList);
            Long total=vueElementAdminUserRoleEntityPage.getTotalElements();
            Map<String,Object> resultMap = new HashMap<>(2);
            resultMap.put("items",vueElementAdminUserRoleWebVOList);
            resultMap.put("total",total);
            vueElementAdminResponse.setCode(ResponseCodeEnum.OK.getCode());
            vueElementAdminResponse.setMessage("获取角色列表成功");
            vueElementAdminResponse.setData(resultMap);
        } catch (Exception e) {
            log.error("获取角色列表出错",e);
            vueElementAdminResponse.setCode(ResponseCodeEnum.OTHER_SERVER_EXCEPTION.getCode());
            vueElementAdminResponse.setMessage("获取角色列表出错");
            vueElementAdminResponse.setData(null);
        }
        return vueElementAdminResponse;
    }

    @Override
    public VueElementAdminResponse addedVueElementAdminUserRole(VueElementAdminUserRoleAddedParam vueElementAdminUserRoleAddedParam, String token) {
        VueElementAdminResponse vueElementAdminResponse=new VueElementAdminResponse();
        try {
            vueElementAdminUserRoleAddedParam.setRoleName(SmartStringUtils.trimToNull(vueElementAdminUserRoleAddedParam.getRoleName()));
            vueElementAdminUserRoleAddedParam.setRoleDisplayName(SmartStringUtils.trimToNull(vueElementAdminUserRoleAddedParam.getRoleDisplayName()));
            vueElementAdminUserRoleAddedParam.setRemarks(SmartStringUtils.trimToNull(vueElementAdminUserRoleAddedParam.getRemarks()));
            if(null==vueElementAdminUserRoleAddedParam.getRoleName()){
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                vueElementAdminResponse.setMessage("角色名称不可为空");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            if(null==vueElementAdminUserRoleAddedParam.getRoleDisplayName()){
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                vueElementAdminResponse.setMessage("角色显示名称不可为空");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            if(null==vueElementAdminUserRoleAddedParam.getRemarks()){
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                vueElementAdminResponse.setMessage("角色使用说明不可为空");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            if(null==vueElementAdminUserRoleAddedParam.getStatus()){
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                vueElementAdminResponse.setMessage("角色是否启用不可为空");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            Boolean existRoleName=this.vueElementAdminUserRoleDao.existsByRoleNameAndDelFlag(vueElementAdminUserRoleAddedParam.getRoleName(),false);
            if(existRoleName){
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                vueElementAdminResponse.setMessage("该角色名称已存在");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            VueElementAdminUserRoleEntity vueElementAdminUserRoleEntity=new VueElementAdminUserRoleEntity();
            BeanUtils.copyProperties(vueElementAdminUserRoleAddedParam,vueElementAdminUserRoleEntity);
            Optional<VueElementAdminUserEntity> vueElementAdminUserEntityOptional=this.vueElementAdminUserDao.findByToken(token);
            if(vueElementAdminUserEntityOptional.isPresent()){
                VueElementAdminUserEntity vueElementAdminUserEntity=vueElementAdminUserEntityOptional.get();
                vueElementAdminUserRoleEntity.setCreateBy(String.valueOf(vueElementAdminUserEntity.getId()));
                vueElementAdminUserRoleEntity.setUpdateBy(String.valueOf(vueElementAdminUserEntity.getId()));
            }else{
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_LOGIN_TOKEN.getCode());
                vueElementAdminResponse.setMessage("登陆失效请重新登陆");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            vueElementAdminUserRoleEntity.setCreateTime(new Date());
            vueElementAdminUserRoleEntity.setUpdateTime(new Date());
            vueElementAdminUserRoleEntity.setDelFlag(false);
            VueElementAdminUserRoleEntity savedVueElementAdminUserRoleEntity=this.vueElementAdminUserRoleDao.save(vueElementAdminUserRoleEntity);
            if(null==savedVueElementAdminUserRoleEntity){
                vueElementAdminResponse.setCode(ResponseCodeEnum.OPERATOR_FAIL.getCode());
                vueElementAdminResponse.setMessage("角色添加失败");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            vueElementAdminResponse.setCode(ResponseCodeEnum.OK.getCode());
            vueElementAdminResponse.setMessage("角色添加成功");
            vueElementAdminResponse.setData(null);
        } catch (Exception e) {
            log.error("角色添加出错",e);
            vueElementAdminResponse.setCode(ResponseCodeEnum.OTHER_SERVER_EXCEPTION.getCode());
            vueElementAdminResponse.setMessage("角色添加出错");
            vueElementAdminResponse.setData(null);
        }
        return vueElementAdminResponse;
    }

    @Override
    public VueElementAdminUserRoleEntity addedVueElementAdminUserRoleEntity(VueElementAdminUserRoleEntity vueElementAdminUserRoleEntity) {
        return this.vueElementAdminUserRoleDao.save(vueElementAdminUserRoleEntity);
    }

    @Override
    public Long countVueElementAdminUserRole() {
        return this.vueElementAdminUserRoleDao.count();
    }

    @Override
    public Optional<VueElementAdminUserRoleEntity> findVueElementAdminUserRoleEntityById(String id) {
        return this.vueElementAdminUserRoleDao.findByIdAndStatus(Long.valueOf(id),true);
    }

    @Override
    public Optional<VueElementAdminUserRoleEntity> findVueElementAdminUserRoleEntityByRoleName(String roleName) {
        return this.vueElementAdminUserRoleDao.findByRoleNameAndStatusAndDelFlag(roleName,true,false);
    }

    @Override
    public List<VueElementAdminUserRoleWebVO> convertToWebVOList(List<VueElementAdminUserRoleEntity > vueElementAdminUserRoleEntityList) {
        if(null==vueElementAdminUserRoleEntityList||vueElementAdminUserRoleEntityList.size()<=0){
            return Collections.EMPTY_LIST;
        }
        List<VueElementAdminUserRoleWebVO> vueElementAdminUserRoleWebVOList=new ArrayList<>(vueElementAdminUserRoleEntityList.size());
        VueElementAdminUserRoleWebVO vueElementAdminUserRoleWebVO;
        Integer numberId=1;
        for (VueElementAdminUserRoleEntity vueElementAdminUserRoleEntity:vueElementAdminUserRoleEntityList
             ) {
            vueElementAdminUserRoleWebVO=this.convertToWebVO(vueElementAdminUserRoleEntity);
            vueElementAdminUserRoleWebVO.setNumberId(numberId);
            vueElementAdminUserRoleWebVOList.add(vueElementAdminUserRoleWebVO);
            numberId++;
        }
        return vueElementAdminUserRoleWebVOList;
    }

    @Override
    public VueElementAdminUserRoleWebVO convertToWebVO(VueElementAdminUserRoleEntity vueElementAdminUserRoleEntity) {
        VueElementAdminUserRoleWebVO vueElementAdminUserRoleWebVO=new VueElementAdminUserRoleWebVO();
        BeanUtils.copyProperties(vueElementAdminUserRoleEntity,vueElementAdminUserRoleWebVO);
        vueElementAdminUserRoleWebVO.setId(String.valueOf(vueElementAdminUserRoleEntity.getId()));
        if(null!=vueElementAdminUserRoleEntity.getCreateTime()){
            vueElementAdminUserRoleWebVO.setCreateDate(SmartDateUtils.convertDateToYYYY_MM_DD_HH_MM_SS_Str(vueElementAdminUserRoleEntity.getCreateTime()));
        }
        if(null!=vueElementAdminUserRoleEntity.getUpdateTime()){
            vueElementAdminUserRoleWebVO.setUpdateDate(SmartDateUtils.convertDateToYYYY_MM_DD_HH_MM_SS_Str(vueElementAdminUserRoleEntity.getUpdateTime()));
        }
        if(null!=vueElementAdminUserRoleEntity.getCreateBy()){
            Optional<VueElementAdminUserEntity> vueElementAdminUserEntityOptional=this.vueElementAdminUserDao.findById(Long.valueOf(vueElementAdminUserRoleEntity.getCreateBy()));
            if(vueElementAdminUserEntityOptional.isPresent()){
                VueElementAdminUserEntity vueElementAdminUserEntity=vueElementAdminUserEntityOptional.get();
                if(null!=vueElementAdminUserEntity.getName()){
                    vueElementAdminUserRoleWebVO.setCreateBy(vueElementAdminUserEntity.getName());
                }
            }
        }
        if(null!=vueElementAdminUserRoleEntity.getStatus()){
            vueElementAdminUserRoleWebVO.setStatus(vueElementAdminUserRoleEntity.getStatus());
        }
        if(null!=vueElementAdminUserRoleEntity.getUpdateBy()){
            Optional<VueElementAdminUserEntity> vueElementAdminUserEntityOptional=this.vueElementAdminUserDao.findById(Long.valueOf(vueElementAdminUserRoleEntity.getUpdateBy()));
            if(vueElementAdminUserEntityOptional.isPresent()){
                VueElementAdminUserEntity vueElementAdminUserEntity=vueElementAdminUserEntityOptional.get();
                if(null!=vueElementAdminUserEntity.getName()){
                    vueElementAdminUserRoleWebVO.setUpdateBy(vueElementAdminUserEntity.getName());
                }
            }
        }
        return vueElementAdminUserRoleWebVO;
    }

    @Override
    public VueElementAdminResponse savedVueElementAdminUserRoleEntity(VueElementAdminUserRoleSaveParam vueElementAdminUserRoleSaveParam, String token) {
        VueElementAdminResponse vueElementAdminResponse=new VueElementAdminResponse();
        try {
            vueElementAdminUserRoleSaveParam.setRoleName(SmartStringUtils.trimToNull(vueElementAdminUserRoleSaveParam.getRoleName()));
            vueElementAdminUserRoleSaveParam.setRoleDisplayName(SmartStringUtils.trimToNull(vueElementAdminUserRoleSaveParam.getRoleDisplayName()));
            vueElementAdminUserRoleSaveParam.setRemarks(SmartStringUtils.trimToNull(vueElementAdminUserRoleSaveParam.getRemarks()));
            if(null==vueElementAdminUserRoleSaveParam.getRoleName()){
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                vueElementAdminResponse.setMessage("角色名称不可为空");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            if(null==vueElementAdminUserRoleSaveParam.getRoleDisplayName()){
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                vueElementAdminResponse.setMessage("角色显示名称不可为空");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            if(null==vueElementAdminUserRoleSaveParam.getRemarks()){
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                vueElementAdminResponse.setMessage("角色使用说明不可为空");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            if(null==vueElementAdminUserRoleSaveParam.getStatus()){
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                vueElementAdminResponse.setMessage("角色是否启用不可为空");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            vueElementAdminUserRoleSaveParam.setId(SmartStringUtils.trimToNull(vueElementAdminUserRoleSaveParam.getId()));
            if(null==vueElementAdminUserRoleSaveParam.getId()){
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                vueElementAdminResponse.setMessage("角色Id不可为空");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            Optional<VueElementAdminUserRoleEntity> vueElementAdminUserRoleEntityOptional=this.vueElementAdminUserRoleDao.findById(Long.valueOf(vueElementAdminUserRoleSaveParam.getId()));
            if(vueElementAdminUserRoleEntityOptional.isPresent()){
                VueElementAdminUserRoleEntity vueElementAdminUserRoleEntity=vueElementAdminUserRoleEntityOptional.get();
                if(!vueElementAdminUserRoleEntity.getRoleName().equals(vueElementAdminUserRoleSaveParam.getRoleName())){
                    Boolean existRoleName=this.vueElementAdminUserRoleDao.existsByRoleNameAndDelFlag(vueElementAdminUserRoleSaveParam.getRoleName(),false);
                    if(existRoleName){
                        vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                        vueElementAdminResponse.setMessage("该角色名称已存在");
                        vueElementAdminResponse.setData(null);
                        return vueElementAdminResponse;
                    }
                }
                BeanUtils.copyProperties(vueElementAdminUserRoleSaveParam,vueElementAdminUserRoleEntity);
                Optional<VueElementAdminUserEntity> vueElementAdminUserEntityOptional=this.vueElementAdminUserDao.findByToken(token);
                if(vueElementAdminUserEntityOptional.isPresent()){
                    VueElementAdminUserEntity vueElementAdminUserEntity=vueElementAdminUserEntityOptional.get();
                    vueElementAdminUserRoleEntity.setUpdateBy(String.valueOf(vueElementAdminUserEntity.getId()));
                }else{
                    vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_LOGIN_TOKEN.getCode());
                    vueElementAdminResponse.setMessage("登陆失效请重新登陆");
                    vueElementAdminResponse.setData(null);
                    return vueElementAdminResponse;
                }
                vueElementAdminUserRoleEntity.setUpdateTime(new Date());
                VueElementAdminUserRoleEntity savedVueElementAdminUserRoleEntity=this.vueElementAdminUserRoleDao.save(vueElementAdminUserRoleEntity);
                if(null==savedVueElementAdminUserRoleEntity){
                    vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                    vueElementAdminResponse.setMessage("角色修改失败");
                    vueElementAdminResponse.setData(null);
                    return vueElementAdminResponse;
                }
                vueElementAdminResponse.setCode(ResponseCodeEnum.OK.getCode());
                vueElementAdminResponse.setMessage("角色名称修改成功");
                vueElementAdminResponse.setData(null);
            }else{
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                vueElementAdminResponse.setMessage("无效的角色Id");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
        } catch (Exception e) {
            log.error("角色名称修改出错",e);
            vueElementAdminResponse.setCode(ResponseCodeEnum.OTHER_SERVER_EXCEPTION.getCode());
            vueElementAdminResponse.setMessage("角色名称修改出错");
            vueElementAdminResponse.setData(null);
        }
        return vueElementAdminResponse;
    }

    @Override
    public VueElementAdminResponse removedVueElementAdminUserRoleEntity(String id, String token) {
        VueElementAdminResponse vueElementAdminResponse=new VueElementAdminResponse();
        try {
            id=SmartStringUtils.trimToNull(id);
            if(null==id){
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                vueElementAdminResponse.setMessage("角色Id不可为空");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            Optional<VueElementAdminUserRoleEntity> vueElementAdminUserRoleEntityOptional=this.vueElementAdminUserRoleDao.findById(Long.valueOf(id));
            if(vueElementAdminUserRoleEntityOptional.isPresent()){
                VueElementAdminUserRoleEntity vueElementAdminUserRoleEntity=vueElementAdminUserRoleEntityOptional.get();
                Optional<VueElementAdminUserEntity> vueElementAdminUserEntityOptional=this.vueElementAdminUserDao.findByToken(token);
                if(vueElementAdminUserEntityOptional.isPresent()){
                    VueElementAdminUserEntity vueElementAdminUserEntity=vueElementAdminUserEntityOptional.get();
                    vueElementAdminUserRoleEntity.setUpdateBy(String.valueOf(vueElementAdminUserEntity.getId()));
                }
                vueElementAdminUserRoleEntity.setUpdateTime(new Date());
                vueElementAdminUserRoleEntity.setDelFlag(true);
                VueElementAdminUserRoleEntity savedVueElementAdminUserRoleEntity=this.vueElementAdminUserRoleDao.save(vueElementAdminUserRoleEntity);
                if(null==savedVueElementAdminUserRoleEntity){
                    vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                    vueElementAdminResponse.setMessage("角色删除失败");
                    vueElementAdminResponse.setData(null);
                    return vueElementAdminResponse;
                }
                vueElementAdminResponse.setCode(20000);
                vueElementAdminResponse.setMessage("角色删除成功");
                vueElementAdminResponse.setData(null);
            }else{
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                vueElementAdminResponse.setMessage("无效的角色Id");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
        } catch (NumberFormatException e) {
            log.error("系统用户角色删除异常",e);
            vueElementAdminResponse.setCode(ResponseCodeEnum.OTHER_SERVER_EXCEPTION.getCode());
            vueElementAdminResponse.setMessage("系统用户角色删除异常");
            vueElementAdminResponse.setData(null);
        }
        return vueElementAdminResponse;
    }

    @Override
    public VueElementAdminResponse deleteVueElementAdminUserRoleEntity(String id) {
        VueElementAdminResponse vueElementAdminResponse=new VueElementAdminResponse();
        try {
            this.vueElementAdminUserRoleDao.deleteById(Long.valueOf(id));
            vueElementAdminResponse.setCode(ResponseCodeEnum.OK.getCode());
            vueElementAdminResponse.setMessage("角色彻底删除成功");
            vueElementAdminResponse.setData(null);
        } catch (NumberFormatException e) {
            log.error("角色彻底删除异常",e);
            vueElementAdminResponse.setCode(ResponseCodeEnum.OTHER_SERVER_EXCEPTION.getCode());
            vueElementAdminResponse.setMessage("角色彻底删除异常");
            vueElementAdminResponse.setData(null);
        }
        return vueElementAdminResponse;
    }

    @Override
    public VueElementAdminResponse updateVueElementAdminUserRoleEntityStatus(VueElementAdminUserRoleUpdateStatusParam vueElementAdminUserRoleUpdateStatusParam, String token) {
        VueElementAdminResponse vueElementAdminResponse=new VueElementAdminResponse();
        try {
            log.debug("更新用户状态请求参数:{}",vueElementAdminUserRoleUpdateStatusParam);
            vueElementAdminUserRoleUpdateStatusParam.setId(SmartStringUtils.trimToNull(vueElementAdminUserRoleUpdateStatusParam.getId()));
            if(null==vueElementAdminUserRoleUpdateStatusParam.getId()){
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                vueElementAdminResponse.setMessage("角色Id不可为空");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            if(null==vueElementAdminUserRoleUpdateStatusParam.getStatus()){
                vueElementAdminResponse.setCode(ResponseCodeEnum.INVALID_PARAM.getCode());
                vueElementAdminResponse.setMessage("角色状态不可为空");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
            Optional<VueElementAdminUserRoleEntity> vueElementAdminUserRoleEntityOptional=this.vueElementAdminUserRoleDao.findById(Long.valueOf(vueElementAdminUserRoleUpdateStatusParam.getId()));
            if(vueElementAdminUserRoleEntityOptional.isPresent()){
                VueElementAdminUserRoleEntity vueElementAdminUserRoleEntity=vueElementAdminUserRoleEntityOptional.get();
                Optional<VueElementAdminUserEntity> vueElementAdminUserEntityOptional=this.vueElementAdminUserDao.findByToken(token);
                if(vueElementAdminUserEntityOptional.isPresent()){
                    VueElementAdminUserEntity vueElementAdminUserEntity=vueElementAdminUserEntityOptional.get();
                    vueElementAdminUserRoleEntity.setUpdateBy(String.valueOf(vueElementAdminUserEntity.getId()));
                }
                vueElementAdminUserRoleEntity.setUpdateTime(new Date());
                vueElementAdminUserRoleEntity.setStatus(vueElementAdminUserRoleUpdateStatusParam.getStatus());
                VueElementAdminUserRoleEntity savedVueElementAdminUserRoleEntity=this.vueElementAdminUserRoleDao.save(vueElementAdminUserRoleEntity);
                if(null==savedVueElementAdminUserRoleEntity){
                    vueElementAdminResponse.setCode(ResponseCodeEnum.OPERATOR_FAIL.getCode());
                    vueElementAdminResponse.setMessage("角色状态修改失败");
                    vueElementAdminResponse.setData(null);
                    return vueElementAdminResponse;
                }
                vueElementAdminResponse.setCode(ResponseCodeEnum.OK.getCode());
                vueElementAdminResponse.setMessage("角色状态修改成功");
                vueElementAdminResponse.setData(null);
            }else{
                vueElementAdminResponse.setCode(40001);
                vueElementAdminResponse.setMessage("无效的角色Id");
                vueElementAdminResponse.setData(null);
                return vueElementAdminResponse;
            }
        } catch (NumberFormatException e) {
            log.error("修改用户角色启用状态异常",e);
            vueElementAdminResponse.setCode(ResponseCodeEnum.OTHER_SERVER_EXCEPTION.getCode());
            vueElementAdminResponse.setMessage(ResponseCodeEnum.OTHER_SERVER_EXCEPTION.getMessage());
            vueElementAdminResponse.setData(null);
            return vueElementAdminResponse;
        }
        return vueElementAdminResponse;
    }

    @Override
    public VueElementAdminResponse fetchAllElementAdminUserRoleList() {
        VueElementAdminResponse vueElementAdminResponse=new VueElementAdminResponse();
        try {
            List<VueElementAdminUserRoleEntity> vueElementAdminUserRoleEntityList=this.vueElementAdminUserRoleDao.findAllByDelFlagAndStatus(false,true);
            List<VueElementAdminUserRoleWebVO> vueElementAdminUserRoleWebVOList=this.convertToWebVOList(vueElementAdminUserRoleEntityList);
            vueElementAdminResponse.setCode(ResponseCodeEnum.OK.getCode());
            vueElementAdminResponse.setMessage("获取角色列表成功");
            vueElementAdminResponse.setData(vueElementAdminUserRoleWebVOList);
        } catch (Exception e) {
            log.error("获取角色列表成功异常",e);
            vueElementAdminResponse.setCode(ResponseCodeEnum.OTHER_SERVER_EXCEPTION.getCode());
            vueElementAdminResponse.setMessage(ResponseCodeEnum.OTHER_SERVER_EXCEPTION.getMessage());
            vueElementAdminResponse.setData(null);
        }
        return vueElementAdminResponse;
    }
}
