package com.myrepo.rentacar.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JWTAuthenticationResponse {

    private String token;
    private String refreshToken;

}
