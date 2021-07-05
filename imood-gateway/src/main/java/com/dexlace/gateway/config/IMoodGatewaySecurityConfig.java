package com.dexlace.gateway.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @Author: xiaogongbing
 * @Description: 因为imood-gateway引入了imood-common模块，
 * imood-common模块包含了Spring Cloud Security依赖，
 * 所以我们需要定义一个自己的WebSecurity配置类，来覆盖默认的。
 * 这里主要是关闭了csrf功能，否则会报csrf相关异常。
 * @Date: 2021/6/30
 */
@EnableWebSecurity
public class IMoodGatewaySecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests().antMatchers("/actuator/**").permitAll();
    }
}
