package com.myrepo.rentacar.services.implementation;


import com.myrepo.rentacar.entities.RentalUser;
import com.myrepo.rentacar.repositories.RentalUserRepository;
import com.myrepo.rentacar.services.DateService;
import com.myrepo.rentacar.services.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.List;


@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JWTService {

    @Value("${backoffice.jwt.secret_key}")
    private String secret;
    private static final int KEY_LEN = 64;

    private final RentalUserRepository rentalUserRepository;
    private final RentalUserServiceImpl rentalUserServiceImpl;
    private final DateService dateService;


    public String generateToken(UserDetails userDetails, Long apiKeyId) {

        RentalUser foxUser = rentalUserRepository.findByEmail(userDetails.getUsername()).get();

        Long id = foxUser.getId();

        Key secretKey = new SecretKeySpec(getSigninKey(), "HmacSHA512");

        rentalUserRepository.save(foxUser);

        /*

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .claim("id", id)
                .claim("role", rentalUserServiceImpl.getRoleOfUser(foxUser))
                .claim("client", apiKeyId)
                .claim("roles", rentalUserServiceImpl.getRoleOfUser(foxUser))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

    */
        return null;

    }

    @Override
    public String extractUserName(String token) {
        return null;
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        return false;
    }

    @Override
    public List<String> extractUserRoles(String token) {
        return null;
    }


    private byte[] getSigninKey() {
        byte[] key = secret.getBytes();
        byte[] result = new byte[KEY_LEN];
        int p = 0;
        while (p < key.length) {
            int l = Math.min(key.length, result.length - p);
            System.arraycopy(key, 0, result, p, l);
            p += l;
        }

        return result;
    }



}
