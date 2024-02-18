package com.myrepo.rentacar.services;

import com.myrepo.rentacar.entities.RentalRole;

import java.util.List;

public interface RentalRoleService {

    void initializeRoles();

    RentalRole findByName(String name);

    boolean isRoleListValid(List<String> roles);


}
