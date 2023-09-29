package com.distinction.barbenheimer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.distinction.barbenheimer.model.CustomerDetail;

public interface CustomerDetailRepository extends JpaRepository<CustomerDetail,Long>{
    
}
