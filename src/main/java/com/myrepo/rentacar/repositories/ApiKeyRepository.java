package com.myrepo.rentacar.repositories;

import com.myrepo.rentacar.entities.ApiKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

public interface ApiKeyRepository extends JpaRepository<ApiKey, Long> {

    @NonNull
    Optional<ApiKey> findById(@NonNull Long id);

    List<ApiKey> findAllByOrderByCreatedAtDesc();

    List<ApiKey> findAllByActiveIsTrueOrderByCreatedAtDesc();
}
