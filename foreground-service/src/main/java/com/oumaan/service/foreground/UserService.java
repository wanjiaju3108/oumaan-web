package com.oumaan.service.foreground;

import com.oumaan.domain.user.UserInfoDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: wjj
 * @Date: 2023/12/14
 */
@Service
public class UserService {
    @Autowired
    private UserInfoDomain userInfoDomain;

    public Integer addUser(String username, String password) {
        return userInfoDomain.addUser(username, password);
    }

    public Boolean verifyPassword(String username, String password) {
        return userInfoDomain.verifyPassword(username, password);
    }
}
