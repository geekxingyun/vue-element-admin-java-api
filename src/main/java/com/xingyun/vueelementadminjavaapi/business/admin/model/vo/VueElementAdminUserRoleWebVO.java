package com.xingyun.vueelementadminjavaapi.business.admin.model.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author qing-feng.zhao
 */
@Data
public class VueElementAdminUserRoleWebVO implements Serializable {
    private static final long serialVersionUID = 2010585322028097493L;
    private Integer numberId;
    private String id;
    private String roleName;
    private String roleDisplayName;
    private String remarks;
    private Boolean status;
    private String createDate;
    private String updateDate;
    private String createBy;
    private String updateBy;
}
