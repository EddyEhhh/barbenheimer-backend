package com.barbenheimer.ticket.service;

import com.barbenheimer.customer.model.CustomerDetail;
import com.barbenheimer.ticket.dto.PurchaseDTO;
import com.barbenheimer.ticket.dto.SeatStatusDetailDTO;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface PurchaseService{

    public ResponseEntity<?> savePurchase(CustomerDetail customerDetail, SeatStatusDetailDTO seatStatusDetailDTO, PurchaseDTO purchaseDTO);
    public PaymentIntent createPaymentIntent(PurchaseDTO purchaseDTO) throws StripeException;

    //public ResponseEntity<?> updatePaymentIntentStatus(HttpServletRequest httpServletRequest) throws IOException;

    ResponseEntity<?> updatePaymentIntentStatus(String payload, String sigHeader);
}
