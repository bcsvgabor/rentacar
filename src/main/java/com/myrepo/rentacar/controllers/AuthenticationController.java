package com.myrepo.rentacar.controllers;

import com.myrepo.rentacar.dto.SignInRequest;
import com.myrepo.rentacar.dto.SignUpRequest;
import com.myrepo.rentacar.exceptions.UnathorizedUserException;
import com.myrepo.rentacar.exceptions.ValidationAppException;
import com.myrepo.rentacar.handlers.ApiExceptionHandler;
import com.myrepo.rentacar.services.ApiKeyService;
import com.myrepo.rentacar.services.AuthenticationService;
import com.myrepo.rentacar.services.RentalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final RentalUserService foxUserService;
    private final ApiExceptionHandler apiExceptionHandler;

    private final ApiKeyService apiKeyService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignUpRequest signUpRequest) {

        authenticationService.signup(signUpRequest);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signin(@RequestBody SignInRequest signInRequest) {

        if (signInRequest.getEmail() == null || signInRequest.getEmail().isEmpty()) {
            return apiExceptionHandler.handleExceptions(new ValidationAppException("Missing email"));

        } else if (signInRequest.getPassword() == null || signInRequest.getPassword().isEmpty()) {
            return apiExceptionHandler.handleExceptions(new ValidationAppException("Missing password"));
        } else if (!foxUserService.existsByEmail(signInRequest.getEmail())) {
            return apiExceptionHandler.handleExceptions(new UnathorizedUserException("Invalid email"));
        } else if (!StringUtils.hasText(signInRequest.getSecret())) {
            return apiExceptionHandler.handleExceptions(new ValidationAppException("Secret is missing."));
        }

        apiKeyService.isApiKeyValid(signInRequest.getSecret());

        return ResponseEntity.ok(authenticationService.signIn(signInRequest));
    }
}

