package com.dexlace.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/7/4
 */
@EnableAdminServer
@SpringBootApplication
public class IMoodMonitorAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(IMoodMonitorAdminApplication.class, args);
    }
}
