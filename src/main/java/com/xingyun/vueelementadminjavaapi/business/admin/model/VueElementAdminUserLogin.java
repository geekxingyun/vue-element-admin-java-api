package com.xingyun.vueelementadminjavaapi.business.admin.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author qing-feng.zhao
 */
@Data
public class VueElementAdminUserLogin {

    @ApiModelProperty(name = "username",value = "登陆账号",example = "admin")
    private String username;
    @ApiModelProperty(name = "password",value = "登陆密码",example = "111111")
    private String password;
}

