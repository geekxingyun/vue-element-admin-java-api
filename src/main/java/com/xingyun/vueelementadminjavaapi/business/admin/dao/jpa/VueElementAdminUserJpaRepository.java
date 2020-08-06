package com.xingyun.vueelementadminjavaapi.business.admin.dao.jpa;

import com.xingyun.vueelementadminjavaapi.business.admin.model.VueElementAdminUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author qing-feng.zhao
 */
public interface VueElementAdminUserJpaRepository extends JpaRepository<VueElementAdminUserEntity,Long> {

    Optional<VueElementAdminUserEntity> findByToken(String token);
}
