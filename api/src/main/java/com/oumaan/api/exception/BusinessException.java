package com.oumaan.api.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: wjj
 * @Date: 2023/12/15
 * 业务异常
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 5782289874891655004L;

    private int errorCode = -1;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(int errorCode) {
        this.errorCode = errorCode;
    }

    public BusinessException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public BusinessException(int errorCode, String msg, Throwable cause) {
        super(msg, cause);
        this.errorCode = errorCode;
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

}
