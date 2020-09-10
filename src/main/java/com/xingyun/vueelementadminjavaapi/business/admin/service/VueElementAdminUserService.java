package com.xingyun.vueelementadminjavaapi.business.admin.service;

import com.xingyun.vueelementadminjavaapi.business.admin.model.VueElementAdminUserEntity;
import com.xingyun.vueelementadminjavaapi.business.admin.model.VueElementAdminUserLogin;
import com.xingyun.vueelementadminjavaapi.business.admin.model.VueElementAdminUserWebVO;
import com.xingyun.vueelementadminjavaapi.business.admin.model.VueWelcomeInfo;
import com.xingyun.vueelementadminjavaapi.model.VueElementAdminResponse;

import java.util.List;
import java.util.Optional;

/**
 * @author qing-feng.zhao
 */
public interface VueElementAdminUserService {

    VueElementAdminResponse initVueElementAdmin();

    List<VueWelcomeInfo>  initVueWelcomeInfoList();

    Optional<VueElementAdminUserEntity> loginVueElementAdmin(VueElementAdminUserLogin vueElementAdminUserLogin);

    VueElementAdminUserEntity saveVueElementAdminUser(VueElementAdminUserEntity vueElementAdminUserEntity);

    Optional<VueElementAdminUserEntity> findVueElementAdminUserByToken(String token);

    VueElementAdminUserWebVO convertToWebVO(VueElementAdminUserEntity vueElementAdminUserEntity);
}
