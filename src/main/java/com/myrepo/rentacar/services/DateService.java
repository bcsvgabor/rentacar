package com.myrepo.rentacar.services;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;

public interface DateService {

    ZonedDateTime getCurrentDateTime();

    LocalDate getDateOfBirth(Date date);
}
