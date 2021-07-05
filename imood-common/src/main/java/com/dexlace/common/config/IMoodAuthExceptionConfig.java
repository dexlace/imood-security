package com.dexlace.common.config;

import com.dexlace.common.handler.IMoodAccessDeniedHandler;
import com.dexlace.common.handler.IMoodAuthExceptionEntryPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * @Author: xiaogongbing
 * @Description: @ConditionalOnMissingBean注解的意思是 当IOC容器中没有指定名称或类型的Bean的时候，就注册它
 * 这样做的好处在于，子系统可以自定义自个儿的资源服务器异常处理器
 * @Date: 2021/7/1
 */
public class IMoodAuthExceptionConfig {
    @Bean
    @ConditionalOnMissingBean(name = "accessDeniedHandler")
    public IMoodAccessDeniedHandler accessDeniedHandler() {
        return new IMoodAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = "authenticationEntryPoint")
    public IMoodAuthExceptionEntryPoint authenticationEntryPoint() {
        return new IMoodAuthExceptionEntryPoint();
    }
}
