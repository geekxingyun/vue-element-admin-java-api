package com.xingyun.vueelementadminjavaapi.business.admin.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 登陆请求参数
 * @author qing-feng.zhao
 */
@Data
public class VueElementAdminUserLoginVO implements Serializable {
    private String name;
    private String avatar;
    private String introduction;
    private List<String> roles;
}

