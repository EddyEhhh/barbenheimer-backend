package com.barbenheimer.movieservice.service;


import com.barbenheimer.movieservice.dto.OngoingPurchaseShortDTO;
import com.barbenheimer.movieservice.dto.OngoingPurchaseTokenDTO;
import com.barbenheimer.movieservice.dto.PurchaseDTO;
import com.barbenheimer.movieservice.dto.PurchaseShortDTO;
import com.barbenheimer.movieservice.model.Purchase;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import org.springframework.http.ResponseEntity;


public interface PurchaseService{

    public void sendMail(Purchase purchase);

     public ResponseEntity<PurchaseShortDTO> getPurchaseByPaymentIntent(OngoingPurchaseTokenDTO ongoingPurchaseTokenDTO) throws StripeException;

     public ResponseEntity<?> paymentIntentStatus(String payload, String sigHeader);

}
