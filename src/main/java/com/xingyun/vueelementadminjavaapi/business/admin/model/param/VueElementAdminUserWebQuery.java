package com.xingyun.vueelementadminjavaapi.business.admin.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qing-feng.zhao
 */
@Data
public class VueElementAdminUserWebQuery implements Serializable {

    private static final long serialVersionUID = 7118286923338940131L;
    /**
     * 根据用户名称搜索
     */
    private String name;
    /**
     * 根据角色id搜索
     */
    private String roleId;
    /**
     * 根据角色代号搜索
     */
    private String role;
    /**
     * 根据账号搜索
     */
    private String username;
    /**
     * 根据启用状态搜索
     */
    private Boolean status;
}
