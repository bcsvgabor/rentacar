package com.myrepo.rentacar.services.implementation;

import com.myrepo.rentacar.entities.RentalUser;
import com.myrepo.rentacar.services.ApiKeyService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/*
@Service
public class ApiKeyServiceImpl implements ApiKeyService {
    @Override
    public String createApiKey(String description, LocalDate expiresAt, FoxUser createdBy) {
        String randomLetters = generateRandomSecret();
        ApiKey apiKey = new ApiKey();
        apiKey.setDescription(description);
        apiKey.setToken(passwordUtil.createHash(randomLetters, secret));
        apiKey.setCreatedBy(createdBy);
        apiKey.setCreatedAt(LocalDate.now());
        apiKey.setExpiresAt(expiresAt);
        apiKey.setActive(true);
        apiKeyRepository.save(apiKey);
        return generateTokenFromRandomLetters(apiKey.getId(), randomLetters);
    }
}
*/

