package com.xingyun.vueelementadminjavaapi.framework.model.enumvalue;

import lombok.Getter;

/***
 * @author qingfeng.zhao
 * @date 2022/4/26
 * @apiNote
 */
public enum ResponseCodeEnum {
    OK(20000,"响应成功"),
    INVALID_ACCOUNT_OR_PASSWORD(60204,"账号或密码不正确"),
    LOGIN_FAIL(50008,"登陆失败,无法获取用户信息"),
    INVALID_LOGIN_TOKEN(50014,"登陆失效"),
    INVALID_PARAM(40001,"无效的请求参数"),
    EXIST_ACCOUNT(40001,"账号已存在"),
    OPERATOR_FAIL(40002,"操作失败"),

    OTHER_SERVER_EXCEPTION(50000,"服务器异常");
    @Getter
    private Integer code;
    @Getter
    private String message;
    ResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
