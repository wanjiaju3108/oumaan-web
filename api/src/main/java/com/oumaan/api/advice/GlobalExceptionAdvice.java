package com.oumaan.api.advice;

import com.oumaan.api.common.ResponseVO;
import com.oumaan.api.enums.BusinessExceptionCodeEnum;
import com.oumaan.api.enums.HttpCodeEnum;
import com.oumaan.api.exception.BusinessException;
import com.oumaan.api.utils.ThreadLocalUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author: wjj
 * @Date: 2023/12/15
 * 对所有controller的异常统一处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @Autowired
    private ApplicationContext applicationContext;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseVO<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        if (bindingResult.getFieldError() != null && bindingResult.getFieldError().getDefaultMessage() != null) {
            return ResponseVO.failed(bindingResult.getFieldError().getDefaultMessage());
        }
        return ResponseVO.failed(HttpCodeEnum.BAD_REQUEST.getContent());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseVO<Void> handleBusinessException(BusinessException e) {
        //未登录处理
        if (e.getErrorCode() == BusinessExceptionCodeEnum.UNAUTHORIZED.getCode()) {
            BusinessExceptionCodeEnum businessExceptionCodeEnum = BusinessExceptionCodeEnum.UNAUTHORIZED;
            return ResponseVO.create(HttpCodeEnum.UNAUTHORIZED.getCode(), applicationContext.getMessage(businessExceptionCodeEnum.getKey(), null, ThreadLocalUtil.getLocaleThreadLocal().get()));
        }
        return ResponseVO.failed(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseVO<Void> handleException(Exception e) {
        log.error("", e);
        return ResponseVO.failed(e.getMessage());
    }
}
