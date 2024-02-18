package com.myrepo.rentacar.services.implementation;

import com.myrepo.rentacar.entities.RentalUser;
import com.myrepo.rentacar.exceptions.NotFoundException;
import com.myrepo.rentacar.repositories.RentalUserRepository;
import com.myrepo.rentacar.services.RentalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RentalUserServiceImpl implements RentalUserService {

    private final RentalUserRepository rentalUserRepository;

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
}
