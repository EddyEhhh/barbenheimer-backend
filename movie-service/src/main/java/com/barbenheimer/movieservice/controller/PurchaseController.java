package com.barbenheimer.movieservice.controller;

import com.barbenheimer.movieservice.dto.OngoingPurchaseDetailDTO;
import com.barbenheimer.movieservice.dto.OngoingPurchaseTokenDTO;
import com.barbenheimer.movieservice.dto.*;
import com.barbenheimer.movieservice.service.OngoingPurchaseService;
import com.barbenheimer.movieservice.service.PurchaseService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/payments")
@Slf4j
public class PurchaseController {


    private final PurchaseService purchaseService;

    private OngoingPurchaseService ongoingPurchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService, OngoingPurchaseService ongoingPurchaseService){
        this.purchaseService = purchaseService;
        this.ongoingPurchaseService = ongoingPurchaseService;
    }

    /**
     * This method gets an ongoing purchase's details with its token.
     * @param ongoingPurchaseTokenDTO
     * @return ResponseEntity<OngoingPurchaseDetailDTO>
     */
    @GetMapping
    public ResponseEntity<OngoingPurchaseDetailDTO> getOngoingPurchaseDetail(@RequestBody OngoingPurchaseTokenDTO ongoingPurchaseTokenDTO){
        log.info("token: " + ongoingPurchaseTokenDTO.getToken());
        return ResponseEntity.ok(ongoingPurchaseService.getDetail(ongoingPurchaseTokenDTO));
    }


    /**
     * This method is called when the customer is directed the payment summary page after successful payment.
     * It returns a PurchaseShortDTO containing necessary payment details to show as a post-payment summary.
     * @param paymentIntentId
     * @return ResponseEntity<PurchaseShortDTO>
     * @throws StripeException
     */
    @GetMapping("/{paymentIntentId}")
    public ResponseEntity<PurchaseShortDTO> getPurchaseByPaymentIntent(@PathVariable("paymentIntentId") String paymentIntentId ) throws StripeException {
        PurchaseShortDTO purchaseShortDTO = purchaseService.getPurchaseByPaymentIntent(paymentIntentId);
        return ResponseEntity.ok(purchaseShortDTO);
    }


    /**
     * This method is called when the customer has selected their desired seats and is now ready for payment.
     * @param purchaseDTO
     * @return ResponseEntity<?> containing PaymentIntent
     * @throws StripeException
     */

    @PostMapping(value = "/paymentIntent", consumes = "application/json")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PurchaseDTO purchaseDTO) throws StripeException {
        PaymentIntent paymentIntent = purchaseService.createPaymentIntent(purchaseDTO);
        return ResponseEntity.ok(paymentIntent);
    }


    /**
     * This method is called by Stripe automatically when a payment intent created for a customer has its status changed.
     * E.g. from payment_intent.processing to payment_intent.succeeded
     * @param payload
     * @param sigHeader
     * @return ResponseEntity<?>
     */
    @PostMapping(value = "/webhook")
    public ResponseEntity<?> updatePaymentIntentStatus(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        return purchaseService.updatePaymentIntentStatus(payload, sigHeader);
    }




}
