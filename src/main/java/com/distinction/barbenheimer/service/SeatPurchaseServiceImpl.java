package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.model.SeatPurchase;
import com.distinction.barbenheimer.repository.SeatPurchaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class SeatPurchaseServiceImpl implements SeatPurchaseService {

    private SeatPurchaseRepository seatPurchaseRepository;

    @Autowired
    public SeatPurchaseServiceImpl(SeatPurchaseRepository seatPurchaseRepository){
        this.seatPurchaseRepository = seatPurchaseRepository;
    }

    public String createCustomerIdentifyingToken(){
        StringBuilder token = new StringBuilder();
        long currentTimeInMillisecond = Instant.now().toEpochMilli();
        token.append(currentTimeInMillisecond).append(UUID.randomUUID().toString());
        return token.toString().replace("-","");
    }

//    private boolean existToken(String token){
//        return seatPurchaseRepository.findByToken(token).isPresent();
//    }

    public void purchaseSeats(){
        //TODO: implement this
    }


}
