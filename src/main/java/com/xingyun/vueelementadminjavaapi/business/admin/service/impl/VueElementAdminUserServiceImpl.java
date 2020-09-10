package com.xingyun.vueelementadminjavaapi.business.admin.service.impl;

import com.xingyun.vueelementadminjavaapi.business.admin.dao.jpa.VueElementAdminUserJpaRepository;
import com.xingyun.vueelementadminjavaapi.business.admin.model.VueElementAdminUserEntity;
import com.xingyun.vueelementadminjavaapi.business.admin.model.VueElementAdminUserLogin;
import com.xingyun.vueelementadminjavaapi.business.admin.model.VueElementAdminUserWebVO;
import com.xingyun.vueelementadminjavaapi.business.admin.model.VueWelcomeInfo;
import com.xingyun.vueelementadminjavaapi.business.admin.service.VueElementAdminUserService;
import com.xingyun.vueelementadminjavaapi.customized.MyAppProperties;
import com.xingyun.vueelementadminjavaapi.model.VueElementAdminResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author qing-feng.zhao
 */
@Service
public class VueElementAdminUserServiceImpl implements VueElementAdminUserService {

    private final MyAppProperties myAppProperties;
    private final VueElementAdminResponse vueElementAdminResponse;
    private final VueElementAdminUserJpaRepository vueElementAdminUserJpaRepository;
    public VueElementAdminUserServiceImpl(VueElementAdminUserJpaRepository vueElementAdminUserJpaRepository, VueElementAdminResponse vueElementAdminResponse, MyAppProperties myAppProperties) {
        this.vueElementAdminUserJpaRepository = vueElementAdminUserJpaRepository;
        this.vueElementAdminResponse = vueElementAdminResponse;
        this.myAppProperties = myAppProperties;
    }

    @Override
    public VueElementAdminResponse initVueElementAdmin() {
        long currentUserData=this.vueElementAdminUserJpaRepository.count();
        if(currentUserData<=0){
            VueElementAdminUserEntity vueElementAdminUserEntity=new VueElementAdminUserEntity();
            vueElementAdminUserEntity.setUsername(myAppProperties.getAdminUserName());
            vueElementAdminUserEntity.setPassword(myAppProperties.getAdminUserPassword());
            vueElementAdminUserEntity.setRoles("admin");
            vueElementAdminUserEntity.setIntroduction("I am a super administrator");
            vueElementAdminUserEntity.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            vueElementAdminUserEntity.setName("Super Admin");
            this.saveVueElementAdminUser(vueElementAdminUserEntity);

            VueElementAdminUserEntity vueElementAdminUserEditEntity=new VueElementAdminUserEntity();
            vueElementAdminUserEditEntity.setUsername(myAppProperties.getEditorUserName());
            vueElementAdminUserEditEntity.setPassword(myAppProperties.getEditorUserPassword());
            vueElementAdminUserEditEntity.setRoles("editor");
            vueElementAdminUserEditEntity.setIntroduction("I am an editor");
            vueElementAdminUserEditEntity.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            vueElementAdminUserEditEntity.setName("Normal Editor");
            this.saveVueElementAdminUser(vueElementAdminUserEditEntity);
            this.vueElementAdminResponse.setMessage("账号初始化完成");
            this.vueElementAdminResponse.setCode(20000);
            this.vueElementAdminResponse.setData(null);
        }else{
            this.vueElementAdminResponse.setMessage("账号已初始化");
            this.vueElementAdminResponse.setCode(20000);
            this.vueElementAdminResponse.setData(null);
        }
        return this.vueElementAdminResponse;
    }

    @Override
    public List<VueWelcomeInfo> initVueWelcomeInfoList() {
        List<VueWelcomeInfo> vueWelcomeInfoList=new ArrayList<>();
        VueWelcomeInfo vueWelcomeInfo;
        int initDataCount=20;
        for (int i = 0; i < initDataCount; i++) {
            vueWelcomeInfo=new VueWelcomeInfo();
            vueWelcomeInfo.setOrder_no("3BC7bD0E-E351-EcC2-eB73-b57c54cEfA4e");
            vueWelcomeInfo.setPrice(4501.78);
            vueWelcomeInfo.setTimestamp(262597322851L);
            vueWelcomeInfo.setStatus("pending");
            vueWelcomeInfo.setUsername("Sharon Robinson");
            vueWelcomeInfoList.add(vueWelcomeInfo);
        }
        return vueWelcomeInfoList;
    }

    @Override
    public Optional<VueElementAdminUserEntity> loginVueElementAdmin(VueElementAdminUserLogin vueElementAdminUserLogin) {
        VueElementAdminUserEntity vueElementAdminUserEntity=new VueElementAdminUserEntity();
        vueElementAdminUserEntity.setUsername(vueElementAdminUserLogin.getUsername());
        vueElementAdminUserEntity.setPassword(vueElementAdminUserLogin.getPassword());
        Example<VueElementAdminUserEntity> vueElementAdminUserEntityExample= Example.of(vueElementAdminUserEntity);
        return this.vueElementAdminUserJpaRepository.findOne(vueElementAdminUserEntityExample);
    }

    @Override
    public VueElementAdminUserEntity saveVueElementAdminUser(VueElementAdminUserEntity vueElementAdminUserEntity) {
        return this.vueElementAdminUserJpaRepository.save(vueElementAdminUserEntity);
    }

    @Override
    public Optional<VueElementAdminUserEntity> findVueElementAdminUserByToken(String token) {
        return this.vueElementAdminUserJpaRepository.findByToken(token);
    }

    @Override
    public VueElementAdminUserWebVO convertToWebVO(VueElementAdminUserEntity vueElementAdminUserEntity) {
        VueElementAdminUserWebVO vueElementAdminUserWebVO=new VueElementAdminUserWebVO();
        BeanUtils.copyProperties(vueElementAdminUserEntity,vueElementAdminUserWebVO);
        if(null!=vueElementAdminUserEntity.getRoles()){
            String[] roles=vueElementAdminUserEntity.getRoles().split(",");
            vueElementAdminUserWebVO.setRoles(roles);
        }
        return vueElementAdminUserWebVO;
    }
}
