package com.oumaan.api.request.foreground;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @Author: wjj
 * @Date: 2023/12/16
 */
@Data
public class UserRequestTO {
    @Length(min = 4, max = 20, message = "{username.notBlank}")
    private String username;
}
