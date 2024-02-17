package com.myrepo.rentacar.services;

import com.myrepo.rentacar.entities.RentalUser;

import java.time.LocalDate;

public interface ApiKeyService {

    String createApiKey(String description, LocalDate expiresAt, RentalUser createdBy);
}
