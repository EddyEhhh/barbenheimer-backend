package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.dto.PurchaseResponseDTO;
import com.distinction.barbenheimer.dto.PurchaseDTO;
import com.distinction.barbenheimer.dto.SeatStatusDetailDTO;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

public interface PurchaseService{

    public ResponseEntity<?> savePurchase(SeatStatusDetailDTO seatStatusDetailDTO, PurchaseDTO purchaseDTO, HttpSession httpSession);
    public PurchaseResponseDTO createPaymentIntent(PurchaseDTO purchaseDTO) throws StripeException;
}
