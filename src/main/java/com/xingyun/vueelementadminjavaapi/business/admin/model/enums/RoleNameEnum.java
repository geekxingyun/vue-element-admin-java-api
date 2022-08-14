package com.xingyun.vueelementadminjavaapi.business.admin.model.enums;

import lombok.Getter;

/***
 * 系统用户角色
 * @author qingfeng.zhao
 * @date 2022/6/5
 * @apiNote
 */
public enum RoleNameEnum {

    SUPER_ADMIN("superAdmin","超级管理员"),
    ADMIN("admin","管理员"),
    SKILL_LEADER("skillLeader","技术总监"),
    TEAM_LEADER("teamLeader","团队负责人"),
    PROJECT_MANAGER("projectManager","项目经理"),
    EDIT("edit","编辑者"),
    DEVELOPER("developer","研发"),
    QA("qa","测试"),
    ANALYST("analyst","分析师"),
    VISITOR("visitor","访问者");

    @Getter
    private String roleName;

    @Getter
    private String display_name;

    RoleNameEnum(String roleName, String display_name) {
        this.roleName = roleName;
        this.display_name = display_name;
    }
}
