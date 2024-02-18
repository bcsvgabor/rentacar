package com.myrepo.rentacar.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@RequiredArgsConstructor
@Table(name = "roles")
public class RentalRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public RentalRole(String name) {
        this.name = name;
    }
}
