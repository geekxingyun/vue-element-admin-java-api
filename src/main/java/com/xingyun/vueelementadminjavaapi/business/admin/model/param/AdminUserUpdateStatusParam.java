package com.xingyun.vueelementadminjavaapi.business.admin.model.param;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qing-feng.zhao
 */
@Data
public class AdminUserUpdateStatusParam implements Serializable {
    private static final long serialVersionUID = 4101503336216443061L;
    private String id;
    private Boolean status;
}
