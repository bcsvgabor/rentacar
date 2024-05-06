package com.myrepo.rentacar.entities;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerRecordTest {

    @Test
    void modifyAtCustomerRecordTest() {
        // Arrange
        LocalDateTime expected = LocalDateTime.now();

        // Act
        CustomerRecord cr = new CustomerRecord();
        cr.modifyAtCustomerRecord();

        // Assert
        assertEquals(expected, cr.getCreatedAt());

    }

}