package com.myrepo.rentacar.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class SignInRequest {

    private String email;
    private String password;
    private String secret;
}
