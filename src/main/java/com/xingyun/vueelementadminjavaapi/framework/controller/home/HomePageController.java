package com.xingyun.vueelementadminjavaapi.framework.controller.home;

import com.xingyun.vueelementadminjavaapi.framework.model.constant.GlobalConstantValues;
import com.xingyun.vueelementadminjavaapi.framework.model.properties.MyAppProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/***
 * @author qingfeng.zhao
 * @date 2022/7/23
 * @apiNote
 */
@Controller
public class HomePageController {
    private final MyAppProperties myAppProperties;
    public HomePageController(MyAppProperties myAppProperties) {
        this.myAppProperties = myAppProperties;
    }

    @GetMapping(value={"/","/index.do"})
    public String homePage(Model model){
        model.addAttribute("buildDateTime", null== GlobalConstantValues.buildDateTime?"暂未设置":GlobalConstantValues.buildDateTime);
        model.addAttribute("serviceCnName",this.myAppProperties.getServiceCnName());
        model.addAttribute("serviceEnName",this.myAppProperties.getServiceEnName());
        return "index";
    }
}
