package com.dexlace.common.config;

import com.dexlace.common.interceptor.IMoodServerProtectorInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: xiaogongbing
 * @Description:
 * 我们在该配置类里注册了ServerProtectInterceptor，并且将它加入到了Spring的拦截器链中。

 * 同样的，要让该配置类生效，我们可以定义一个@Enable注解来驱动它
 * @Date: 2021/7/1
 */
public class IMoodServerProtectConfig implements WebMvcConfigurer {
    @Bean
    @ConditionalOnMissingBean(value = PasswordEncoder.class)
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public HandlerInterceptor imoodServerProtectInterceptor() {
        return new IMoodServerProtectorInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(imoodServerProtectInterceptor());
    }

}
