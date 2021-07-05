package com.dexlace.common.annotation;

import com.dexlace.common.config.IMoodOAuth2FeignConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(IMoodOAuth2FeignConfig.class)
public @interface EnableIMoodOauth2FeignClient {
}
