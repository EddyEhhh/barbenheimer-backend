package com.distinction.barbenheimer.repository;

import com.distinction.barbenheimer.model.CustomerDetail;
import com.distinction.barbenheimer.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CustomerDetailRepository extends JpaRepository<CustomerDetail, Long> {

}
