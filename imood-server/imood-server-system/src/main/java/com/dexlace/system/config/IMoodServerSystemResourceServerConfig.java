package com.dexlace.system.config;

import com.dexlace.common.handler.IMoodAccessDeniedHandler;
import com.dexlace.common.handler.IMoodAuthExceptionEntryPoint;
import com.dexlace.system.properties.IMoodServerSystemProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import javax.annotation.Resource;

/**
 * @Author: xiaogongbing
 * @Description: 所有访问imood-server-system的请求都需要认证，只有通过认证服务器发放的令牌才能进行访问
 * @Date: 2021/6/30
 */
@Configuration
@EnableResourceServer

public class IMoodServerSystemResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Resource
    private IMoodAccessDeniedHandler accessDeniedHandler;

    @Resource
    private IMoodAuthExceptionEntryPoint exceptionEntryPoint;

    @Autowired
    private IMoodServerSystemProperties properties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(properties.getAnonUrl(), ",");
        http.csrf().disable()
                // 表明该安全配置对所有请求都生效
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers(anonUrls).permitAll()
                .antMatchers("/**").authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(exceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }
}
