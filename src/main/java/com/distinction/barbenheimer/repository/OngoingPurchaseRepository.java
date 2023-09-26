package com.distinction.barbenheimer.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.distinction.barbenheimer.model.OngoingPurchase;
import java.util.List;
import com.distinction.barbenheimer.model.SeatStatus;



@Repository
public interface OngoingPurchaseRepository extends JpaRepository<OngoingPurchase, Long>{
    
    public OngoingPurchase findBySeatStatus(SeatStatus seatStatus);

    public OngoingPurchase findByPurchaseToken(String purchaseToken);

}
