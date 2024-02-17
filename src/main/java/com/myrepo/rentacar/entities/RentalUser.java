package com.myrepo.rentacar.entities;
import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "user")
public class RentalUser {

    @Id
    @GeneratedValue
    private Long id;
    private String email;
    private String password;
    private String role;
}
