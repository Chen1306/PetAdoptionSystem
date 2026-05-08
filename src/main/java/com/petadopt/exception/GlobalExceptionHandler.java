package com.petadopt.exception;

import com.petadopt.common.ApiResponse;
import com.petadopt.common.BusinessException;
import com.petadopt.common.ResultCode;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Void> handleBusiness(BusinessException e) {
        return ApiResponse.fail(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Void> handleValidation(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldError() == null
                ? "validation failed"
                : e.getBindingResult().getFieldError().getDefaultMessage();
        return ApiResponse.fail(ResultCode.BAD_REQUEST, message);
    }

    @ExceptionHandler(BindException.class)
    public ApiResponse<Void> handleBind(BindException e) {
        String message = e.getBindingResult().getFieldError() == null
                ? "bind failed"
                : e.getBindingResult().getFieldError().getDefaultMessage();
        return ApiResponse.fail(ResultCode.BAD_REQUEST, message);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleOther(Exception e) {
        return ApiResponse.fail(ResultCode.SERVER_ERROR, e.getMessage());
    }
}
