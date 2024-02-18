package com.myrepo.rentacar.dto;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Set;


@Data
public class CreateRentalDetailsRequest {

    private String email;
    private String password;
    private String role;

    public boolean isValid() {
        return StringUtils.hasText(email) && StringUtils.hasText(password) && !role.isEmpty();
    }
}
