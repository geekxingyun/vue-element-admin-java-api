package com.xingyun.vueelementadminjavaapi.business.admin.dao.role;


import com.xingyun.vueelementadminjavaapi.business.admin.model.entity.VueElementAdminUserRoleEntity;
import com.xingyun.vueelementadminjavaapi.business.admin.model.param.VueElementAdminUserRoleQueryParam;
import com.xingyun.vueelementadminjavaapi.framework.model.param.MyPageParam;
import com.xingyun.vueelementadminjavaapi.framework.model.vo.BackPageAdapterVO;

import java.util.List;
import java.util.Optional;

/***
 * @author qingfeng.zhao
 * @date 2022/4/27
 * @apiNote
 */
public interface VueElementAdminUserRoleDao {
    /**
     * 查询角色是否存在
     * @param roleName
     * @param delFlag
     * @return
     */
    Boolean existsByRoleNameAndDelFlag(String roleName,Boolean delFlag);

    VueElementAdminUserRoleEntity save(VueElementAdminUserRoleEntity vueElementAdminUserRoleEntity);

    BackPageAdapterVO<VueElementAdminUserRoleEntity> findAll(VueElementAdminUserRoleQueryParam vueElementAdminUserRoleQueryParam, MyPageParam myPageParam);

    long count();

    List<VueElementAdminUserRoleEntity> findAllByDelFlagAndStatus(Boolean delFlag, Boolean status);

    Optional<VueElementAdminUserRoleEntity> findById(Long id);

    void deleteById(Long id);
    /**
     *
     * @param id
     * @param status
     * @return
     */
    Optional<VueElementAdminUserRoleEntity> findByIdAndStatus(Long id, Boolean status);

    /**
     *
     * @return
     */
    Optional<VueElementAdminUserRoleEntity> findByRoleNameAndStatusAndDelFlag(String roleName,Boolean status,Boolean delFlag);
}
