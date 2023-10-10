package com.barbenheimer.movieservice.repository;

import com.barbenheimer.movieservice.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    public Purchase findByCustomerDetail_Email(String email);

}
