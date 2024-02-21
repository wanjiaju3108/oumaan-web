package com.oumaan.api.enums;

import lombok.Getter;

/**
 * @Author: wjj
 * @Date: 2023/12/15
 */
@Getter
public enum HttpCodeEnum {

    SUCCESS(200, "请求成功"),
    UNKNOWN(400, "未知错误"),
    BAD_REQUEST(400, "参数错误"),
    UNAUTHORIZED(401, "未登录/登录已过期"),
    ;

    private final Integer code;

    private final String content;

    HttpCodeEnum(Integer code, String content) {
        this.code = code;
        this.content = content;
    }
}
