package com.dexlace.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/30
 */
@EnableZuulProxy
@EnableDiscoveryClient
@SpringBootApplication(exclude = RedisAutoConfiguration.class)
public class IMoodGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(IMoodGatewayApplication.class, args);
    }
}
