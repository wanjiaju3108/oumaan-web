package com.oumaan.api.request.foreground;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.Length;

/**
 * @Author: wjj
 * @Date: 2023/12/15
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginRequest extends UserRequestTO {
    @Length(min = 8, max = 20, message = "{password.notBlank}")
    private String password;
}
