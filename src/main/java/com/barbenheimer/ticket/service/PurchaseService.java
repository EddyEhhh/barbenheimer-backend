package com.barbenheimer.ticket.service;

import com.barbenheimer.customer.model.CustomerDetail;
import com.barbenheimer.ticket.dto.PurchaseDTO;
import com.barbenheimer.ticket.dto.PurchaseResponseDTO;
import com.barbenheimer.ticket.dto.SeatStatusDetailDTO;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;

import java.time.Clock;

public interface PurchaseService{

    public ResponseEntity<?> savePurchase(CustomerDetail customerDetail, SeatStatusDetailDTO seatStatusDetailDTO, PurchaseDTO purchaseDTO, Clock clock);
    public PurchaseResponseDTO createPaymentIntent(PurchaseDTO purchaseDTO) throws StripeException;
}
