package com.dexlace.common.handler;

import com.dexlace.common.exception.IMoodAuthException;
import com.dexlace.common.exception.IMoodException;
import com.dexlace.common.vo.IMoodResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Set;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/7/1
 */
@Slf4j
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public IMoodResponse handleException(Exception e) {
        log.error("系统内部异常，异常信息", e);
        return new IMoodResponse().message("系统内部异常");
    }

    @ExceptionHandler(value = IMoodAuthException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public IMoodResponse handleIMoodAuthException(IMoodAuthException e) {
        log.error("系统错误", e);
        return new IMoodResponse().message(e.getMessage());
    }

    @ExceptionHandler(value = AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public IMoodResponse handleAccessDeniedException(){
        return new IMoodResponse().message("没有权限访问该资源");
    }


    @ExceptionHandler(value = IMoodException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public IMoodResponse handleFebsException(IMoodException e) {
        log.error("系统错误", e);
        return new IMoodResponse().message(e.getMessage());
    }


    /**
     * 统一处理请求参数校验(普通传参)
     *
     * @param e ConstraintViolationException
     * @return FebsResponse
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IMoodResponse handleConstraintViolationException(ConstraintViolationException e) {
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), ".");
            message.append(pathArr[1]).append(violation.getMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return new IMoodResponse().message(message.toString());
    }


    /**
     * 统一处理请求参数校验(实体对象传参)
     * 校验不通过
     * @param e BindException
     * @return FebsResponse
     */
    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public IMoodResponse handleBindException(BindException e) {
        StringBuilder message = new StringBuilder();
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return new IMoodResponse().message(message.toString());
    }
}