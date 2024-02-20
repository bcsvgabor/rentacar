package com.myrepo.rentacar.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@Table(name = "customer_profile")
public class CustomerRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "foxUserId")
    private RentalUser foxUser;

    public void modifyAtCustomerRecord() {
        modifiedAt = LocalDateTime.now();
    }

    public CustomerRecord() {
        this.createdAt = LocalDateTime.now();
    }
}
