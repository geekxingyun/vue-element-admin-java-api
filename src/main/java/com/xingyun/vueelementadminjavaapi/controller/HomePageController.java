package com.xingyun.vueelementadminjavaapi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qing-feng.zhao
 */
@Controller
public class HomePageController {

    @GetMapping(value = "/")
    public String homePage(){
        return "index";
    }
}
