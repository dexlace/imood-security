package com.dexlace.auth.config;

import com.dexlace.auth.properties.IMoodAuthProperties;
import com.dexlace.auth.properties.IMoodClientsProperties;
import com.dexlace.auth.service.IMoodUserDetailService;
import com.dexlace.auth.translator.IMoodWebResponseExceptionTranslator;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @Author: xiaogongbing
 * @Description: 一个和认证服务器相关的安全配置类
 * 认证服务器相关配置
 * @Date: 2021/6/30
 */
@Configuration
@EnableAuthorizationServer
public class IMoodAuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @Autowired
    private IMoodUserDetailService userDetailService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 要认证的属性，包括客户端，token的有效时间，refresh_token的有效时间
     */
    @Autowired
    private IMoodAuthProperties authProperties;

    /**
     * 异常翻译器的bean注入
     */
    @Autowired
    private IMoodWebResponseExceptionTranslator exceptionTranslator;

    /**
     * 这就是客户端配置
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                // 客户端从认证服务器获取令牌的时候，必须使用client_id为imood，client_secret为123456的标识来获取；
//                .withClient("imood")
//                .secret(passwordEncoder.encode("123456"))
//                // 该client_id支持password模式获取令牌，并且可以通过refresh_token来获取新的令牌
//                .authorizedGrantTypes("password", "refresh_token")
//                // 在获取client_id为febs的令牌的时候，scope只能指定为all，否则将获取失败；
//                .scopes("all");
//
//        // 如果需要指定多个client，可以继续使用withClient配置。

        IMoodClientsProperties[] authClients = authProperties.getClients();
        // 内存
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if (ArrayUtils.isNotEmpty(authClients)) {
            for (IMoodClientsProperties client : authClients) {
                if (StringUtils.isBlank(client.getClient())) {
                    throw new Exception("client不能为空");
                }
                if (StringUtils.isBlank(client.getSecret())) {
                    throw new Exception("secret不能为空");
                }

                // 认证类型以逗号分割，得到认证类型的数组
                String[] grantTypes = StringUtils.splitByWholeSeparatorPreserveAllTokens(client.getGrantType(), ",");
                builder.withClient(client.getClient())
                        .secret(passwordEncoder.encode(client.getSecret()))
                        .authorizedGrantTypes(grantTypes)
                        .scopes(client.getScope());
            }


        }
    }

    /**
     * @param endpoints
     */
    @SuppressWarnings("all")
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.tokenStore(tokenStore())
                .userDetailsService(userDetailService)
                .authenticationManager(authenticationManager)
                .tokenServices(defaultTokenServices())
                .exceptionTranslator(exceptionTranslator);
    }


    /**
     * tokenStore使用的是RedisTokenStore
     *
     * @return
     */
    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * 这是token配置
     *
     * @return
     */
    @Primary
    @Bean
    public DefaultTokenServices defaultTokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore());
        // setSupportRefreshToken设置为true表示开启刷新令牌的支持。
        tokenServices.setSupportRefreshToken(true);
        // 令牌有效时间为60 * 60 * 24秒
//            tokenServices.setAccessTokenValiditySeconds(60 * 60 * 24);
        tokenServices.setAccessTokenValiditySeconds(authProperties.getAccessTokenValiditySeconds());
        // 刷新令牌有效时间为60 * 60 * 24 * 7秒
//            tokenServices.setRefreshTokenValiditySeconds(60 * 60 * 24 * 7);
        tokenServices.setRefreshTokenValiditySeconds(authProperties.getRefreshTokenValiditySeconds());
        return tokenServices;
    }
}
