package com.dexlace.common.config;

import com.dexlace.common.constant.IMoodConstant;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.util.Base64Utils;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/7/1
 */
public class IMoodOAuth2FeignConfig {

    /**
     * 我们通过SecurityContextHolder从请求上下文中获取了OAuth2AuthenticationDetails类型对象，
     * 并通过该对象获取到了请求令牌，然后在请求模板对象requestTemplate的头部手动将令牌添加上去。
     * @return
     */

    @Bean
    public RequestInterceptor oauth2FeignRequestInterceptor() {
        return requestTemplate -> {
            // 添加 Zuul Token
            String zuulToken = new String(Base64Utils.encode(IMoodConstant.ZUUL_TOKEN_VALUE.getBytes()));
            requestTemplate.header(IMoodConstant.ZUUL_TOKEN_HEADER, zuulToken);

            Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (details instanceof OAuth2AuthenticationDetails) {
                String authorizationToken = ((OAuth2AuthenticationDetails) details).getTokenValue();
                requestTemplate.header(HttpHeaders.AUTHORIZATION, "bearer " + authorizationToken);
            }
        };
    }
}
