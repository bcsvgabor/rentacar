package com.myrepo.rentacar.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordUtil {

    private static final int ITERATIONS = 64000;

    @Value("${salt.length}")
    private int SALT_LENGTH;

    public PasswordEncoder getEncoder(String secretCode) {
        return new Pbkdf2PasswordEncoder(
                secretCode,
                SALT_LENGTH,
                ITERATIONS,
                Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512
        );
    }

    public String createHash(String password, String secretCode) {
        return getEncoder(secretCode).encode(password);
    }

    public boolean verifyHash(String hash, String password, String secretCode) {
        return getEncoder(secretCode).matches(
                password,
                hash
        );
    }
}
