package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.DTO.PurchaseResponseDTO;
import com.distinction.barbenheimer.DTO.PurchaseDTO;
import com.distinction.barbenheimer.DTO.SeatStatusDetailDTO;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

public interface PurchaseService{

    public ResponseEntity<?> savePurchase(SeatStatusDetailDTO seatStatusDetailDTO, PurchaseDTO purchaseDTO, HttpSession httpSession);
    public PurchaseResponseDTO createPaymentIntent(PurchaseDTO purchaseDTO) throws StripeException;
}
