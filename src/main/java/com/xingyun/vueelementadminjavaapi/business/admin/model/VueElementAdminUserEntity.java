package com.xingyun.vueelementadminjavaapi.business.admin.model;

import com.xingyun.vueelementadminjavaapi.customized.SnowFlakeIdGenerator;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author qing-feng.zhao
 */
@Data
@Table(name = "t_vue_element_admin_user")
@Entity
public class VueElementAdminUserEntity {
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
    @GenericGenerator(name = "SnowFlakeId", strategy = "com.xingyun.vueelementadminjavaapi.customized.SnowFlakeIdGenerator")
    @Column(name = "id")
    private Long id;
    /**
     * 用户名
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
     * 角色
     */
    @Column(name = "roles")
    private String roles;
    /**
     * 自我介绍
     */
    @Column(name = "introduction")
    private String introduction;
    /**
     * 头像
     */
    @Column(name = "avatar")
    private String avatar;
    /**
     *
     */
    @Column(name = "name")
    private String name;
}
