package com.xingyun.vueelementadminjavaapi.framework.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingyun.vueelementadminjavaapi.business.admin.model.entity.VueElementAdminUserEntity;
import com.xingyun.vueelementadminjavaapi.business.admin.service.VueElementAdminUserService;
import com.xingyun.vueelementadminjavaapi.framework.model.vo.VueElementAdminResponse;
import com.xingyun.vueelementadminjavaapi.framework.util.SmartStringUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.web.filter.OncePerRequestFilter;
import org.thymeleaf.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author qing-feng.zhao
 */
@Slf4j
public class AuthenticationTokenFilter extends OncePerRequestFilter {

    @Getter
    @Setter
    private List<String> whiteWebSiteList;

    @Getter
    @Setter
    private Map<String,Boolean> whiteWebSiteMap;

    @Autowired
    VueElementAdminUserService vueElementAdminUserService;

    @Autowired
    private ServerProperties serverProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        VueElementAdminResponse vueElementAdminResponse=new VueElementAdminResponse();
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");

        String remoteHost=httpServletRequest.getRemoteHost();
        String remoteIpAddress=httpServletRequest.getRemoteAddr();
        String requestUri=httpServletRequest.getRequestURI();
        StringBuffer requestUrl=httpServletRequest.getRequestURL();
        //获取Token
        String token = httpServletRequest.getHeader("X-Token");
        log.info("remoteHost={}",remoteHost);
        log.info("remoteIpAddress={}",remoteIpAddress);
        log.info("requestUri={}",requestUri);
        log.info("requestURL={}",requestUrl);
        log.info("token={}",token);
        if(null!=serverProperties.getServlet().getContextPath()){
            //去掉/dev-api/vue-element-admin/user/login 中的 /dev-api
            requestUri=requestUri.replace(serverProperties.getServlet().getContextPath(),"");
            log.info("去掉上下文后请求地址:{}",requestUri);
        }
        log.info("检查是否在白名单请求Uri={}",requestUri);
        //精确匹配
        if(this.whiteWebSiteList.contains(requestUri)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            log.info("该请求在白名单");
            return ;
        }
        //模糊匹配
        for (Map.Entry<String,Boolean> entrySet:this.whiteWebSiteMap.entrySet()){
            String key=entrySet.getKey();
            if(requestUri.startsWith(key)){
                filterChain.doFilter(httpServletRequest,httpServletResponse);
                return ;
            }
        }
        token=SmartStringUtils.trimToNull(token);
        if(StringUtils.isEmpty(token)){
            log.info("token={}",token);
            vueElementAdminResponse.setCode(50008);
            vueElementAdminResponse.setMessage("Token不可为空");
            vueElementAdminResponse.setData(null);
            ObjectMapper objectMapper=new ObjectMapper();
            String response=objectMapper.writeValueAsString(vueElementAdminResponse);
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(response);
            return ;
        }
        Optional<VueElementAdminUserEntity> vueElementAdminUserEntityOptional=vueElementAdminUserService.findVueElementAdminUserByToken(token);
        if(vueElementAdminUserEntityOptional.isPresent()){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return ;
        }else{
            vueElementAdminResponse.setCode(50014);
            vueElementAdminResponse.setMessage("无效的Token");
            vueElementAdminResponse.setData(null);
            ObjectMapper objectMapper=new ObjectMapper();
            String response=objectMapper.writeValueAsString(vueElementAdminResponse);
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(response);
            return ;
        }
    }
}
