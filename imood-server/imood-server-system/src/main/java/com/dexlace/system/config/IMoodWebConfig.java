package com.dexlace.system.config;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.dexlace.system.properties.IMoodServerSystemProperties;
import com.dexlace.system.properties.IMoodSwaggerProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @Author: xiaogongbing
 * @Description: 分页插件
 * @Date: 2021/7/2
 */
@Configuration
@EnableSwagger2  // 开启Swagger功能
public class IMoodWebConfig {

    @Autowired
    private IMoodServerSystemProperties properties;

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        sqlParserList.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParserList);
        return paginationInterceptor;
    }

    @Bean
    public Docket swaggerApi() {
        IMoodSwaggerProperties swagger = properties.getSwagger();

        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                // 表示将com.dexlace.system.controller下的controller都添加进去
                .apis(RequestHandlerSelectors.basePackage(swagger.getBasePackage()))
                // 表示Controller里的所有方法都纳入
                .paths(PathSelectors.any())
                .build()
                // 用于定义一些API页面信息，比如作者名称，邮箱，网站链接，开源协议等等。
                .apiInfo(apiInfo(swagger))
                .securitySchemes(Collections.singletonList(securityScheme(swagger)))
                .securityContexts(Collections.singletonList(securityContext(swagger)));
    }

    private ApiInfo apiInfo(IMoodSwaggerProperties swagger) {
        return new ApiInfo(
                swagger.getTitle(),
                swagger.getDescription(),
                swagger.getVersion(),
                null,
                new Contact(swagger.getAuthor(), swagger.getUrl(), swagger.getEmail()),
                swagger.getLicense(), swagger.getLicenseUrl(), Collections.emptyList());
    }


    /**
     * securitySchemes：用于配置安全策略，比如配置认证模型，scope等内容；
     * 我们通过OAuthBuilder对象构建了安全策略，主要配置了认证类型为ResourceOwnerPasswordCredentialsGrant（即密码模式），
     * 认证地址为http://localhost:8301/auth/oauth/token（即通过网关转发到认证服务器），scope为test，
     * 和imood-auth模块里定义的一致。这个安全策略我们将其命名为imood_oauth_swagger。
     * @param swagger
     * @return
     */
    private SecurityScheme securityScheme(IMoodSwaggerProperties swagger) {
        GrantType grantType = new ResourceOwnerPasswordCredentialsGrant("http://localhost:8301/auth/oauth/token");

        return new OAuthBuilder()
                .name("imood_oauth_swagger")
                .grantTypes(Collections.singletonList(grantType))
                .scopes(Arrays.asList(scopes(swagger)))
                .build();
    }


    /**
     * securityContexts：用于配置安全上下文，只有配置了安全上下文的接口才能使用令牌获取资源。
     * @param swagger
     * @return
     */
    private SecurityContext securityContext(IMoodSwaggerProperties swagger) {
        return SecurityContext.builder()
                .securityReferences(Collections.singletonList(new SecurityReference("imood_oauth_swagger", scopes(swagger))))
                .forPaths(PathSelectors.any())
                .build();
    }

    private AuthorizationScope[] scopes(IMoodSwaggerProperties swagger) {
        return new AuthorizationScope[]{
                new AuthorizationScope("test", "")
        };
    }

}
