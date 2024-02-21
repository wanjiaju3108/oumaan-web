package com.oumaan.api.request.background;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: wjj
 * @Date: 2023/12/27
 */
@Data
public class AddSubPageRequest {
    private Integer pageId;
    private String name;
}
