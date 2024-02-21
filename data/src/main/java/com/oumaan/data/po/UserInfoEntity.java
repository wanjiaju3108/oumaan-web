package com.oumaan.data.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: wjj
 * @Date: 2023/12/14
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("user_info")
public class UserInfoEntity extends BaseEntity {
    private String username;
    private String password;
}
