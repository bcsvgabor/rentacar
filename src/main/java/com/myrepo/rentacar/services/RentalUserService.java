package com.myrepo.rentacar.services;

import com.myrepo.rentacar.entities.RentalUser;

import java.util.List;

public interface RentalUserService {

    RentalUser findByEmail(String userName);

    void saveUser(RentalUser foxUser);

    String getRoleOfUser(RentalUser rentalUser);

}
