package com.barbenheimer.movieservice.repository;

import com.barbenheimer.movieservice.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    public Optional<Purchase> findByPaymentIntentId(String paymentIntentId);

}
