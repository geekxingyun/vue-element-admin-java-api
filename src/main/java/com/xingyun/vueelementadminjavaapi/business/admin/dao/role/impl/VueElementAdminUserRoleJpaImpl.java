package com.xingyun.vueelementadminjavaapi.business.admin.dao.role.impl;

import com.xingyun.vueelementadminjavaapi.business.admin.dao.role.VueElementAdminUserRoleDao;
import com.xingyun.vueelementadminjavaapi.business.admin.dao.role.jpa.VueElementAdminUserRoleJpaRepository;
import com.xingyun.vueelementadminjavaapi.business.admin.model.entity.VueElementAdminUserRoleEntity;
import com.xingyun.vueelementadminjavaapi.business.admin.model.param.VueElementAdminUserRoleQueryParam;
import com.xingyun.vueelementadminjavaapi.framework.model.param.MyPageParam;
import com.xingyun.vueelementadminjavaapi.framework.model.vo.BackPageAdapterVO;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/***
 * @author qingfeng.zhao
 * @date 2022/6/5
 * @apiNote
 */
@Primary
@Repository
public class VueElementAdminUserRoleJpaImpl implements VueElementAdminUserRoleDao {

    private final VueElementAdminUserRoleJpaRepository vueElementAdminUserRoleJpaRepository;

    public VueElementAdminUserRoleJpaImpl(VueElementAdminUserRoleJpaRepository vueElementAdminUserRoleJpaRepository) {
        this.vueElementAdminUserRoleJpaRepository = vueElementAdminUserRoleJpaRepository;
    }

    @Override
    public Boolean existsByRoleNameAndDelFlag(String roleName, Boolean delFlag) {
        return this.vueElementAdminUserRoleJpaRepository.existsByRoleNameAndDelFlag(roleName,delFlag);
    }

    @Override
    public VueElementAdminUserRoleEntity save(VueElementAdminUserRoleEntity vueElementAdminUserRoleEntity) {
        return this.vueElementAdminUserRoleJpaRepository.save(vueElementAdminUserRoleEntity);
    }

    @Override
    public BackPageAdapterVO<VueElementAdminUserRoleEntity> findAll(VueElementAdminUserRoleQueryParam vueElementAdminUserRoleQueryParam, MyPageParam myPageParam) {
        Sort sort= Sort.by("createTime").descending();
        myPageParam.initJpaPageParam();
        Pageable pageable= PageRequest.of(myPageParam.getStart(),myPageParam.getLength(),sort);
        VueElementAdminUserRoleEntity vueElementAdminUserRoleEntity=new VueElementAdminUserRoleEntity();
        BeanUtils.copyProperties(vueElementAdminUserRoleQueryParam,vueElementAdminUserRoleEntity);
        vueElementAdminUserRoleEntity.setDelFlag(false);
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
                .withMatcher("roleName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("roleDisplayName", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("remarks", ExampleMatcher.GenericPropertyMatchers.contains());

        Example<VueElementAdminUserRoleEntity> vueElementAdminUserRoleEntityExample=Example.of(vueElementAdminUserRoleEntity,exampleMatcher);
        Page<VueElementAdminUserRoleEntity> vueElementAdminUserRoleEntityPage= this.vueElementAdminUserRoleJpaRepository.findAll(vueElementAdminUserRoleEntityExample,pageable);
        BackPageAdapterVO<VueElementAdminUserRoleEntity> vueElementAdminUserRoleEntityBackPageAdapterVO =new BackPageAdapterVO<>();
        vueElementAdminUserRoleEntityBackPageAdapterVO.setTotalElements(vueElementAdminUserRoleEntityPage.getTotalElements());
        vueElementAdminUserRoleEntityBackPageAdapterVO.setTotalPages(vueElementAdminUserRoleEntityPage.getTotalPages());
        vueElementAdminUserRoleEntityBackPageAdapterVO.setSize(vueElementAdminUserRoleEntityPage.getSize());
        vueElementAdminUserRoleEntityBackPageAdapterVO.setNumberOfElements(vueElementAdminUserRoleEntityPage.getNumberOfElements());
        vueElementAdminUserRoleEntityBackPageAdapterVO.setContent(vueElementAdminUserRoleEntityPage.getContent());
        return vueElementAdminUserRoleEntityBackPageAdapterVO;
    }

    @Override
    public long count() {
        return this.vueElementAdminUserRoleJpaRepository.count();
    }

    @Override
    public List<VueElementAdminUserRoleEntity> findAllByDelFlagAndStatus(Boolean delFlag, Boolean status) {
        return this.vueElementAdminUserRoleJpaRepository.findAllByDelFlagAndStatus(delFlag,status);
    }

    @Override
    public Optional<VueElementAdminUserRoleEntity> findById(Long id) {
        return this.vueElementAdminUserRoleJpaRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
          this.vueElementAdminUserRoleJpaRepository.deleteById(id);
    }

    @Override
    public Optional<VueElementAdminUserRoleEntity> findByIdAndStatus(Long id, Boolean status) {
        return this.vueElementAdminUserRoleJpaRepository.findByIdAndStatus(id,status);
    }

    @Override
    public Optional<VueElementAdminUserRoleEntity> findByRoleNameAndStatusAndDelFlag(String roleName, Boolean status, Boolean delFlag) {
        return this.vueElementAdminUserRoleJpaRepository.findByRoleNameAndStatusAndDelFlag(roleName,status,delFlag);
    }
}
