package com.myrepo.rentacar.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class CustomerProfileResponse {

    private Long customerId;
    private String fullName;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
