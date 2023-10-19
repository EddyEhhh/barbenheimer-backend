package com.barbenheimer.movieservice.service;


import com.barbenheimer.movieservice.dto.PurchaseDTO;
import com.barbenheimer.movieservice.dto.PurchaseShortDTO;
import com.barbenheimer.movieservice.model.Purchase;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.http.ResponseEntity;


public interface PurchaseService{

    public ResponseEntity<?> savePurchase(PaymentIntent paymentIntent);
    public PaymentIntent createPaymentIntent(PurchaseDTO purchaseDTO) throws StripeException;

    //public ResponseEntity<?> updatePaymentIntentStatus(HttpServletRequest httpServletRequest) throws IOException;

    public ResponseEntity<?> updatePaymentIntentStatus(String payload, String sigHeader);

    public void sendMail(Purchase purchase);

    public PurchaseShortDTO getPurchaseByPaymentIntent(String paymentIntentId) throws StripeException;
}
