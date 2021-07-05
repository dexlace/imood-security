package com.dexlace.common.annotation;

import com.dexlace.common.config.IMoodAuthExceptionConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/7/1
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(IMoodAuthExceptionConfig.class)
public @interface  EnableIMoodAuthExceptionHandler {
}
