package com.dexlace.gateway.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/7/5
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:imood-gateway.properties"})
@ConfigurationProperties(prefix = "imood.gateway")
public class IMoodGatewayProperties {

    /**
     * 禁止外部访问的 URI，多个值的话以逗号分隔
     */
    private String forbidRequestUri;
}
