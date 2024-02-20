package com.myrepo.rentacar.dto;

import lombok.Data;
import org.springframework.util.StringUtils;

@Data
public class CreateCustomerRequest {

    private String email;
    private String password;
    private String fullName;

    public boolean isValid() {
        return (StringUtils.hasText(email)
                && StringUtils.hasText(password)
                && StringUtils.hasText(fullName)
        );
    }
}
