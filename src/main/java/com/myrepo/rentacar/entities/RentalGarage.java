package com.myrepo.rentacar.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.text.SimpleDateFormat;
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
public class RentalGarage {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @
    private List<RentalCar> cars;
}

