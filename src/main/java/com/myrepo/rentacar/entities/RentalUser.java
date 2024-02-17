package com.myrepo.rentacar.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
