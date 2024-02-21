package com.oumaan.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.oumaan.data.po.UserInfoEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: wjj
 * @Date: 2023/12/14
 */
@Mapper
public interface UserInfoMapper extends BaseMapper<UserInfoEntity> {
    Integer insertIgnore(@Param("entity") UserInfoEntity entity);
}
