package com.oumaan.api.advice;

import com.oumaan.api.utils.ThreadLocalUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Locale;

/**
 * @Author: wjj
 * @Date: 2023/12/16
 * 国际化处理
 */
@ControllerAdvice
public class LocaleAdvice extends RequestBodyAdviceAdapter {
    @Override
    public boolean supports(@NonNull MethodParameter methodParameter, @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @NonNull
    @Override
    public Object afterBodyRead(@NonNull Object body, @NonNull HttpInputMessage inputMessage, @NonNull MethodParameter parameter, @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        //国际化部分
        Locale locale = Locale.getDefault();
        List<Locale.LanguageRange> languageRangeList = inputMessage.getHeaders().getAcceptLanguage();
        if (CollectionUtils.isNotEmpty(languageRangeList)) {
            locale = Locale.lookup(languageRangeList, List.of(Locale.getAvailableLocales()));
        }
        ThreadLocalUtil.getLocaleThreadLocal().set(locale);
        return body;
    }
}
