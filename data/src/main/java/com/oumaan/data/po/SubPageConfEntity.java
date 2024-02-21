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
@TableName("sub_page_conf")
public class SubPageConfEntity extends BaseEntity {
    private Integer pageId;
    private String name;
    private String content;
}
