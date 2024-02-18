package com.myrepo.rentacar.services.implementation;

import com.myrepo.rentacar.services.DateService;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Date;

@Service
public class DateServiceImpl implements DateService {

    public ZonedDateTime getCurrentDateTime() {
        LocalDateTime localNow = LocalDateTime.now();
        ZonedDateTime zonedUTC = localNow.atZone(ZoneId.of("UTC"));
        ZonedDateTime zonedIST = zonedUTC.withZoneSameInstant(ZoneId.of("Europe/Budapest"));
        return zonedIST;
    }

    @Override
    public LocalDate getDateOfBirth(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zone = instant.atZone(ZoneId.systemDefault());
        return zone.toLocalDate();
    }
}
