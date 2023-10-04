package com.barbenheimer.ticket.service;

import com.barbenheimer.ticket.dto.PurchaseDTO;
import com.barbenheimer.ticket.dto.PurchaseResponseDTO;
import com.barbenheimer.ticket.dto.SeatStatusDetailDTO;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

public interface PurchaseService{

    public ResponseEntity<?> savePurchase(SeatStatusDetailDTO seatStatusDetailDTO, PurchaseDTO purchaseDTO, HttpSession httpSession);
    public PurchaseResponseDTO createPaymentIntent(PurchaseDTO purchaseDTO) throws StripeException;
}
