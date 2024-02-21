package com.oumaan.api.request.background;

import lombok.Data;

/**
 * @Author: wjj
 * @Date: 2024/1/7
 */
@Data
public class UpdateSubPageRequest {
    private Integer id;
    private String name;
    private String content;
}
