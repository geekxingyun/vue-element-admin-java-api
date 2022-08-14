package com.xingyun.vueelementadminjavaapi.business.admin.model.enums;

import lombok.Getter;

/***
 * @author qingfeng.zhao
 * @date 2022/6/9
 * @apiNote
 */
public enum LoginTypeEnum {
    PASSWORD("password");
    @Getter
    private String displayName;
    LoginTypeEnum(String displayName){
        this.displayName=displayName;
    }
}
