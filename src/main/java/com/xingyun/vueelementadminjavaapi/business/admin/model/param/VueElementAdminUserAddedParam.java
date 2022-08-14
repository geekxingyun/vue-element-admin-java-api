package com.xingyun.vueelementadminjavaapi.business.admin.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author qing-feng.zhao
 */
@Data
public class VueElementAdminUserAddedParam implements Serializable {
    private static final long serialVersionUID = 624918032690904203L;
    /**
     * 账号
     */
    @ApiModelProperty(name = "username",required = true)
    private String username;
    /**
     * 密码
     */
    @ApiModelProperty(name = "password",required = true)
    private String password;
    /**
     * 用户昵称
     */
    @ApiModelProperty(name = "name",required = true)
    private String name;
    /**
     * 角色
     */
    @ApiModelProperty(name = "roleId",required = true)
    private List<String> roleIds;
    /**
     * 状态
     */
    @ApiModelProperty(name = "status",required = true)
    private Boolean status;
}
