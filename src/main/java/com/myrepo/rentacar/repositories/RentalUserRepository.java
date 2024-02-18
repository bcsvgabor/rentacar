package com.myrepo.rentacar.repositories;

import com.myrepo.rentacar.entities.RentalUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RentalUserRepository extends JpaRepository<RentalUser, Long> {

    Optional<RentalUser> findById(Long id);

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    Optional<RentalUser> findByEmail(String email);


}
