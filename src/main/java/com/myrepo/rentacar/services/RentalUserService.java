package com.myrepo.rentacar.services;

import com.myrepo.rentacar.dto.CreateCustomerRequest;
import com.myrepo.rentacar.dto.CreateUserDetailsRequest;
import com.myrepo.rentacar.dto.CustomerProfileResponse;
import com.myrepo.rentacar.entities.RentalUser;
import com.myrepo.rentacar.exceptions.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface RentalUserService {

    RentalUser findByEmail(String userName);

    void saveUser(RentalUser foxUser);

    String getRoleOfUser(RentalUser rentalUser);
    void deleteUser(RentalUser foxUser);
    void createRentalUser(CreateUserDetailsRequest createUserDetailsRequest) throws NotFoundException;

    Long getFoxUserIdByEmail(String email);

    boolean existsByEmail(String email);

    UserDetailsService userDetailsService();

    Optional<RentalUser> findUserById(Long id);

    CreateUserDetailsRequest createCustomerCreateUserDetailsRequest(CreateCustomerRequest createCustomerRequest);

    boolean isAdmin(UserDetails userDetails);

    CustomerProfileResponse createCustomerProfileResponse(Long id);

}
