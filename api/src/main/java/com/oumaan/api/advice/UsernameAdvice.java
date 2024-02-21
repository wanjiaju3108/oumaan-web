package com.oumaan.api.advice;

import com.google.common.collect.Sets;
import com.oumaan.api.controller.UserController;
import com.oumaan.api.enums.BusinessExceptionCodeEnum;
import com.oumaan.api.exception.BusinessException;
import com.oumaan.api.request.foreground.UserRequestTO;
import com.oumaan.api.utils.TokenUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

/**
 * @Author: wjj
 * @Date: 2023/12/16
 * 用户信息注入
 */
@ControllerAdvice
public class UsernameAdvice extends RequestBodyAdviceAdapter {

    private final Set<Class<?>> userClassSet = Sets.newHashSet();
    private Set<Class<?>> managerClassSet = Sets.newHashSet();

    public UsernameAdvice() {
        userClassSet.add(UserController.class);
    }

    @Override
    public boolean supports(@NonNull MethodParameter methodParameter, @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        return userClassSet.contains(methodParameter.getDeclaringClass());
    }

    @NonNull
    @Override
    public Object afterBodyRead(@NonNull Object body, @NonNull HttpInputMessage inputMessage, @NonNull MethodParameter parameter, @NonNull Type targetType, @NonNull Class<? extends HttpMessageConverter<?>> converterType) {
        //username部分
        List<String> authorizationList = inputMessage.getHeaders().get("Authorization");
        if (CollectionUtils.isEmpty(authorizationList)) {
            throw new BusinessException(BusinessExceptionCodeEnum.UNAUTHORIZED.getCode());
        }
        String token = authorizationList.stream().findAny().orElse(null);
        String username = TokenUtil.getUsername(token);
        ((UserRequestTO) body).setUsername(username);
        return body;
    }
}
