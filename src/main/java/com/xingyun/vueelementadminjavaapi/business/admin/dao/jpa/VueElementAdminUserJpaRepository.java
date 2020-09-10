package com.xingyun.vueelementadminjavaapi.business.admin.dao.jpa;

import com.xingyun.vueelementadminjavaapi.business.admin.model.VueElementAdminUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author qing-feng.zhao
 */
public interface VueElementAdminUserJpaRepository extends JpaRepository<VueElementAdminUserEntity,Long> {
    /**
     * 根据token 查询登陆用户信息
     * @param token
     * @return
     */
    Optional<VueElementAdminUserEntity> findByToken(String token);
}
