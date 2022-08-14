package com.xingyun.vueelementadminjavaapi.business.admin.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qing-feng.zhao
 */
@Data
public class VueElementAdminUserRoleAddedParam implements Serializable {
    private static final long serialVersionUID = 2645390820883359538L;
    private String roleName;

    private String roleDisplayName;

    private String remarks;

    private Boolean status;
}
