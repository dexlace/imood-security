package com.dexlace.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/6/30
 */
@EnableEurekaServer
@SpringBootApplication(exclude = RedisAutoConfiguration.class)
public class IMoodRegisterApplication {
    public static void main(String[] args) {
        SpringApplication.run(IMoodRegisterApplication.class, args);
    }
}
