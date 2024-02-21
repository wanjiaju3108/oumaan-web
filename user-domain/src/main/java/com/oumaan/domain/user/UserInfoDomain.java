package com.oumaan.domain.user;

import com.oumaan.data.dao.UserInfoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: wjj
 * @Date: 2023/12/14
 */
@Service
public class UserInfoDomain {
    @Autowired
    private UserInfoDao userInfoDao;

    public Integer addUser(String username, String password) {
        return userInfoDao.addOne(username, password);
    }

    public Boolean verifyPassword(String username, String password) {
        return userInfoDao.findByUsernamePassword(username, password) == 1;
    }

}
