package com.myrepo.rentacar.dto;

import lombok.Data;
import org.springframework.util.StringUtils;


@Data
public class CreateUserDetailsRequest {

    private String email;
    private String password;
    private String role;

    public boolean isValid() {
        return StringUtils.hasText(email) && StringUtils.hasText(password) && !role.isEmpty();
    }
}
