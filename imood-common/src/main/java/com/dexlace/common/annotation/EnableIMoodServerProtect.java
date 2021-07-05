package com.dexlace.common.annotation;


import com.dexlace.common.config.IMoodServerProtectConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(IMoodServerProtectConfig.class)
public @interface EnableIMoodServerProtect {
}
