package com.dexlace.common.annotation;


import com.dexlace.common.config.IMoodLettuceRedisConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(IMoodLettuceRedisConfig.class)
public @interface EnableIMoodLettuceRedis {
}
