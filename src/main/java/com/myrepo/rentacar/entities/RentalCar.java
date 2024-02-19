package com.myrepo.rentacar.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.text.SimpleDateFormat;

@Entity
@Data
public class RentalCar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String manufacturer;
    private String type;
    private Integer odometer;
    private boolean booked;
    private SimpleDateFormat bookedFrom;
    private SimpleDateFormat bookedUntil;

    @ManyToOne
    private RentalGarage garage;

}
