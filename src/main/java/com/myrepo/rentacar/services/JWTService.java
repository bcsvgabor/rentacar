package com.myrepo.rentacar.services;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface JWTService {

    String generateToken(UserDetails userDetails, Long apiKeyId);

    String extractUserName(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    List<String> extractUserRoles(String token);
}
