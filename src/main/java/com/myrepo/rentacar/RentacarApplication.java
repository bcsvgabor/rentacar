package com.myrepo.rentacar;

import com.myrepo.rentacar.entities.RentalUser;
import com.myrepo.rentacar.services.ApiKeyService;
import com.myrepo.rentacar.services.AuthenticationService;
import com.myrepo.rentacar.services.RentalRoleService;
import com.myrepo.rentacar.services.RentalUserService;
import com.myrepo.rentacar.utils.ApiKeyCreationTool;
import com.myrepo.rentacar.utils.UserCreationTool;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.HashMap;
import java.util.Objects;

@SpringBootApplication
@RequiredArgsConstructor
public class RentacarApplication implements CommandLineRunner, ApplicationRunner {

    private final AuthenticationService authenticationService;
    private final RentalRoleService rentalRoleService;
    private final RentalUserService rentalUserService;
    private final ApiKeyService apiKeyService;

    public static void main(String[] args) {
        SpringApplication.run(RentacarApplication.class, args);
    }

    @Override
    public void run(String... args) {
        UserCreationTool userCreationTool = new UserCreationTool(rentalUserService, rentalRoleService);
        ApiKeyCreationTool apiKeyCreationTool = new ApiKeyCreationTool();

        if (Objects.equals(args[0], "--init")) {
            if (args.length != 7) {
                System.out.println("To create admin, start with --init keyword.");
            }

            HashMap<String, String> dbDataAndEmail = userCreationTool.getDbDataAndEmail(args);
            HashMap<String, String> getPasswordInputs = userCreationTool.getPasswords();

            RentalUser foxUser = authenticationService.saveNewAdmin(dbDataAndEmail.get("email"), getPasswordInputs.get("userPassword"));
            System.out.printf("Created a user with the email: %s%n", dbDataAndEmail.get("email"));

            System.out.println("Generating API Key!");
            apiKeyCreationTool.createKey(apiKeyService, foxUser);
        }
    }

    @Override
    public void run(ApplicationArguments args) {
        rentalRoleService.initializeRoles();
    }
}
