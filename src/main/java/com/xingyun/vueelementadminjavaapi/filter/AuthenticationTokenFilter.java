package com.xingyun.vueelementadminjavaapi.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xingyun.vueelementadminjavaapi.business.admin.model.VueElementAdminUserEntity;
import com.xingyun.vueelementadminjavaapi.business.admin.service.VueElementAdminUserService;
import com.xingyun.vueelementadminjavaapi.model.VueElementAdminResponse;
import com.xingyun.vueelementadminjavaapi.util.SmartStringUtils;
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
import java.nio.charset.Charset;
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
    private VueElementAdminResponse vueElementAdminResponse;

    @Autowired
    private ServerProperties serverProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
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
        requestUri=requestUri.replace(serverProperties.getServlet().getContextPath(),"");
        log.info("检查是否在白名单请求Uri={}",requestUri);
        //精确匹配
        if(this.whiteWebSiteList.contains(requestUri)){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
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
            this.vueElementAdminResponse.setCode(50008);
            this.vueElementAdminResponse.setMessage("Token不可为空");
            this.vueElementAdminResponse.setData(null);
            ObjectMapper objectMapper=new ObjectMapper();
            String response=objectMapper.writeValueAsString(this.vueElementAdminResponse);
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(response);
            return ;
        }
        Optional<VueElementAdminUserEntity> vueElementAdminUserEntityOptional=vueElementAdminUserService.findVueElementAdminUserByToken(token);
        if(vueElementAdminUserEntityOptional.isPresent()){
            filterChain.doFilter(httpServletRequest,httpServletResponse);
            return ;
        }else{
            this.vueElementAdminResponse.setCode(50014);
            this.vueElementAdminResponse.setMessage("无效的Token");
            this.vueElementAdminResponse.setData(null);
            ObjectMapper objectMapper=new ObjectMapper();
            String response=objectMapper.writeValueAsString(this.vueElementAdminResponse);
            httpServletResponse.setContentType("application/json;charset=UTF-8");
            httpServletResponse.getWriter().write(response);
            return ;
        }
    }
}
