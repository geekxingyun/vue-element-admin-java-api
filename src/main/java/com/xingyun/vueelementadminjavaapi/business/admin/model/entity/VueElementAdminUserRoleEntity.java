package com.xingyun.vueelementadminjavaapi.business.admin.model.entity;

import com.xingyun.vueelementadminjavaapi.framework.model.constant.GlobalConstantValues;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/***
 * @author qingfeng.zhao
 * @date 2022/4/26
 * @apiNote
 */
@Table(name = "t_vue_element_admin_user_role")
@Entity
@Data
public class VueElementAdminUserRoleEntity implements Serializable {
    private static final long serialVersionUID = -7004549947420096029L;
    /**
     * 角色Id
     */
    @Id
    @GeneratedValue(generator = "SnowFlakeId")
    @GenericGenerator(name = "SnowFlakeId", strategy = GlobalConstantValues.SNOW_FlAKE_ID_GENERATOR)
    @Column(name = "id")
    private Long id;
    /**
     * 角色名称
     */
    @Column(name = "role_name")
    private String roleName;
    /**
     * 备注
     */
    /**
     * 文章内容
     * CLOB （Character Large Ojects）类型是长字符串类型，
     * java.sql.Clob、 Character[]、char[] 和 String 将被映射为 Clob 类型。
     * BLOB 主要存储图片、音频信息等 由于现在图片和音频越来越多，检索起来也不方便，
     * 所以都不放在数据库，一般放在专门的文件存储服务器上。比如阿里云OOS
     * TEXT 只能存储纯文本文件
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "remarks",columnDefinition = "text",nullable = true)
    private String remarks;
    /**
     * 角色显示名称
     */
    @Column(name = "role_display_name")
    private String roleDisplayName;
    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;
    /**
     * 创建者
     */
    @Column(name = "create_by")
    private String createBy;
    /**
     * 修改者
     */
    @Column(name = "update_by")
    private String updateBy;
    /**
     * 逻辑删除标记 true 表示删除 false 表示没有删除
     */
    @Column(name = "del_flag")
    private Boolean delFlag;
    /**
     * 用户启用状态 true 启用 false 禁用
     */
    @Column(name = "status")
    private Boolean status;
}
