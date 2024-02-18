package com.myrepo.rentacar.services;


import com.gfa.backoffice.dto.JWTAuthenticationResponse;
import com.gfa.backoffice.dto.SignInRequest;
import com.gfa.backoffice.dto.SignUpRequest;
import com.gfa.backoffice.entities.FoxUser;

public interface AuthenticationService {
    void signup(SignUpRequest signUpRequest);

    JWTAuthenticationResponse signIn(SignInRequest signInRequest);

    FoxUser saveNewAdmin(String email, String password);
}

