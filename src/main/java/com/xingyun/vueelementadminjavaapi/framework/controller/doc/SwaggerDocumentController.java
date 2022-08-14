package com.xingyun.vueelementadminjavaapi.framework.controller.doc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qing-feng.zhao
 */
@Controller
public class SwaggerDocumentController {

    /**
     * 带注销登陆功能的Swagger API
     * @return
     */
    @GetMapping(value = "/read-api-doc-page.do")
    public String readApi(){
        return "/doc/swagger-api-doc";
    }
}
