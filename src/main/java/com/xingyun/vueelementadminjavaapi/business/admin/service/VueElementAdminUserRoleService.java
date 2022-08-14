package com.xingyun.vueelementadminjavaapi.business.admin.service;

import com.xingyun.vueelementadminjavaapi.business.admin.model.entity.VueElementAdminUserRoleEntity;
import com.xingyun.vueelementadminjavaapi.business.admin.model.param.VueElementAdminUserRoleAddedParam;
import com.xingyun.vueelementadminjavaapi.business.admin.model.param.VueElementAdminUserRoleQueryParam;
import com.xingyun.vueelementadminjavaapi.business.admin.model.param.VueElementAdminUserRoleSaveParam;
import com.xingyun.vueelementadminjavaapi.business.admin.model.param.VueElementAdminUserRoleUpdateStatusParam;
import com.xingyun.vueelementadminjavaapi.business.admin.model.vo.VueElementAdminUserRoleWebVO;
import com.xingyun.vueelementadminjavaapi.framework.model.param.MyPageParam;
import com.xingyun.vueelementadminjavaapi.framework.model.vo.BackPageAdapterVO;
import com.xingyun.vueelementadminjavaapi.framework.model.vo.VueElementAdminResponse;

import java.util.List;
import java.util.Optional;

/***
 * @author qingfeng.zhao
 * @date 2022/4/26
 * @apiNote
 */
public interface VueElementAdminUserRoleService {
    /**
     * 查找
     * @param myPageParam
     * @return
     */
    BackPageAdapterVO<VueElementAdminUserRoleEntity> findAllVueElementAdminUserRoleEntityPageList(VueElementAdminUserRoleQueryParam vueElementAdminUserRoleQueryParam, MyPageParam myPageParam);
    /**
     *
     * @param vueElementAdminUserRoleQueryParam
     * @param myPageParam
     * @return
     */
    VueElementAdminResponse findAllVueElementAdminUserRolePageListResponse(VueElementAdminUserRoleQueryParam vueElementAdminUserRoleQueryParam, MyPageParam myPageParam);
    /**
     *
     * @param vueElementAdminUserRoleAddedParam
     * @param token
     * @return
     */
    VueElementAdminResponse addedVueElementAdminUserRole(VueElementAdminUserRoleAddedParam vueElementAdminUserRoleAddedParam, String token);

    VueElementAdminUserRoleEntity addedVueElementAdminUserRoleEntity(VueElementAdminUserRoleEntity vueElementAdminUserRoleEntity);

    Long countVueElementAdminUserRole();
    /**
     *
     * @param id
     * @return
     */
    Optional<VueElementAdminUserRoleEntity> findVueElementAdminUserRoleEntityById(String id);

    /**
     *
     * @param roleName
     * @return
     */
    Optional<VueElementAdminUserRoleEntity> findVueElementAdminUserRoleEntityByRoleName(String roleName);

    /**
     * 对象类型转换
     * @param vueElementAdminUserRoleEntityList
     * @return
     */
    List<VueElementAdminUserRoleWebVO> convertToWebVOList(List<VueElementAdminUserRoleEntity> vueElementAdminUserRoleEntityList);

    VueElementAdminUserRoleWebVO convertToWebVO(VueElementAdminUserRoleEntity vueElementAdminUserRoleEntity);

    /**
     *
     * @param vueElementAdminUserRoleSaveParam
     * @param token
     * @return
     */
    VueElementAdminResponse savedVueElementAdminUserRoleEntity(VueElementAdminUserRoleSaveParam vueElementAdminUserRoleSaveParam, String token);

    VueElementAdminResponse removedVueElementAdminUserRoleEntity(String id,String token);

    VueElementAdminResponse deleteVueElementAdminUserRoleEntity(String id);

    VueElementAdminResponse updateVueElementAdminUserRoleEntityStatus(VueElementAdminUserRoleUpdateStatusParam vueElementAdminUserRoleUpdateStatusParam, String token);

    /**
     *
     * @return
     */
    VueElementAdminResponse fetchAllElementAdminUserRoleList();
}
