package com.barbenheimer.movieservice.repository;

import com.barbenheimer.movieservice.model.CustomerDetail;
import com.stripe.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CustomerDetailRepository extends JpaRepository<CustomerDetail,Long>{

    public Optional<CustomerDetail> findFirstByEmail(String email);
}
