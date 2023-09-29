package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.DTO.PurchaseResponseDTO;
import com.distinction.barbenheimer.DTO.PurchaseDTO;
import com.distinction.barbenheimer.DTO.SeatStatusDetailDTO;
import com.distinction.barbenheimer.model.Purchase;
import com.distinction.barbenheimer.repository.PurchaseRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Value("${STRIPE_PRIVATE_KEY}")
    private String stripeApiKey;

    private PurchaseRepository purchaseRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository){
        this.purchaseRepository = purchaseRepository;
    }

    @Override
    public ResponseEntity<?> savePurchase(SeatStatusDetailDTO seatStatusDetailDTO, PurchaseDTO purchaseDTO, HttpSession httpSession) {
        Purchase purchase = Purchase.builder()
                .dateTime(LocalDateTime.now())
                .paidAmount(purchaseDTO.getPriceInCents() * purchaseDTO.getQuantity())
                .build();
        return ResponseEntity.ok(purchaseRepository.save(purchase));
    }

    public PurchaseResponseDTO createPaymentIntent(PurchaseDTO purchaseDTO) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(purchaseDTO.getQuantity() * purchaseDTO.getPriceInCents())
                .setCurrency("sgd")
                .build();
        PaymentIntent paymentIntent = PaymentIntent.create(params);
        PurchaseResponseDTO purchaseResponseDTO = PurchaseResponseDTO.builder()
                .clientSecret(paymentIntent.getClientSecret())
                .build();
        return purchaseResponseDTO;
    }




}
