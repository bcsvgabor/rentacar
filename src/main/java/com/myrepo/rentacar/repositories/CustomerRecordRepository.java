package com.myrepo.rentacar.repositories;

import com.myrepo.rentacar.entities.CustomerRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRecordRepository extends JpaRepository<CustomerRecord, Long> {

    CustomerRecord findCustomerRecordByFoxUserId(Long foxUser_id);
}
