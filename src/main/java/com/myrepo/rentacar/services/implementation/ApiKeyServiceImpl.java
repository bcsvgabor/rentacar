package com.myrepo.rentacar.services.implementation;

import com.myrepo.rentacar.dto.ApiKeyResponseDTO;
import com.myrepo.rentacar.dto.Impersonation;
import com.myrepo.rentacar.entities.ApiKey;
import com.myrepo.rentacar.entities.RentalUser;
import com.myrepo.rentacar.repositories.ApiKeyRepository;
import com.myrepo.rentacar.services.ApiKeyService;
import com.myrepo.rentacar.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Random;


@Service
@RequiredArgsConstructor
public class ApiKeyServiceImpl implements ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;
    private final PasswordUtil passwordUtil;

    @Value("${backoffice.pwd.secret_key}")
    private String secret;

    @Override
    public String createApiKey(String description, LocalDate expiresAt, RentalUser rentalUser) {

        String randomString = generateRandomSecret();

        ApiKey apiKey = new ApiKey();
        apiKey.setDescription(description);
        apiKey.setToken(passwordUtil.createHash(randomString, secret));
        apiKey.setCreatedBy(rentalUser);
        apiKey.setCreatedAt(LocalDate.now());
        apiKey.setExpiresAt(expiresAt);
        apiKey.setActive(true);
        apiKeyRepository.save(apiKey);
        return generateTokenFromRandomString(apiKey.getId(), randomString);
    }

    @Override
    public void saveApiKey(ApiKey apiKey) {

    }

    @Override
    public String decodeSecret(String secret) {
        return null;
    }

    @Override
    public void revokeApiKey(Long id, Impersonation modifiedBy) {

    }

    @Override
    public List<ApiKeyResponseDTO> findAllKeysSorted(String all, Impersonation impersonation) {
        return null;
    }

    @Override
    public boolean isApiKeyValid(String nonHashedApiKey) {
        return false;
    }

    @Override
    public Long getApiKeyId(String nonHashSecret) {
        return null;
    }

    private String generateRandomSecret() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder randomString = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 20; i++) {
            randomString.append(alphabet.charAt(random.nextInt(alphabet.length())));
        }
        return randomString.toString();
    }

    private String generateTokenFromRandomString(Long apiKeyId, String randomString) {
        String tokenBeforeEncoding = apiKeyId + ":" + randomString;
        byte[] bytes = tokenBeforeEncoding.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().encodeToString(bytes);
    }
}


