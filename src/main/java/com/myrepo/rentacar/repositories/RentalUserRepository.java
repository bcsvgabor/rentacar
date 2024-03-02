package com.myrepo.rentacar.repositories;

import com.myrepo.rentacar.entities.RentalUser;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface RentalUserRepository extends JpaRepository<RentalUser, Long> {

    Optional<RentalUser> findById(Long id);

    boolean existsByEmail(String email);

    boolean existsById(Long id);

    Optional<RentalUser> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("DELETE FROM RentalUser user WHERE user.id = :userId")
    void deleteUserById(Long userId);
}
