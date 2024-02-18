package com.myrepo.rentacar.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;


@Data
@RequiredArgsConstructor
public class SignUpRequest {

    private String email;
    private String password;
    private String role;

}
