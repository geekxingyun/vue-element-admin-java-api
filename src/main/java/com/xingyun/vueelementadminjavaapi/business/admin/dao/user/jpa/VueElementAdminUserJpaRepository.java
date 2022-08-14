package com.xingyun.vueelementadminjavaapi.business.admin.dao.user.jpa;

import com.xingyun.vueelementadminjavaapi.business.admin.model.entity.VueElementAdminUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author qing-feng.zhao
 */
@Repository
public interface VueElementAdminUserJpaRepository extends JpaRepository<VueElementAdminUserEntity,Long>{
    /**
     * 根据token 查询登陆用户信息
     * @param token
     * @return
     */
    Optional<VueElementAdminUserEntity> findByToken(String token);
    /**
     * 根据条件过滤查找用户信息
     * @param username
     * @param delFlag
     * @param status
     * @return
     */
    Optional<VueElementAdminUserEntity> findByUsernameAndDelFlagAndStatus(String username,Boolean delFlag,Boolean status);
    /**
     * 查找用户信息根据账号
     * @param username
     * @param delFlag
     * @return
     */
    Optional<VueElementAdminUserEntity> findByUsernameAndDelFlag(String username,Boolean delFlag);
    /**
     * 判断用户是否存在
     * @param username
     * @param delFlag
     * @return
     */
    Boolean existsAllByUsernameAndDelFlag(String username,Boolean delFlag);
}
