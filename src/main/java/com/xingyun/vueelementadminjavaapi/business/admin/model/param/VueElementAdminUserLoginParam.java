package com.xingyun.vueelementadminjavaapi.business.admin.model.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 登陆请求参数
 * @author qing-feng.zhao
 */
@Data
public class VueElementAdminUserLoginParam implements Serializable {

    @ApiModelProperty(name = "username",value = "登陆账号",example = "admin",required = false)
    private String username;
    @ApiModelProperty(name = "password",value = "登陆密码",example = "111111",required = false)
    private String password;
    @ApiModelProperty(name = "loginType",value = "登陆类型",example = "password",notes = "可选值:password|qq|wechat")
    private String loginType;
}

