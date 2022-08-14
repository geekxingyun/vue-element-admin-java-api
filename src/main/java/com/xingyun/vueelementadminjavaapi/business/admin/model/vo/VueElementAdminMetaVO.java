package com.xingyun.vueelementadminjavaapi.business.admin.model.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/***
 * @author qingfeng.zhao
 * @date 2022/7/14
 * @apiNote
 */
@Data
public class VueElementAdminMetaVO implements Serializable {
    private List<String> roles;
    private String title;
    private String icon;
    private Boolean noCache;
}
