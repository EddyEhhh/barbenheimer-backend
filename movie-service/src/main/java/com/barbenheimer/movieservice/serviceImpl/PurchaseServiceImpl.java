package com.barbenheimer.movieservice.serviceImpl;

import com.barbenheimer.movieservice.dto.PurchaseDTO;
import com.barbenheimer.movieservice.dto.PurchaseResponseDTO;
import com.barbenheimer.movieservice.dto.SeatStatusDetailDTO;
import com.barbenheimer.movieservice.model.CustomerDetail;
import com.barbenheimer.movieservice.model.Purchase;
import com.barbenheimer.movieservice.repository.PurchaseRepository;
import com.barbenheimer.movieservice.service.PurchaseService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Value("${STRIPE_PRIVATE_KEY}")
    private String stripeApiKey;

    private PurchaseRepository purchaseRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository){
        this.purchaseRepository = purchaseRepository;
    }

    //TODO: define the clock
    @Override
    public ResponseEntity<?> savePurchase(CustomerDetail customerDetail, SeatStatusDetailDTO seatStatusDetailDTO, PurchaseDTO purchaseDTO) {
        Purchase purchase = Purchase.builder()
                .customerDetail(customerDetail)
                .dateTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
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