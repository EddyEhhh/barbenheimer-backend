package com.barbenheimer.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barbenheimer.customer.model.CustomerDetail;
import java.util.List;


public interface CustomerDetailRepository extends JpaRepository<CustomerDetail,Long>{
    
    public CustomerDetail findById(long id);

    public CustomerDetail findByEmail(String email);
}
