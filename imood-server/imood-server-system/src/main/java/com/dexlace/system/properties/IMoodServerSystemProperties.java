package com.dexlace.system.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/7/4
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:imood-server-system.properties"})
@ConfigurationProperties(prefix = "imood.server.system")
public class IMoodServerSystemProperties {
        /**
         * 免认证 URI，多个值的话以逗号分隔
         */
        private String anonUrl;
        private IMoodSwaggerProperties swagger = new IMoodSwaggerProperties();

}
