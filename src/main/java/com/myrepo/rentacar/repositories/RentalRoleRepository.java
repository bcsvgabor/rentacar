package com.myrepo.rentacar.repositories;

import com.myrepo.rentacar.entities.RentalRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRoleRepository extends JpaRepository<RentalRole, Long> {

    RentalRole findByName(String name);

    boolean existsByName(String name);
}
