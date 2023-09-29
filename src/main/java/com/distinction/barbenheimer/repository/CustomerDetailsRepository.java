package com.distinction.barbenheimer.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.distinction.barbenheimer.model.CustomerDetail;

public interface CustomerDetailsRepository extends JpaRepository<CustomerDetail,Long>{
    
}
