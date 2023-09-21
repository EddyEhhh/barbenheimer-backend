package com.distinction.barbenheimer.repository;


import com.distinction.barbenheimer.model.SeatPurchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatPurchaseRepository extends JpaRepository<SeatPurchase, Long> {

    public Optional<SeatPurchase> findByToken(String token);

}
