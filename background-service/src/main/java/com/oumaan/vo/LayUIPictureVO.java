package com.oumaan.vo;

import lombok.Data;

/**
 * @Author: wjj
 * @Date: 2024/1/6
 */
@Data
public class LayUIPictureVO {
    private Integer code;
    private String msg;
    private LayUIData data;

    @Data
    public static class LayUIData {
        private String src;
        private String title;
    }
}
