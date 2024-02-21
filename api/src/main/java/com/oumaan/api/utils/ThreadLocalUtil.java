package com.oumaan.api.utils;

import lombok.Getter;

import java.util.Locale;

/**
 * @Author: wjj
 * @Date: 2023/12/18
 */
public class ThreadLocalUtil {
    @Getter
    private static ThreadLocal<Locale> localeThreadLocal = new ThreadLocal<>();
}
