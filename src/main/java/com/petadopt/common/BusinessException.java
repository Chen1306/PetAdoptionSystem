package com.petadopt.common;

public class BusinessException extends RuntimeException {

    private final Integer code;
    /*说明异常原因*/

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
