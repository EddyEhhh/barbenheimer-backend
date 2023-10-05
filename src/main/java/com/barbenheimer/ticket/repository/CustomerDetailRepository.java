package com.barbenheimer.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.barbenheimer.ticket.model.CustomerDetail;


public interface CustomerDetailRepository extends JpaRepository<CustomerDetail,Long>{
    
    public CustomerDetail findById(long id);

    public CustomerDetail findByEmail(String email);
}
