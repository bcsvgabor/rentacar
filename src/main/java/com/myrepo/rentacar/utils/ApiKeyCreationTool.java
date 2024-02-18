package com.myrepo.rentacar.utils;

import com.myrepo.rentacar.entities.RentalUser;
import com.myrepo.rentacar.services.ApiKeyService;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Scanner;

@Component
public class ApiKeyCreationTool {

    public void createKey(ApiKeyService apiKeyService, RentalUser foxUser) {
        Scanner scanner = new Scanner(System.in);

        String description;
        String dateInput;
        LocalDate expiryDate;

        System.out.print("Enter API Key description: ");
        description = scanner.nextLine();
        System.out.print("Enter expiry date (YYYY-MM-DD): ");
        dateInput = scanner.nextLine();
        expiryDate = (Objects.equals(dateInput, "")) ? LocalDate.now().plusDays(10) : LocalDate.parse(dateInput);
        System.out.println(apiKeyService.createApiKey(description, expiryDate, foxUser));
    }
}
