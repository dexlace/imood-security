package com.dexlace.auth.config;

import com.dexlace.auth.properties.IMoodAuthProperties;
import com.dexlace.common.handler.IMoodAccessDeniedHandler;
import com.dexlace.common.handler.IMoodAuthExceptionEntryPoint;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.annotation.Resource;

/**
 * @Author: xiaogongbing
 * @Description: 资源服务配置   注意  所以会有资源服务的错误处理的handler，自定义的
 * 资源服务器对所有请求都有效，但是其优先级是3，而WebSecurityConfigurerAdapter 的优先级是100，所以这个为优先过滤
 * 用于处理非/oauth/开头的请求，其主要用于资源的保护，客户端只能通过OAuth2协议发放的令牌来从资源服务器中获取受保护的资源。
 * @Date: 2021/6/30
 */
@Configuration
@EnableResourceServer // 用于开启资源服务器相关配置
public class IMoodResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Resource
    private IMoodAccessDeniedHandler accessDeniedHandler;

    @Resource
    private IMoodAuthExceptionEntryPoint exceptionEntryPoint;

    @Autowired
    private IMoodAuthProperties properties;


   @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(properties.getAnonUrl(), ",");
        http.csrf().disable()
                // 表明该安全配置对所有请求都生效
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers(anonUrls).permitAll()
                 .antMatchers("/actuator/**").permitAll()
                .antMatchers("/**").authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(exceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }

}
