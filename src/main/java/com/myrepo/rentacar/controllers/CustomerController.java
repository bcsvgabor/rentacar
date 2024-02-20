package com.myrepo.rentacar.controllers;

import com.myrepo.rentacar.config.Impersonation;
import com.myrepo.rentacar.dto.CreateCustomerRequest;
import com.myrepo.rentacar.dto.CreateUserDetailsRequest;
import com.myrepo.rentacar.dto.CustomerProfileResponse;
import com.myrepo.rentacar.exceptions.NotFoundException;
import com.myrepo.rentacar.exceptions.UnathorizedUserException;
import com.myrepo.rentacar.exceptions.ValidationAppException;
import com.myrepo.rentacar.services.CustomerRecordService;
import com.myrepo.rentacar.services.RentalUserService;
import com.myrepo.rentacar.utils.PasswordValidatorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")
public class CustomerController {

    private final RentalUserService rentalUserService;
    private final AdminController adminController;
    private final CustomerRecordService customerRecordService;

    @GetMapping("/create")
    public void createCustomer(@RequestBody CreateCustomerRequest createCustomerRequest,
                               @AuthenticationPrincipal Impersonation impersonation) {

        if (createCustomerRequest == null
                || createCustomerRequest.getEmail() == null || createCustomerRequest.getEmail().isEmpty()
                || createCustomerRequest.getPassword() == null || createCustomerRequest.getPassword().isEmpty()
                || createCustomerRequest.getFullName() == null || createCustomerRequest.getFullName().isEmpty()
        ) {
            throw new ValidationAppException("One or more required fields are missing.");
        }

        if (rentalUserService.existsByEmail(createCustomerRequest.getEmail())) {
            throw new ValidationAppException("User already exists.");
        }

        if (!PasswordValidatorUtil.isProdPasswordValid(createCustomerRequest.getPassword())) {
            throw new ValidationAppException("Password is not strong enough.");
        }

        CreateUserDetailsRequest dto = rentalUserService.createCustomerCreateUserDetailsRequest(createCustomerRequest);
        rentalUserService.createRentalUser(dto);

        customerRecordService.createCustomerRecord(createCustomerRequest);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerProfile(@PathVariable Long id,
                                                @AuthenticationPrincipal Impersonation impersonation) {

        if (id == null || rentalUserService.findUserById(id).isEmpty()) {
            throw new NotFoundException("ID does not exist.");
        }
        if (impersonation.getId() == id
                || rentalUserService.isAdmin(impersonation)) {

            CustomerProfileResponse profile = rentalUserService.createCustomerProfileResponse(id);

            return ResponseEntity.ok(profile);
        } else {

            throw new UnathorizedUserException("User is not authorized to view data.");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile(@AuthenticationPrincipal Impersonation impersonation) {

        if (impersonation == null) {
            throw new UnathorizedUserException("User not authenticated.");
        }

        CustomerProfileResponse profile = rentalUserService.createCustomerProfileResponse(impersonation.getId());
        return ResponseEntity.ok(profile);
    }
}
