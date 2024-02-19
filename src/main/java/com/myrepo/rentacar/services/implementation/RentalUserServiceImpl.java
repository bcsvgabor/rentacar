package com.myrepo.rentacar.services.implementation;

import com.myrepo.rentacar.dto.CreateRentalDetailsRequest;
import com.myrepo.rentacar.config.Impersonation;
import com.myrepo.rentacar.entities.RentalUser;
import com.myrepo.rentacar.exceptions.NotFoundException;
import com.myrepo.rentacar.repositories.RentalUserRepository;
import com.myrepo.rentacar.services.RentalUserService;
import com.myrepo.rentacar.utils.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RentalUserServiceImpl implements RentalUserService {

    private final RentalUserRepository rentalUserRepository;
    private final PasswordUtil passwordUtil;

    @Value("${backoffice.pwd.secret_key}")
    private String secret;

    @Override
    public RentalUser findByEmail(String userName) {

        if (rentalUserRepository.findByEmail(userName).isPresent()) {
            return rentalUserRepository.findByEmail(userName).get();

        } else {
            throw new NotFoundException("User is not found.");
        }
    }

    public void saveUser(RentalUser rentalUser) {
        rentalUserRepository.save(rentalUser);
    }

    public String getRoleOfUser(RentalUser foxUser) {
        return foxUser.getRole();
    }

    @Override
    public void deleteUser(RentalUser rentalUser) {
        Long userId = rentalUser.getId();
        rentalUserRepository.deleteUserById(userId);
    }

    public void createRentalUser(CreateRentalDetailsRequest createUserDetailsRequest) {

        String passwordHashed = passwordUtil.createHash(createUserDetailsRequest.getPassword(), secret);

        RentalUser rentalUser = new RentalUser();

        rentalUser.setEmail(createUserDetailsRequest.getEmail());
        rentalUser.setPassword(passwordHashed);
        rentalUser.setRole(createUserDetailsRequest.getRole());

        rentalUserRepository.save(rentalUser);
    }

    public Long getFoxUserIdByEmail(String email) throws NotFoundException {
        Optional<RentalUser> foxUser = rentalUserRepository.findByEmail(email);
        if (foxUser.isPresent()) {
            return foxUser.get().getId();
        } else {
            throw new NotFoundException("FoxUser email not found.");
        }
    }

    public boolean existsByEmail(String email) {
        return rentalUserRepository.existsByEmail(email);
    }

    @Override
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

                return Impersonation.fromUser(rentalUserRepository.findByEmail(username).get());
            }
        };
    }

}
