package com.dexlace.common.exception;

/**
 * @Author: xiaogongbing
 * @Description:
 * @Date: 2021/7/1
 */
public class ValidateCodeException extends Exception{

    private static final long serialVersionUID = 7514854456967620043L;

    public ValidateCodeException(String message){
        super(message);
    }
}