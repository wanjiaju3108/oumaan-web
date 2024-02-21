package com.oumaan.data.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: wjj
 * @Date: 2024/1/5
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("web_cfg")
public class WebCfgEntity extends BaseEntity {
    private Integer titleImg;
}
