package com.xingyun.vueelementadminjavaapi.business.admin.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qing-feng.zhao
 */
@Data
public class VueElementAdminUserRoleQueryParam implements Serializable {
    private static final long serialVersionUID = 4909530804502859532L;
    private String roleName;
    private String roleDisplayName;
    private String remarks;
    private Boolean status;
}
