package com.xingyun.vueelementadminjavaapi.business.admin.dao.role.jpa;

import com.xingyun.vueelementadminjavaapi.business.admin.model.entity.VueElementAdminUserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author qing-feng.zhao
 */
@Repository
public interface VueElementAdminUserRoleJpaRepository extends JpaRepository<VueElementAdminUserRoleEntity,Long> {
    /**
     * 查询角色是否存在
     * @param roleName
     * @param delFlag
     * @return
     */
    Boolean existsByRoleNameAndDelFlag(String roleName,Boolean delFlag);

    List<VueElementAdminUserRoleEntity> findAllByDelFlagAndStatus(Boolean delFlag, Boolean status);

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
