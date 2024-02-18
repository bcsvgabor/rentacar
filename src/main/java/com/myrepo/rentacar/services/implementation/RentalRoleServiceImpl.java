package com.myrepo.rentacar.services.implementation;

import com.myrepo.rentacar.entities.RentalRole;
import com.myrepo.rentacar.repositories.RentalRoleRepository;
import com.myrepo.rentacar.services.RentalRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RentalRoleServiceImpl implements RentalRoleService {

    private final RentalRoleRepository rentalRoleRepository;

    public void initializeRoles() {
        createRole("admin");
        createRole("employee");
        createRole("customer");
    }

    private void createRole(String name) {

        RentalRole existingFoxRole = rentalRoleRepository.findByName(name);

        if (existingFoxRole == null) {
            RentalRole newFoxRole = new RentalRole();
            newFoxRole.setName(name);
            rentalRoleRepository.save(newFoxRole);
        }
    }

    @Override
    public RentalRole findByName(String name) {
        return rentalRoleRepository.findByName(name);
    }

    @Override
    public boolean isRoleListValid(List<String> roles) {

        for (String role : roles) {
            if (!rentalRoleRepository.existsByName(role)) {
                return false;
            }
        }
        return true;
    }
}
