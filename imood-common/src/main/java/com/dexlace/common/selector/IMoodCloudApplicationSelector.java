package com.dexlace.common.selector;

import com.dexlace.common.config.IMoodAuthExceptionConfig;
import com.dexlace.common.config.IMoodOAuth2FeignConfig;
import com.dexlace.common.config.IMoodServerProtectConfig;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/7/1
 */
public class IMoodCloudApplicationSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                IMoodAuthExceptionConfig.class.getName(),
                IMoodOAuth2FeignConfig.class.getName(),
                IMoodServerProtectConfig.class.getName()
        };
    }
}
