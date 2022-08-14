package com.xingyun.vueelementadminjavaapi.business.admin.dao.user;

import com.xingyun.vueelementadminjavaapi.business.admin.model.entity.VueElementAdminUserEntity;
import com.xingyun.vueelementadminjavaapi.business.admin.model.param.VueElementAdminUserWebQuery;
import com.xingyun.vueelementadminjavaapi.framework.model.param.MyPageParam;
import com.xingyun.vueelementadminjavaapi.framework.model.vo.BackPageAdapterVO;

import java.util.Optional;

/***
 * @author qingfeng.zhao
 * @date 2022/4/26
 * @apiNote
 */
public interface VueElementAdminUserDao {
    /**
     * 添加系统用户信息
     * @param vueElementAdminUserEntity
     * @return
     */
    VueElementAdminUserEntity insert(VueElementAdminUserEntity vueElementAdminUserEntity);
    /**
     * 保存系统用户信息
     * @param vueElementAdminUserEntity
     * @return
     */
    VueElementAdminUserEntity save(VueElementAdminUserEntity vueElementAdminUserEntity);
    /**
     * 根据token查找用户信息
     * @param token
     * @return
     */
    Optional<VueElementAdminUserEntity> findByToken(String token);
    /**
     * 根据id查找用户信息
     * @param id
     * @return
     */
    Optional<VueElementAdminUserEntity> findById(Long id);
    /**
     * 系统登陆
     * @param username
     * @return
     */
    Optional<VueElementAdminUserEntity> findByUserName(String username);
    /**
     * 检查账号是否存在
     * @param username
     * @return
     */
    Boolean existsAllByUsername(String username);
    /**
     * 查询分页对象
      * @param vueElementAdminUserWebQuery
     * @param myPageParam
     * @return
     */
    BackPageAdapterVO<VueElementAdminUserEntity> findAllVueElementAdminUserPageList(VueElementAdminUserWebQuery vueElementAdminUserWebQuery, MyPageParam myPageParam);
    /**
     * 查询当前总记录数
     * @return
     */
    long count();
}
