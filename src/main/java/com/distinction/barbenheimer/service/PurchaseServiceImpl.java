package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.DTO.PurchaseDTO;
import com.distinction.barbenheimer.DTO.SeatStatusDetailDTO;
import com.distinction.barbenheimer.exception.ResourceNotFoundException;
import com.distinction.barbenheimer.model.Purchase;
import com.distinction.barbenheimer.model.SeatPurchase;
import com.distinction.barbenheimer.repository.PurchaseRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    private PurchaseRepository purchaseRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository){
        this.purchaseRepository = purchaseRepository;
    }


    @Override
    public ResponseEntity<?> savePurchase(SeatStatusDetailDTO seatStatusDetailDTO, PurchaseDTO purchaseDTO, HttpSession httpSession) {
        Purchase purchase = Purchase.builder()
                .dateTime(LocalDateTime.now())
                .paidAmount(purchaseDTO.getPriceInCents() * purchaseDTO.getQuantity())
                .build();
        return ResponseEntity.ok(purchaseRepository.save(purchase));
    }
}
