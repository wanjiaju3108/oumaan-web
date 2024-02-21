package com.oumaan.data.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: wjj
 * @Date: 2023/12/27
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("page_conf")
public class PageConfEntity extends BaseEntity {
    private String name;
    private Integer imgId;
}
