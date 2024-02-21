package com.oumaan.data.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @Author: wjj
 * @Date: 2023/12/27
 */
@Data
public class BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Boolean del;
}
