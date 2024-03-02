package com.myrepo.rentacar.controllers;

import com.myrepo.rentacar.config.Impersonation;
import com.myrepo.rentacar.dto.CreateUserDetailsRequest;
import com.myrepo.rentacar.entities.RentalUser;
import com.myrepo.rentacar.exceptions.NotFoundException;
import com.myrepo.rentacar.exceptions.UnathorizedUserException;
import com.myrepo.rentacar.exceptions.UnauthorizedAppException;
import com.myrepo.rentacar.exceptions.ValidationAppException;
import com.myrepo.rentacar.repositories.RentalUserRepository;
import com.myrepo.rentacar.services.RentalUserService;
import com.myrepo.rentacar.utils.PasswordValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final RentalUserService rentalUserService;
    private final RentalUserRepository rentalUserRepository;

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @AuthenticationPrincipal Impersonation impersonation) {
        if (!impersonation.isAdmin()) {
            throw new UnauthorizedAppException("User is not authorized to delete.");
        }

        Optional<RentalUser> selectedUser = rentalUserRepository.findById(id);
        if (selectedUser.isEmpty()) {
            throw new NotFoundException("User was not found.");
        } else if (selectedUser.get().getEmail().equals(impersonation.getUsername())) {
            throw new UnauthorizedAppException("Cannot delete your account.");
        }

        rentalUserService.deleteUser(selectedUser.get());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/createuser")
    public ResponseEntity<?> createUser(@RequestBody CreateUserDetailsRequest createUserDetailsRequest,
                                        @AuthenticationPrincipal Impersonation impersonation) throws NotFoundException {

        if (!impersonation.isAdmin()) {
            throw new UnathorizedUserException("Admin role required.");
        }

        if (createUserDetailsRequest == null || !createUserDetailsRequest.isValid()
                || !PasswordValidatorUtil.isProdPasswordValid(createUserDetailsRequest.getPassword())) {
            throw new ValidationAppException("Request is not valid.");
        }

        rentalUserService.createRentalUser(createUserDetailsRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(rentalUserService.getFoxUserIdByEmail(createUserDetailsRequest.getEmail()))
                .toUri();

        return ResponseEntity.status(HttpStatus.CREATED)
                .header(HttpHeaders.LOCATION, String.valueOf(location))
                .build();

    }
}
