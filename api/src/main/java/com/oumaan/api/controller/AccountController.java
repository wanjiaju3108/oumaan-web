package com.oumaan.api.controller;

import com.oumaan.api.common.ResponseVO;
import com.oumaan.api.enums.BusinessExceptionCodeEnum;
import com.oumaan.api.exception.BusinessException;
import com.oumaan.api.request.foreground.AddUserRequest;
import com.oumaan.api.request.foreground.LoginRequest;
import com.oumaan.api.utils.ThreadLocalUtil;
import com.oumaan.api.utils.TokenUtil;
import com.oumaan.service.foreground.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wjj
 * @Date: 2023/12/14
 */
@CrossOrigin
@RestController
@RequestMapping("account")
public class AccountController {
    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping("signup")
    public ResponseVO<Boolean> signup(@Validated @RequestBody AddUserRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        if (userService.addUser(username, password) == 0) {
            BusinessExceptionCodeEnum codeEnum = BusinessExceptionCodeEnum.USERNAME_REGISTERED;
            throw new BusinessException(codeEnum.getCode(), applicationContext.getMessage(codeEnum.getKey(), null, ThreadLocalUtil.getLocaleThreadLocal().get()));
        }
        return ResponseVO.success(null);
    }

    @PostMapping("login")
    public ResponseVO<String> login(@Validated @RequestBody LoginRequest request) {
        String username = request.getUsername();
        String password = request.getPassword();
        if (!userService.verifyPassword(username, password)) {
            BusinessExceptionCodeEnum codeEnum = BusinessExceptionCodeEnum.LOGIN_ERROR;
            throw new BusinessException(codeEnum.getCode(), applicationContext.getMessage(codeEnum.getKey(), null, ThreadLocalUtil.getLocaleThreadLocal().get()));
        }
        return ResponseVO.success(TokenUtil.token(username));
    }
}
