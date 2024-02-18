package com.myrepo.rentacar.services;

import com.myrepo.rentacar.dto.ApiKeyResponseDTO;
import com.myrepo.rentacar.dto.Impersonation;
import com.myrepo.rentacar.entities.ApiKey;
import com.myrepo.rentacar.entities.RentalUser;

import java.time.LocalDate;
import java.util.List;

public interface ApiKeyService {

    String createApiKey(String description, LocalDate expiresAt, RentalUser createdBy);


    void saveApiKey(ApiKey apiKey);

    String decodeSecret(String secret);

    void revokeApiKey(Long id, Impersonation modifiedBy);

    List<ApiKeyResponseDTO> findAllKeysSorted(String all, Impersonation impersonation);

    boolean isApiKeyValid(String nonHashedApiKey);

    Long getApiKeyId(String nonHashSecret);
}
