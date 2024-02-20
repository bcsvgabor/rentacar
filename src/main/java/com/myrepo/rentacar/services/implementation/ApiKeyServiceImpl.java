package com.myrepo.rentacar.services.implementation;

import com.myrepo.rentacar.dto.ApiKeyResponseDTO;
import com.myrepo.rentacar.config.Impersonation;
import com.myrepo.rentacar.entities.ApiKey;
import com.myrepo.rentacar.entities.RentalUser;
import com.myrepo.rentacar.exceptions.NotFoundException;
import com.myrepo.rentacar.exceptions.UnauthorizedAppException;
import com.myrepo.rentacar.exceptions.ValidationAppException;
import com.myrepo.rentacar.repositories.ApiKeyRepository;
import com.myrepo.rentacar.services.ApiKeyService;
import com.myrepo.rentacar.services.RentalUserService;
import com.myrepo.rentacar.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;


@Service
@RequiredArgsConstructor
public class ApiKeyServiceImpl implements ApiKeyService {

    private final ApiKeyRepository apiKeyRepository;
    private final PasswordUtil passwordUtil;
    private final RentalUserService rentalUserService;


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

    public void saveApiKey(ApiKey apiKey) {
        apiKeyRepository.save(apiKey);
    }

    public String decodeSecret(String secret) {
        byte[] decode = Base64.getDecoder().decode(secret);
        return new String(decode, StandardCharsets.UTF_8);
    }

    public void revokeApiKey(Long id, Impersonation modifiedBy) {
        Optional<ApiKey> apiKey = apiKeyRepository.findById(id);

        if (!modifiedBy.isAdmin()) {
            throw new UnauthorizedAppException("User is not admin.");
        }

        if (apiKey.isEmpty()) {
            throw new NotFoundException("API key not found.");
        }

        if (!apiKey.get().isActive() || apiKey.get().getExpiresAt().isBefore(LocalDate.now())) {
            return;
        }

        RentalUser foxUser = rentalUserService.findUserById(modifiedBy.getId()).get();
        ApiKey foundApiKey = apiKey.get();
        foundApiKey.setToken(null);
        foundApiKey.setModifiedAt(LocalDate.now());
        foundApiKey.setModifiedBy(foxUser);
        foundApiKey.setExpiresAt(LocalDate.now());
        foundApiKey.setActive(false);

        apiKeyRepository.save(foundApiKey);
    }

    public List<ApiKeyResponseDTO> findAllKeysSorted(String all, Impersonation user) {

        if (!user.isAdmin()) {
            throw new UnauthorizedAppException("User is not admin.");
        }

        if (all != null && (!all.equalsIgnoreCase("true") && !all.equalsIgnoreCase("false"))) {
            throw new ValidationAppException(all + " can not be parsed as boolean.");
        }

        List<ApiKey> apiKeys = (Boolean.parseBoolean(all)) ? apiKeyRepository.findAllByActiveIsTrueOrderByCreatedAtDesc() :
                apiKeyRepository.findAllByOrderByCreatedAtDesc();
        List<ApiKeyResponseDTO> response = new ArrayList<>();

        for (ApiKey a : apiKeys) {
            ApiKeyResponseDTO apiKeyResponseDTO = new ApiKeyResponseDTO();
            apiKeyResponseDTO.setId(a.getId());
            apiKeyResponseDTO.setDescription(a.getDescription());
            apiKeyResponseDTO.setExpiresAt(a.getExpiresAt());
            apiKeyResponseDTO.setActive(a.isActive());
            response.add(apiKeyResponseDTO);
        }

        return response;
    }

    @Override
    public boolean isApiKeyValid(String nonHashedApiKey) {
        Optional<ApiKey> apiKey = apiKeyRepository.findById(getApiKeyId(nonHashedApiKey));
        if (apiKey.isPresent()) {

            boolean isNotExpired = apiKey.get().getExpiresAt() == null || apiKey.get().getExpiresAt().isAfter(LocalDate.now());
            boolean isActive = apiKey.get().isActive();
            boolean tokenMatches = tokenMatches(nonHashedApiKey, apiKey.get());

            return  tokenMatches && isNotExpired && isActive;
        }
        return false;
    }

    private boolean tokenMatches(String secretHash, ApiKey apiKey) {
        String[] decodedSecret = decodeSecret(secretHash).split(":");
        String password = decodedSecret[1];
        return passwordUtil.verifyHash(apiKey.getToken(), password, secret);
    }

    public Long getApiKeyId(String nonHashedSecret) {
        String[] decodedSecret = decodeSecret(nonHashedSecret).split(":");
        return Long.valueOf(decodedSecret[0]);
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


