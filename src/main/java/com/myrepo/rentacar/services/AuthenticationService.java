package com.myrepo.rentacar.services;


import com.myrepo.rentacar.dto.JWTAuthenticationResponse;
import com.myrepo.rentacar.dto.SignInRequest;
import com.myrepo.rentacar.dto.SignUpRequest;
import com.myrepo.rentacar.entities.RentalUser;

public interface AuthenticationService {
    void signup(SignUpRequest signUpRequest);

    JWTAuthenticationResponse signIn(SignInRequest signInRequest);

    RentalUser saveNewAdmin(String email, String password);
}

