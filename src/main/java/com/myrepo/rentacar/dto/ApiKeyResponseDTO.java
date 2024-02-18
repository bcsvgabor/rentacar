package com.myrepo.rentacar.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ApiKeyResponseDTO {

    private Long id;
    private String description;
    private LocalDate expiresAt;
    private boolean isActive;


}
