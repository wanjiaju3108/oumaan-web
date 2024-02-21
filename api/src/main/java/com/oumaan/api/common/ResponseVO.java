package com.oumaan.api.common;

import com.oumaan.api.enums.HttpCodeEnum;
import com.oumaan.api.utils.ThreadLocalUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Author: wjj
 * @Date: 2023/12/10
 */
@Data
@AllArgsConstructor
public class ResponseVO<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> ResponseVO<T> success(T data) {
        ThreadLocalUtil.getLocaleThreadLocal().remove();
        return new ResponseVO<>(HttpCodeEnum.SUCCESS.getCode(), HttpCodeEnum.SUCCESS.getContent(), data);
    }

    public static <T> ResponseVO<T> failed(String errMsg) {
        ThreadLocalUtil.getLocaleThreadLocal().remove();
        return new ResponseVO<>(HttpCodeEnum.UNKNOWN.getCode(), errMsg, null);
    }

    public static <T> ResponseVO<T> create(Integer code, String errMsg) {
        ThreadLocalUtil.getLocaleThreadLocal().remove();
        return new ResponseVO<>(code, errMsg, null);
    }
}
