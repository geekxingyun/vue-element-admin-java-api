package com.xingyun.vueelementadminjavaapi.framework.config;

import com.xingyun.vueelementadminjavaapi.framework.model.properties.MySpringSecurityAccountProperties;
import com.xingyun.vueelementadminjavaapi.framework.model.enumvalue.MySpringSecurityRoleEnum;
import com.xingyun.vueelementadminjavaapi.framework.util.SmartStringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

/**
 * 自定义身份验证管理器
 * @author qing-feng.zhao
 */
@Slf4j
@EnableWebSecurity
@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MySpringSecurityAccountProperties mySpringSecurityAccountProperties;
    /**
     * config password encoder
     * passwordEncoder
     * @return
     */
    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * spring security global config
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // config cors
                .cors().configurationSource(corsConfigurationSource())
                .and()
                //config allow include frame page
                .headers().frameOptions().sameOrigin()
                .and()
                //Session manage
                //ALWAYS:Always create an HttpSession
                //NEVER:Spring Security will never create an HttpSession, but will use the HttpSession if it already exists
                //IF_REQUIRED:Spring Security will only create an  HttpSession if required
                //STATELESS:Spring Security will never create an HttpSession and it will never use it to obtain the SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
                .and()
                .authorizeRequests()
                //API文档 需要授权
                .antMatchers("/swagger-ui/index.html","/swagger-ui/**","/swagger-resources/**","/v3/**").hasRole(MySpringSecurityRoleEnum.VISIT_SWAGGER_API_DOC_ROLE.getRoleName())
                // 健康检查基础不需要授权
                .antMatchers("/actuator/health").permitAll()
                // 健康检查其他详情需要授权
                .antMatchers("/actuator/**").hasAnyRole(MySpringSecurityRoleEnum.VISIT_ACTUATOR_HEALTH_CHECK_DETAIL_ROLE.getRoleName())
                //测试数据库控制台
               .antMatchers("/h2-console/**").hasRole(MySpringSecurityRoleEnum.VISIT_H2_DB_ROLE.getRoleName())
                // 除上面外的所有请求需要认证,其他全部放行由filter进行权限拦截
                .anyRequest().permitAll()
                .and()
                //使用默认的表单登陆认证方式 支持表单登陆 openid oauth2.0 saml2等多种认证方式
                .formLogin()
                //第一次如果拦截到非法请求,默认重定向到 /login 请求去处理
                //默认值/login 如果不配置会默认拦截/login请求并自动生成一个登陆页面 如果配置会指向自定义的登陆页面
                .loginPage("/toLoginPage.do")
                //执行登陆的请求处理 如果不配置会拦截/login 交给Spring Security 默认处理
                .loginProcessingUrl("/login")
                //要允许匿名访问
                .permitAll()
                .and()
                //注销登录
                .logout()
                //注销成功后返回首页
                .logoutSuccessUrl("/")
                //清除认证
                .clearAuthentication(true)
                //不拦截
                .permitAll()
                .and()
                //跨站请求伪造（CSRF）
                //这里需要将csrf 禁用否则会报不能跨域的错误
                .csrf().disable();
                //自定义过滤器
                //.addFilterAfter(null,UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * AuthenticationManagerBuilder 它非常适合设置内存中的JDBC或LDAP用户详细信息
     * 或添加自定义UserDetailsService。
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //使用密码类型
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        String[] allowHeaders={
                "x-auth-token",
                "Content-Type",
                "X-Requested-With",
                "XMLHttpRequest",
                "X-Content-Type-Options",
                "X-XSS-Protection",
                "X-Frame-Options",
                "X-Content-Type-Options",
                "Content-Language",
                "Cache-Control",
                "Connection"
        };
        CorsConfiguration configuration = new CorsConfiguration();
        //设置同源策略 *
        configuration.setAllowedOrigins(Arrays.asList(CorsConfiguration.ALL));
        configuration.setAllowedHeaders(Arrays.asList(allowHeaders));
        configuration.setExposedHeaders(Arrays.asList(allowHeaders));
        //设置允许的方法
        configuration.setAllowedMethods(Arrays.asList(CorsConfiguration.ALL));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //拦截所有的请求
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        //内存中保存登录信息
        return inMemoryUserDetailsManager();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager(){
        String currentConfigSwaggerPassword= SmartStringUtils.trimToNull(mySpringSecurityAccountProperties.getSwaggerPassword());
        if(null==currentConfigSwaggerPassword){
            currentConfigSwaggerPassword=SmartStringUtils.getCustomizedDigitsUuid(16);
            log.warn("\r\nhave no config swagger user password,create default password:{}",currentConfigSwaggerPassword);
        }
        String currentConfigH2Password=SmartStringUtils.trimToNull(mySpringSecurityAccountProperties.getH2Password());
        if(null==currentConfigH2Password){
            currentConfigH2Password= SmartStringUtils.getCustomizedDigitsUuid(16);
            log.warn("\r\nhave no config h2 user password,create default password:{}",currentConfigH2Password);
        }
        UserDetails swaggerUserDetails =
                User.builder()
                        //账号
                        .username(mySpringSecurityAccountProperties.getSwaggerAccount())
                        //密码
                        .password(passwordEncoder().encode(currentConfigSwaggerPassword))
                        //角色
                        .roles(MySpringSecurityRoleEnum.VISIT_SWAGGER_API_DOC_ROLE.getRoleName())
                        //账户是否过期
                        .accountExpired(false)
                        //是否锁定账户
                        .accountLocked(false)
                        //是否禁用该用户
                        .disabled(false)
                        //登录凭据已过期
                        .credentialsExpired(false)
                        .build();
        UserDetails h2UserDetails =
                User.builder()
                        //账号
                        .username(mySpringSecurityAccountProperties.getH2Account())
                        //密码
                        .password(passwordEncoder().encode(currentConfigSwaggerPassword))
                        //角色
                        .roles(MySpringSecurityRoleEnum.VISIT_H2_DB_ROLE.getRoleName())
                        //账户是否过期
                        .accountExpired(false)
                        //是否锁定账户
                        .accountLocked(false)
                        //是否禁用该用户
                        .disabled(false)
                        //登录凭据已过期
                        .credentialsExpired(false)
                        .build();
        return new InMemoryUserDetailsManager(swaggerUserDetails,h2UserDetails);
    }
}

