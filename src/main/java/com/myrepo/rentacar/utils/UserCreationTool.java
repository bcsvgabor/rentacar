package com.myrepo.rentacar.utils;

import com.myrepo.rentacar.services.RentalRoleService;
import com.myrepo.rentacar.services.RentalUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.Console;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

@Component
@AllArgsConstructor
public class UserCreationTool {

    /**
     * --init --dburl jdbc:mysql://localhost/backoffice?serverTimezone=UTC&createDatabaseIfNotExist=true --dbusr root --email asd@asd.com
     */

    public UserCreationTool(RentalUserService foxUserService, RentalRoleService foxRoleService) {
    }

    public HashMap<String, String> getDbDataAndEmail(String[] args) {

        HashMap<String, String> data = new HashMap<>();
        String databaseUrl;
        String databaseUsername;
        String email;

        for (int i = 1; i < args.length; i += 2) {
            if ("--dburl".equals(args[i]) && args[i + 1].equals(System.getenv("DB_URL"))) {
                databaseUrl = args[i + 1];
                data.put("dburl", databaseUrl);
            } else if ("--dbusr".equals(args[i]) && args[i + 1].equals(System.getenv("DB_USERNAME"))) {
                databaseUsername = args[i + 1];
                data.put("dbusr", databaseUsername);
            } else if ("--email".equals(args[i])) {
                email = args[i + 1];
                data.put("email", email);
            } else {
                System.out.println("Invalid argument: " + args[i]);
            }
        }
        return data;
    }

    public HashMap<String, String> getPasswords() {
        Scanner scanner = new Scanner(System.in);
        Console console = System.console();

        String userPassword = null;

        HashMap<String, String> data = new HashMap<>();
        if (console == null) {
            System.out.print("Enter User Password: ");
            userPassword = scanner.nextLine();
            data.put("userPassword", userPassword);
        } else {
            System.out.print("Enter User Password: ");
            userPassword = Arrays.toString(console.readPassword("Enter User password: "));
        }
        data.put("userPassword", userPassword);
        return data;
    }

}