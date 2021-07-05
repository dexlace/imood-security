package com.dexlace.test;

import com.dexlace.common.annotation.EnableIMoodAuthExceptionHandler;
import com.dexlace.common.annotation.EnableIMoodOauth2FeignClient;
import com.dexlace.common.annotation.EnableIMoodServerProtect;
import com.dexlace.common.annotation.IMoodCloudApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/30
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(exclude = RedisAutoConfiguration.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)

//@EnableIMoodAuthExceptionHandler  // 认证类型异常翻译
//@EnableIMoodOauth2FeignClient // 开启带令牌的Feign请求，避免微服务内部调用出现401异常
//@EnableIMoodServerProtect  //开启微服务防护，避免客户端绕过网关直接请求微服务；
@IMoodCloudApplication
public class IMoodServerTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(IMoodServerTestApplication.class,args);
    }
}
