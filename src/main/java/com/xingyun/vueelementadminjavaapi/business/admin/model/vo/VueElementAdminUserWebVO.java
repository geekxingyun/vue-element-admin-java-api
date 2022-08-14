package com.xingyun.vueelementadminjavaapi.business.admin.model.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author qing-feng.zhao
 */
@Data
public class VueElementAdminUserWebVO implements Serializable {

    @ApiModelProperty(name = "numberId",value = "序号")
    private Integer numberId;
    @ApiModelProperty(name = "id",value = "主键")
    private String id;
    @ApiModelProperty(name = "name",value = "用户名")
    private String name;
    @ApiModelProperty(name = "username",value = "账号")
    private String username;
    @ApiModelProperty(name = "roles",value = "角色",notes = "角色标签前端以这个标签来验证权限")
    private String roles;
    @ApiModelProperty(name = "roleIds",value = "角色id列表")
    private List<String> roleIds;
    @ApiModelProperty(name = "roleDisplayName",value = "角色显示名称")
    private String roleDisplayName;
    @ApiModelProperty(name = "status",value = "状态")
    private Boolean status;
    @ApiModelProperty(name = "createBy")
    private String createBy;
    @ApiModelProperty(name = "createDate")
    private String createDate;
    @ApiModelProperty(name = "updateBy")
    private String updateBy;
    @ApiModelProperty(name = "updateDate")
    private String updateDate;
}
