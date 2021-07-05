package com.dexlace.auth.config;

import com.dexlace.auth.filter.ValidateCodeFilter;
import com.dexlace.auth.service.IMoodUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/30
 * 用于处理/oauth开头的请求，Spring Cloud OAuth内部定义的获取令牌，
 * 刷新令牌的请求地址都是以/oauth/开头的，也就是说IMoodSecurityConfigure用于处理和令牌相关的请求；
 * 因为当我们引入了spring-cloud-starter-oauth2依赖后，系统会暴露一组由/oauth开头的端点，这些端点用于处理令牌相关请求
 */
@Order(2)
@EnableWebSecurity  // 开启了和Web相关的安全配置
public class IMoodSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private IMoodUserDetailService userDetailService;

    /**
     * 加密的方法
     *
     * @return 加密编码器
     */
    @Resource
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ValidateCodeFilter validateCodeFilter;

    /**
     * 注册了一个authenticationManagerBean，因为密码模式需要使用到这个Bean
     *
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * 该validateCodeFilter 需要在UsernamePasswordAuthenticationFilter之前
         */
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .requestMatchers()
                .antMatchers("/oauth/**")  // 只对/oauth/开头的请求有效
                .and()
                .authorizeRequests()
                .antMatchers("/oauth/**").authenticated()
                .and()
                .csrf().disable();
    }

    /**
     * 最后我们重写了configure(AuthenticationManagerBuilder auth)方法，指定了userDetailsService和passwordEncoder。
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder);
    }

}
