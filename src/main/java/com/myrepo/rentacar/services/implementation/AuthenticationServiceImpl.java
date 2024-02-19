package com.myrepo.rentacar.services.implementation;

import com.myrepo.rentacar.config.Impersonation;
import com.myrepo.rentacar.dto.JWTAuthenticationResponse;
import com.myrepo.rentacar.dto.SignInRequest;
import com.myrepo.rentacar.dto.SignUpRequest;
import com.myrepo.rentacar.entities.RentalUser;
import com.myrepo.rentacar.exceptions.NotFoundException;
import com.myrepo.rentacar.exceptions.UnathorizedUserException;
import com.myrepo.rentacar.repositories.RentalUserRepository;
import com.myrepo.rentacar.services.ApiKeyService;
import com.myrepo.rentacar.services.AuthenticationService;
import com.myrepo.rentacar.services.JWTService;
import com.myrepo.rentacar.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RentalUserRepository rentalUserRepository;

    private final PasswordUtil passwordUtil;

    private final JWTService jwtService;

    private final ApiKeyService apiKeyService;

    @Value("${backoffice.pwd.secret_key}")
    private String secret;


    public void signup(SignUpRequest signUpRequest) {
        RentalUser rentalUser = new RentalUser();

        String passwordHashed = passwordUtil.createHash(signUpRequest.getPassword(), secret);

        rentalUser.setRole(signUpRequest.getRole());

        rentalUser.setEmail(signUpRequest.getEmail());
        rentalUser.setPassword(passwordHashed);

        rentalUserRepository.save(rentalUser);
    }

    public JWTAuthenticationResponse signIn(SignInRequest signInRequest) {

        if (rentalUserRepository.findByEmail(signInRequest.getEmail()).isPresent()) {
            var user = rentalUserRepository.findByEmail(signInRequest.getEmail()).get();

            if (!passwordUtil.verifyHash(user.getPassword(), signInRequest.getPassword(), secret)) {
                throw new UnathorizedUserException("Invalid password");
            }

            if (!passwordUtil.verifyHash(user.getPassword(), signInRequest.getPassword(), secret)) {
                throw new UnathorizedUserException("Invalid password");
            }

            rentalUserRepository.save(user);
            String jwt = jwtService.generateToken(Impersonation.fromUser(user), apiKeyService.getApiKeyId(signInRequest.getSecret()));
            JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
            jwtAuthenticationResponse.setToken(jwt);

            return jwtAuthenticationResponse;

        } else {
            throw new NotFoundException("Email is not found.");
        }

    }

    public RentalUser saveNewAdmin(String email, String password) {
        RentalUser rentalUser = new RentalUser();

        rentalUser.setRole("admin");
        rentalUser.setEmail(email);
        rentalUser.setPassword(passwordUtil.createHash(password, secret));
        return rentalUserRepository.save(rentalUser);
    }
}

