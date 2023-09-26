package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.exception.ResourceNotFoundException;
import com.distinction.barbenheimer.model.SeatPurchase;
import com.distinction.barbenheimer.repository.PurchaseRepository;
import com.distinction.barbenheimer.repository.SeatPurchaseRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SeatPurchaseServiceImpl implements SeatPurchaseService {

    private SeatPurchaseRepository seatPurchaseRepository;

    private PurchaseRepository purchaseRepository;

    @Autowired
    public SeatPurchaseServiceImpl(SeatPurchaseRepository seatPurchaseRepository, PurchaseRepository purchaseRepository){
        this.seatPurchaseRepository = seatPurchaseRepository;
    }

    public String createCustomerIdentifyingToken(){
        StringBuilder token = new StringBuilder();
        long currentTimeInMillisecond = Instant.now().toEpochMilli();
        token.append(currentTimeInMillisecond).append(UUID.randomUUID().toString());
        return token.toString().replace("-","");
    }


    //TODO: add seat to be stored

    /**
     * This method stores an ongoing purchase into the database
     * @param httpSession
     */
    public ResponseEntity<?> saveOngoingPurchase(HttpSession httpSession){
        SeatPurchase seatPurchase = SeatPurchase.builder()
                .token((String) httpSession.getAttribute("customerToken"))
                .timestamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(seatPurchaseRepository.save(seatPurchase));
    }


    public void deleteOngoingPurchase(HttpSession httpSession){
        String customerIdentifyingToken = String.valueOf(httpSession.getAttribute("customerToken"));
        SeatPurchase seatPurchase = seatPurchaseRepository.findByToken(customerIdentifyingToken)
                .orElseThrow(() -> new ResourceNotFoundException("SeatPurchase with " + customerIdentifyingToken + " is not found."));
       seatPurchaseRepository.delete(seatPurchase);
    }



}
