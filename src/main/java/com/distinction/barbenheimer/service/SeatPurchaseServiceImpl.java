package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.exception.ResourceNotFoundException;
import com.distinction.barbenheimer.model.OngoingPurchase;
import com.distinction.barbenheimer.repository.OngoingPurchaseRepository;
import com.distinction.barbenheimer.repository.PurchaseRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class SeatPurchaseServiceImpl implements SeatPurchaseService {

    private OngoingPurchaseRepository ongoingPurchaseRepository;

    private PurchaseRepository purchaseRepository;

    @Autowired
    public SeatPurchaseServiceImpl(OngoingPurchaseRepository ongoingPurchaseRepository, PurchaseRepository purchaseRepository){
        this.ongoingPurchaseRepository = ongoingPurchaseRepository;
    }




    //TODO: add seat to be stored

    /**
     * This method stores an ongoing purchase into the database
     * @param httpSession
     */
    public ResponseEntity<?> saveOngoingPurchase(HttpSession httpSession){
        OngoingPurchase ongoingPurchase = OngoingPurchase.builder()
                .token((String) httpSession.getAttribute("customerToken"))
                .expireTimeStamp(LocalDateTime.now())
                .build();
        return ResponseEntity.ok(ongoingPurchaseRepository.save(ongoingPurchase));
    }


    public void deleteOngoingPurchase(HttpSession httpSession){
        String customerIdentifyingToken = String.valueOf(httpSession.getAttribute("customerToken"));
        OngoingPurchase seatPurchase = ongoingPurchaseRepository.findByToken(customerIdentifyingToken)
                .orElseThrow(() -> new ResourceNotFoundException("SeatPurchase with " + customerIdentifyingToken + " is not found."));
        ongoingPurchaseRepository.delete(seatPurchase);
    }



}
