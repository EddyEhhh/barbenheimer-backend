package com.barbenheimer.movieservice.service;

import com.barbenheimer.movieservice.dto.PurchaseDTO;
import com.barbenheimer.movieservice.dto.PurchaseResponseDTO;
import com.barbenheimer.movieservice.dto.SeatStatusDetailDTO;
import com.barbenheimer.movieservice.model.CustomerDetail;
import com.barbenheimer.movieservice.model.Purchase;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;

public interface PurchaseService{

    public ResponseEntity<?> savePurchase(CustomerDetail customerDetail, SeatStatusDetailDTO seatStatusDetailDTO, PurchaseDTO purchaseDTO);
    public PurchaseResponseDTO createPaymentIntent(PurchaseDTO purchaseDTO) throws StripeException;

    public void sendMail(Purchase purchase);


}
