package com.barbenheimer.customer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barbenheimer.customer.model.CustomerDetail;

public interface CustomerDetailRepository extends JpaRepository<CustomerDetail,Long>{
    
}
