package com.dexlace.auth.properties;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author: xiaogongbing
 * @Description: 认证属性，认证的客户端及access_token,refresh_token的有效时间
 * @Date: 2021/6/30
 */
@Data
@SpringBootConfiguration
@PropertySource(value = {"classpath:imood-auth.properties"})
@ConfigurationProperties(prefix = "imood.auth")
public class IMoodAuthProperties {
    // 认证多个服务器
    private IMoodClientsProperties[] clients = {};
    // accessTokenValiditySeconds用于指定access_token的有效时间
    private int accessTokenValiditySeconds = 60 * 60 * 24;
    // refreshTokenValiditySeconds用于指定refresh_token的有效时间
    private int refreshTokenValiditySeconds = 60 * 60 * 24 * 7;

    // 免认证路径
    private String anonUrl;

    // 验证码配置类
    private IMoodValidateCodeProperties code=new IMoodValidateCodeProperties();
}
