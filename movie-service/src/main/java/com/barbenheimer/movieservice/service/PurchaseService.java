package com.barbenheimer.movieservice.service;


import com.barbenheimer.movieservice.dto.PurchaseDTO;
import com.barbenheimer.movieservice.dto.PurchaseShortDTO;
import com.barbenheimer.movieservice.model.Purchase;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import org.springframework.http.ResponseEntity;


public interface PurchaseService{

    public ResponseEntity<?> savePurchase(Session session);

    public void sendMail(Purchase purchase);

    public PurchaseShortDTO getPurchaseByCheckoutSession(String checkoutSessionId) throws StripeException;

    public ResponseEntity<?> checkoutSessionStatus(String payload, String sigHeader);
}
