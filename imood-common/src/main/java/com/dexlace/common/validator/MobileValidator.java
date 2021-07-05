package com.dexlace.common.validator;

import ch.qos.logback.classic.pattern.Util;
import com.dexlace.common.annotation.IsMobile;
import com.dexlace.common.constant.RegexpConstant;
import com.dexlace.common.utils.IMoodUtil;
import org.apache.commons.lang3.StringUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/7/4
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String> {

    @Override
    public void initialize(IsMobile isMobile) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StringUtils.isBlank(s)) {
                return true;
            } else {
                String regex = RegexpConstant.MOBILE_REG;
                return IMoodUtil.match(regex, s);
            }
        } catch (Exception e) {
            return false;
        }
    }
}
