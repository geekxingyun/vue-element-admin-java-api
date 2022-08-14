package com.xingyun.vueelementadminjavaapi.business.admin.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qing-feng.zhao
 */
@Data
public class VueElementAdminUserRoleUpdateStatusParam implements Serializable {
    private static final long serialVersionUID = 501311691922375323L;
    private String id;
    private Boolean status;
}
