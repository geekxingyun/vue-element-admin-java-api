package com.xingyun.vueelementadminjavaapi.business.admin.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author qing-feng.zhao
 */
@Data
public class VueElementAdminUserSavedParam implements Serializable {
    private static final long serialVersionUID = 302627403441924539L;
    /**
     * id
     */
    @ApiModelProperty(name = "id")
    private String id;
    /**
     * 账号
     */
    @ApiModelProperty(name = "username")
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty(name = "password")
    private String password;
    /**
     * 用户昵称
     */
    @ApiModelProperty(name = "name")
    private String name;
    /**
     * 角色
     */
    @ApiModelProperty(name = "roleIds")
    private List<String> roleIds;
    /***
     * 状态
     */
    @ApiModelProperty(name = "status")
    private Boolean status;
}
