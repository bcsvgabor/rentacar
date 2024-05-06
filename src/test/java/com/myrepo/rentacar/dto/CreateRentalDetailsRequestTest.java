package com.myrepo.rentacar.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateRentalDetailsRequestTest {


    @Test
    void isValid() {

        //Arrange
        boolean target = true;

        //Act
        CreateRentalDetailsRequest createRentalDetailsRequest = new CreateRentalDetailsRequest();
        createRentalDetailsRequest.setEmail("asd@gmail.com");
        createRentalDetailsRequest.setPassword("Pw1234567");
        createRentalDetailsRequest.setRole("customer");

        //Assert
        assertEquals(target, createRentalDetailsRequest.isValid());
    }
}