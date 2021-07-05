package com.dexlace.auth;

import com.dexlace.common.annotation.EnableIMoodLettuceRedis;
import com.dexlace.common.annotation.IMoodCloudApplication;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/30
 */
@EnableDiscoveryClient
@SpringBootApplication
//@EnableIMoodAuthExceptionHandler  // 认证类型异常翻译
//@EnableIMoodOauth2FeignClient // 开启带令牌的Feign请求，避免微服务内部调用出现401异常
//@EnableIMoodServerProtect  //开启微服务防护，避免客户端绕过网关直接请求微服务；
@IMoodCloudApplication
@EnableIMoodLettuceRedis
@MapperScan("com.dexlace.auth.mapper")
public class IMoodAuthApplication {
    public static void main(String[] args) {
        SpringApplication.run(IMoodAuthApplication.class,args);
    }
}
