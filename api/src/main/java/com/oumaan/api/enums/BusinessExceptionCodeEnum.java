package com.oumaan.api.enums;

import lombok.Getter;

/**
 * @Author: wjj
 * @Date: 2023/12/16
 */
@Getter
public enum BusinessExceptionCodeEnum {
    UNAUTHORIZED("unauthorized", 10000),
    USERNAME_REGISTERED("username.registered", 10001),
    LOGIN_ERROR("login.error", 10002),
    ;

    private final String key;
    private final int code;

    BusinessExceptionCodeEnum(String key, int code) {
        this.key = key;
        this.code = code;
    }
}
