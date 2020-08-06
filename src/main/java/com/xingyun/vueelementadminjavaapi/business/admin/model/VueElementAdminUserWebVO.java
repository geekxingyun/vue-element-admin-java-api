package com.xingyun.vueelementadminjavaapi.business.admin.model;

import lombok.Data;

/**
 * @author qing-feng.zhao
 */
@Data
public class VueElementAdminUserWebVO {
    private String token;
    private String[] roles;
    private String introduction;
    private String avatar;
    private String name;
}
