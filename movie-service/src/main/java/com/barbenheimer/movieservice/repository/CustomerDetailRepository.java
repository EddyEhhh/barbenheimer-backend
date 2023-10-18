package com.barbenheimer.movieservice.repository;

import com.barbenheimer.movieservice.model.CustomerDetail;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CustomerDetailRepository extends JpaRepository<CustomerDetail,Long>{

    public CustomerDetail findByEmail(String email);
}
