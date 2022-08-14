package com.xingyun.vueelementadminjavaapi.business.admin.model.entity;

import com.xingyun.vueelementadminjavaapi.framework.model.constant.GlobalConstantValues;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Spring Data JPA 配置
 */
@Table(name = "t_vue_element_admin_user")
@Entity
@Data
public class  VueElementAdminUserEntity implements Serializable {
    /**
     * 主键
     * -GenerationType.AUTO主键由程序控制, 是默认选项 ,不设置就是这个
     * -GenerationType.IDENTITY 主键由数据库生成, 采用数据库自增长, Oracle不支持这种方式
     * -GenerationType.SEQUENCE 通过数据库的序列产生主键,Oracle支持,MYSQL不支持
     * -GenerationType.TABLE 提供特定的数据库产生主键, 该方式更有利于数据库的移植
     * - 雪花算法生成Id
     */
    @Id
    @GeneratedValue(generator = "SnowFlakeId")
    @GenericGenerator(name = "SnowFlakeId", strategy = GlobalConstantValues.SNOW_FlAKE_ID_GENERATOR)
    @Column(name = "id")
    private Long id;
    /**
     * 账号
     */
    @Column(name = "username")
    private String username;
    /**
     * 密码
     */
    @Column(name = "password")
    private String password;
    /**
     * token
     */
    @Column(name = "token")
    private String token;
    /**
     * 角色列表,多个角色名称以逗号分割
     */
    @Column(name = "roles")
    private String roles;
    /**
     * 角色Id列表
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
    @Column(name = "role_ids",columnDefinition = "text",nullable = true)
    private String roleIds;
    /**
     * 自我介绍
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "introduction",columnDefinition = "text",nullable = true)
    private String introduction;
    /**
     * 头像
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "avatar",columnDefinition = "text",nullable = true)
    private String avatar;
    /**
     * 用户昵称
     */
    @Column(name = "name")
    private String name;

    /**
     * 创建时间
     */
    @Column(name = "create_time",nullable = false)
    private Date createTime;
    /**
     * 更新时间
     */
    @Column(name = "update_time",nullable = false)
    private Date updateTime;
    /**
     * 创建者
     */
    @Column(name = "create_by",length = 32)
    private String createBy;
    /**
     * 修改者
     */
    @Column(name = "update_by",length = 32)
    private String updateBy;
    /**
     * 逻辑删除标记 true 表示删除 false 表示没有删除
     */
    @Column(name = "del_flag",nullable = false)
    private Boolean delFlag;
    /**
     * 用户启用状态 true 启用 false 禁用
     */
    @Column(name = "status",nullable = false)
    private Boolean status;
}
