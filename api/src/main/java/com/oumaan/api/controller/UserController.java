package com.oumaan.api.controller;

import com.oumaan.api.enums.BusinessExceptionCodeEnum;
import com.oumaan.api.request.foreground.UserRequestTO;
import com.oumaan.api.common.ResponseVO;
import com.oumaan.api.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: wjj
 * @Date: 2023/12/17
 */
@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private ApplicationContext applicationContext;

    @PostMapping("test")
    public ResponseVO<String> test(@RequestBody UserRequestTO request) throws InterruptedException {
        return ResponseVO.success(applicationContext.getMessage(BusinessExceptionCodeEnum.LOGIN_ERROR.getKey(), null, ThreadLocalUtil.getLocaleThreadLocal().get()));
    }
}
