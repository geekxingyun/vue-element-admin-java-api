package com.xingyun.vueelementadminjavaapi.framework.model.enumvalue;

import lombok.Getter;

/***
 * @author qingfeng.zhao
 * @date 2022/7/23
 * @apiNote
 */
public enum MySpringSecurityRoleEnum {
    VISIT_SWAGGER_API_DOC_ROLE("visit-swagger-api-doc-role"),
    VISIT_ACTUATOR_HEALTH_CHECK_DETAIL_ROLE("visit-actuator-health-check-detail-role"),
    VISIT_H2_DB_ROLE("visit-h2-db-role");
    @Getter
    private String roleName;
    MySpringSecurityRoleEnum(String roleName){
        this.roleName=roleName;
    }
}
