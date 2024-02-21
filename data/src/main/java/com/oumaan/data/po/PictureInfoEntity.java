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
@TableName("picture_info")
public class PictureInfoEntity extends BaseEntity {
    private String filename;
}
