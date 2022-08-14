package com.xingyun.vueelementadminjavaapi.business.admin.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qing-feng.zhao
 */
@Data
public class VueElementAdminUserRoleSaveParam implements Serializable {
    private static final long serialVersionUID = -3027438572262262661L;
    private String id;
    private String roleName;
    private String roleDisplayName;
    private String remarks;
    private Boolean status;
}
