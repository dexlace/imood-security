package com.dexlace.auth.properties;

import lombok.Data;

/**
 * @Author: xiaogongbing
 * @Description: 客户端属性
 * @Date: 2021/6/30
 * client对应client_id，
 * secret对应client_secret，
 * grantType对应当前令牌支持的认证类型，
 * scope对应认证范围。
 * grantType和scope包含默认值。
 */
@Data
public class IMoodClientsProperties {
    private String client;
    private String secret;
    private String grantType = "password,authorization_code,refresh_token";
    private String scope = "all";
}
