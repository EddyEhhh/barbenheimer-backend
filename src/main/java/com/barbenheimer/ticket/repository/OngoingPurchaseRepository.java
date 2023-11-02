package com.barbenheimer.ticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.barbenheimer.ticket.model.OngoingPurchase;

import java.util.Optional;

import com.barbenheimer.ticket.model.SeatStatus;



@Repository
public interface OngoingPurchaseRepository extends JpaRepository<OngoingPurchase, Long>{
    
    public OngoingPurchase findBySeatStatus(SeatStatus seatStatus);

    public Optional<OngoingPurchase> findByToken(String purchaseToken);

}
