package com.xingyun.vueelementadminjavaapi.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * vue-element-admin 框架统一返回结果
 * @author qing-feng.zhao
 */
@Component
@Data
public class VueElementAdminResponse implements Serializable {
    private static final long serialVersionUID = -8668034013803393986L;

    @ApiModelProperty(name = "code",value = "响应码",example = "20000")
    private Integer code;

    @ApiModelProperty(name = "message",value = "响应消息",example = "请求成功")
    private String message;

    @ApiModelProperty(name = "data",value = "响应数据",example = "请求成功")
    private Object data;
}