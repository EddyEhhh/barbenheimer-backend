package com.barbenheimer.ticket.serviceImpl;


import com.barbenheimer.ticket.dto.PurchaseDTO;
import com.barbenheimer.ticket.dto.SeatStatusDetailDTO;
import com.barbenheimer.ticket.model.CustomerDetail;
import com.barbenheimer.ticket.model.Purchase;
import com.barbenheimer.ticket.repository.PurchaseRepository;
import com.barbenheimer.ticket.service.PurchaseService;
import com.google.gson.JsonSyntaxException;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
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

    @Value("${WEBHOOK_SECRET}")
    private String endpointSecret;

    private PurchaseRepository purchaseRepository;

    public PurchaseServiceImpl(PurchaseRepository purchaseRepository){
        this.purchaseRepository = purchaseRepository;
    }

    //TODO: define the clock
    @Override
    public ResponseEntity savePurchase(CustomerDetail customerDetail, SeatStatusDetailDTO seatStatusDetailDTO, PurchaseDTO purchaseDTO) {
        Purchase purchase = Purchase.builder()
                .customerDetail(customerDetail)
                .dateTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .paidAmount(purchaseDTO.getPriceInCents() * purchaseDTO.getQuantity())
                .build();
        return ResponseEntity.ok(purchaseRepository.save(purchase));
    }

    public PaymentIntent createPaymentIntent(PurchaseDTO purchaseDTO) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(purchaseDTO.getQuantity() * purchaseDTO.getPriceInCents())
                .setCurrency("sgd")
                .build();
        return PaymentIntent.create(params);
    }

    public ResponseEntity<?> updatePaymentIntentStatus(String payload, String sigHeader){
        Event event;

        try{
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (JsonSyntaxException | SignatureVerificationException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }

//        PaymentIntent paymentIntent = (PaymentIntent) event
//                .getDataObjectDeserializer()
//                .getObject()
//                .get();

        switch (event.getType()) {
            case "payment_intent.succeeded": {
                //TODO: replace with save purchase function
                purchaseRepository.save(Purchase.builder()
                                .paidAmount(800)
                                .dateTime(LocalDateTime.now())
                                .build());
                return ResponseEntity.status(200).body("Payment Succeeded");
            }
            // ... handle other event types
            default:
                System.out.println("Unhandled event type: " + event.getType());
        }

        return ResponseEntity.ok("Customer purchase succeeded and saved.");
    }




}
