package com.xingyun.vueelementadminjavaapi.framework.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qing-feng.zhao
 */
@Controller
public class SpringSecurityLoginController {
    /**
     * 自定义登陆页面
     * @return
     */
    @GetMapping(value = "/toLoginPage.do")
    public String loginPage(){
        return "login/customize-login";
    }
}
