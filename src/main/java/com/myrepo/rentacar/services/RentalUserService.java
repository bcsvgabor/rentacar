package com.myrepo.rentacar.services;

import com.myrepo.rentacar.dto.CreateRentalDetailsRequest;
import com.myrepo.rentacar.entities.RentalUser;
import com.myrepo.rentacar.exceptions.NotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface RentalUserService {

    RentalUser findByEmail(String userName);

    void saveUser(RentalUser foxUser);

    String getRoleOfUser(RentalUser rentalUser);
    void deleteUser(RentalUser foxUser);
    void createRentalUser(CreateRentalDetailsRequest createRentalDetailsRequest) throws NotFoundException;

    Long getFoxUserIdByEmail(String email);

    boolean existsByEmail(String email);

    UserDetailsService userDetailsService();
}
