package com.xingyun.vueelementadminjavaapi.business.admin.dao.user.impl;

import com.xingyun.vueelementadminjavaapi.business.admin.dao.user.VueElementAdminUserDao;
import com.xingyun.vueelementadminjavaapi.business.admin.dao.user.jpa.VueElementAdminUserJpaRepository;
import com.xingyun.vueelementadminjavaapi.business.admin.model.entity.VueElementAdminUserEntity;
import com.xingyun.vueelementadminjavaapi.business.admin.model.param.VueElementAdminUserWebQuery;
import com.xingyun.vueelementadminjavaapi.framework.model.param.MyPageParam;
import com.xingyun.vueelementadminjavaapi.framework.model.vo.BackPageAdapterVO;
import com.xingyun.vueelementadminjavaapi.framework.util.SmartStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/***
 * @author qingfeng.zhao
 * @date 2022/4/26
 * @apiNote
 */
@Primary
@Repository
public class VueElementAdminUserDaoJpaImpl implements VueElementAdminUserDao {

    private final VueElementAdminUserJpaRepository vueElementAdminUserJpaRepository;
    public VueElementAdminUserDaoJpaImpl(VueElementAdminUserJpaRepository vueElementAdminUserJpaRepository) {
        this.vueElementAdminUserJpaRepository = vueElementAdminUserJpaRepository;
    }

    @Override
    public VueElementAdminUserEntity insert(VueElementAdminUserEntity vueElementAdminUserEntity) {
        return this.vueElementAdminUserJpaRepository.save(vueElementAdminUserEntity);
    }

    @Override
    public VueElementAdminUserEntity save(VueElementAdminUserEntity vueElementAdminUserEntity) {
        return this.vueElementAdminUserJpaRepository.save(vueElementAdminUserEntity);
    }

    @Override
    public Optional<VueElementAdminUserEntity> findByToken(String token) {
        return this.vueElementAdminUserJpaRepository.findByToken(token);
    }

    @Override
    public Optional<VueElementAdminUserEntity> findById(Long id) {
        return this.vueElementAdminUserJpaRepository.findById(id);
    }

    @Override
    public Optional<VueElementAdminUserEntity> findByUserName(String username) {
        return this.vueElementAdminUserJpaRepository.findByUsernameAndDelFlag(username,false);
    }

    @Override
    public Boolean existsAllByUsername(String username) {
        return this.vueElementAdminUserJpaRepository.existsAllByUsernameAndDelFlag(username,false);
    }

    @Override
    public BackPageAdapterVO<VueElementAdminUserEntity> findAllVueElementAdminUserPageList(VueElementAdminUserWebQuery vueElementAdminUserWebQuery, MyPageParam myPageParam) {
        vueElementAdminUserWebQuery.setRoleId(SmartStringUtils.trimToNull(vueElementAdminUserWebQuery.getRoleId()));
        vueElementAdminUserWebQuery.setUsername(SmartStringUtils.trimToNull(vueElementAdminUserWebQuery.getUsername()));
        VueElementAdminUserEntity vueElementAdminUserEntity=new VueElementAdminUserEntity();
        BeanUtils.copyProperties(vueElementAdminUserWebQuery,vueElementAdminUserEntity);
        if(null!=vueElementAdminUserWebQuery.getName()){
            vueElementAdminUserEntity.setName(vueElementAdminUserWebQuery.getName());
        }
        if(null!=vueElementAdminUserWebQuery.getUsername()){
            vueElementAdminUserEntity.setUsername(vueElementAdminUserWebQuery.getUsername());
        }
        if(null!=vueElementAdminUserWebQuery.getRole()){
            vueElementAdminUserEntity.setRoles(vueElementAdminUserWebQuery.getRole());
        }
        vueElementAdminUserEntity.setDelFlag(false);
        //设置匹配规则
        ExampleMatcher exampleMatcher=ExampleMatcher
                //建造者模式 创建一个默认的 ExampleMatcher 对象
                //A、nullHandler：IGNORE。Null值处理方式：忽略
                //B、defaultStringMatcher：DEFAULT。默认字符串匹配方式：默认（相等）
                //C、defaultIgnoreCase：false。默认大小写忽略方式：不忽略
                //D、propertySpecifiers：空。各属性特定查询方式，空。
                //E、ignoredPaths：空列表。忽略属性列表，空列表。
                .matching()
                //昵称模糊查询
                .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());
        Example<VueElementAdminUserEntity> vueElementAdminUserEntityExample=Example.of(vueElementAdminUserEntity);
        //分页参数
        myPageParam.initJpaPageParam();
        Pageable pageable= PageRequest.of(myPageParam.getStart(),myPageParam.getLength());
        Page<VueElementAdminUserEntity> vueElementAdminUserEntityPage=this.vueElementAdminUserJpaRepository.findAll(vueElementAdminUserEntityExample,pageable);
        // 封装自定义分页对象
        BackPageAdapterVO<VueElementAdminUserEntity> vueElementAdminUserEntityBackPageAdapterVO =new BackPageAdapterVO<>();
        // 总记录数
        vueElementAdminUserEntityBackPageAdapterVO.setTotalElements(vueElementAdminUserEntityPage.getTotalElements());
        // 总页数
        vueElementAdminUserEntityBackPageAdapterVO.setTotalPages(vueElementAdminUserEntityPage.getTotalPages());
        // 当页内容
        vueElementAdminUserEntityBackPageAdapterVO.setContent(vueElementAdminUserEntityPage.getContent());
        // 该方法直接返回每页的大小，与实际含有多少元素无关
        vueElementAdminUserEntityBackPageAdapterVO.setSize(vueElementAdminUserEntityPage.getSize());
        // 该方法返回实际含有多少元素，最小为0，最大为每页最大的元素的数量。
        vueElementAdminUserEntityBackPageAdapterVO.setNumberOfElements(vueElementAdminUserEntityPage.getNumberOfElements());
        return vueElementAdminUserEntityBackPageAdapterVO;
    }

    @Override
    public long count() {
        return this.vueElementAdminUserJpaRepository.count();
    }
}
