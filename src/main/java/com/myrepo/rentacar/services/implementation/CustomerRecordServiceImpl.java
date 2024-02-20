package com.myrepo.rentacar.services.implementation;

import com.myrepo.rentacar.dto.CreateCustomerRequest;
import com.myrepo.rentacar.entities.CustomerRecord;
import com.myrepo.rentacar.repositories.CustomerRecordRepository;
import com.myrepo.rentacar.services.CustomerRecordService;
import com.myrepo.rentacar.services.RentalUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerRecordServiceImpl implements CustomerRecordService {

    private final CustomerRecordRepository customerRecordRepository;
    private final RentalUserService rentalUserService;

    @Override
    public void createCustomerRecord(CreateCustomerRequest createCustomerRequest) {

        CustomerRecord customerRecord = new CustomerRecord();

        customerRecord.setFullName(createCustomerRequest.getFullName());
        customerRecord.setFoxUser(rentalUserService.findByEmail(createCustomerRequest.getEmail()));
        customerRecordRepository.save(customerRecord);
    }
}
