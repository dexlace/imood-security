package com.dexlace.common.annotation;

import com.dexlace.common.selector.IMoodCloudApplicationSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(IMoodCloudApplicationSelector.class)
public @interface IMoodCloudApplication {
}
