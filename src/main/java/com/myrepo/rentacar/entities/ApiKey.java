package com.myrepo.rentacar.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private String token;
    @ManyToOne
    private RentalUser createdBy;
    private LocalDate createdAt;
    private LocalDate expiresAt;
    private LocalDate modifiedAt;
    @ManyToOne
    private RentalUser modifiedBy;
    private boolean active;

}
