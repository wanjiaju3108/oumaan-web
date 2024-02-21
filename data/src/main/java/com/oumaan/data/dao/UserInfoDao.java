package com.oumaan.data.dao;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.oumaan.data.mapper.UserInfoMapper;
import com.oumaan.data.po.UserInfoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @Author: wjj
 * @Date: 2023/12/14
 */
@Repository
public class UserInfoDao {

    @Autowired
    private UserInfoMapper userInfoMapper;

    public Integer addOne(String username, String password) {
        UserInfoEntity entity = new UserInfoEntity();
        entity.setUsername(username);
        entity.setPassword(password);
        return userInfoMapper.insertIgnore(entity);
    }

    public Integer findByUsernamePassword(String username, String password) {
        return userInfoMapper.selectCount(new LambdaQueryWrapper<UserInfoEntity>()
                .eq(UserInfoEntity::getUsername, username)
                .eq(UserInfoEntity::getPassword, password)
                .eq(UserInfoEntity::getDel, false)).intValue();
    }
}
