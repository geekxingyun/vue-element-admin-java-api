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
public class VueElementAdminUserRouterVO implements Serializable {
    /**
     * 请求路径
     */
    private String path="";
    /**
     * Vue组件
     */
    private String component;
    /**
     * 是否隐藏组件
     */
    private Boolean hidden;
    /**
     * 父级Id
     */
    private Long parentId;
    /**
     * 重定向
     */
    private String redirect;
    /**
     * 名称
     */
    private String name;
    /**
     * meta节点
     */
    private VueElementAdminMetaVO meta;
    /**
     * children
     */
    private List<VueElementAdminUserRouterVO> children;
}
