package com.distinction.barbenheimer.service;


import com.distinction.barbenheimer.DTO.PurchaseDTO;
import com.distinction.barbenheimer.DTO.SeatStatusDetailDTO;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface SeatPurchaseService {

        public String createCustomerIdentifyingToken();

        public ResponseEntity<?> saveOngoingPurchase(HttpSession httpSession);

        public void deleteOngoingPurchase(HttpSession httpSession);
}
