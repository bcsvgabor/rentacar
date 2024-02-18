package com.myrepo.rentacar.services.implementation;

import com.gfa.backoffice.config.Impersonation;
import com.gfa.backoffice.dto.JWTAuthenticationResponse;
import com.gfa.backoffice.dto.SignInRequest;
import com.gfa.backoffice.dto.SignUpRequest;
import com.gfa.backoffice.entities.FoxRole;
import com.gfa.backoffice.entities.FoxUser;
import com.gfa.backoffice.exceptions.UnathorizedUserException;
import com.gfa.backoffice.repositories.FoxRoleRepository;
import com.gfa.backoffice.repositories.FoxUserRepository;
import com.gfa.backoffice.services.ApiKeyService;
import com.gfa.backoffice.services.AuthenticationService;
import com.gfa.backoffice.services.DateService;
import com.gfa.backoffice.services.JWTService;
import com.gfa.backoffice.utils.PasswordUtil;
import com.myrepo.rentacar.repositories.RentalRoleRepository;
import com.myrepo.rentacar.repositories.RentalUserRepository;
import com.myrepo.rentacar.services.AuthenticationService;
import com.myrepo.rentacar.services.JWTService;
import com.myrepo.rentacar.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RentalUserRepository rentalUserRepository;

    private final RentalRoleRepository rentalRoleRepository;

    private final PasswordUtil passwordUtil;

    private final JWTService jwtService;

    private final DateService dateService;
    private final ApiKeyService apiKeyService;

    @Value("${backoffice.pwd.secret_key}")
    private String secret;


    public void signup(SignUpRequest signUpRequest) {
        FoxUser foxUser = new FoxUser();

        String passwordHashed = passwordUtil.createHash(signUpRequest.getPassword(), secret);

        Set<FoxRole> roles = signUpRequest
                .getRoles()
                .stream()
                .map(foxRoleRepository::findByName).collect(Collectors.toSet());

        foxUser.setLastLogin(dateService.getCurrentDateTime());
        foxUser.getFoxRoles().addAll(roles);


        foxUser.setEmail(signUpRequest.getEmail());
        foxUser.setStatus("active");
        foxUser.setPassword(passwordHashed);

        foxUser.setLastLogin(dateService.getCurrentDateTime());
        foxUserRepository.save(foxUser);

    }

    public JWTAuthenticationResponse signIn(SignInRequest signInRequest) {

        var user = foxUserRepository.findByEmail(signInRequest.getEmail()).get();

        if (!passwordUtil.verifyHash(user.getPassword(), signInRequest.getPassword(), secret)) {
            throw new UnathorizedUserException("Invalid password");
        }

        user.setLastLogin(dateService.getCurrentDateTime());
        foxUserRepository.save(user);
        String jwt = jwtService.generateToken(Impersonation.fromUser(user), apiKeyService.getApiKeyId(signInRequest.getSecret()));
        JWTAuthenticationResponse jwtAuthenticationResponse = new JWTAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);

        return jwtAuthenticationResponse;
    }

    public FoxUser saveNewAdmin(String email, String password) {
        FoxUser foxUser = new FoxUser();
        foxUser.getFoxRoles().add(foxRoleRepository.findByName("admin"));
        foxUser.setEmail(email);
        foxUser.setPassword(passwordUtil.createHash(password, secret));
        foxUser.setStatus("active");
        foxUser.setLastLogin(dateService.getCurrentDateTime());
        return foxUserRepository.save(foxUser);
    }
}

