package com.barbenheimer.movieservice.controller;

import com.barbenheimer.movieservice.dto.OngoingPurchaseDetailDTO;
import com.barbenheimer.movieservice.dto.OngoingPurchaseTokenDTO;
import com.barbenheimer.movieservice.dto.*;
import com.barbenheimer.movieservice.service.OngoingPurchaseService;
import com.barbenheimer.movieservice.service.PurchaseService;
import com.stripe.exception.StripeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/payments")
@Slf4j
public class PurchaseController {


    private final PurchaseService purchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService){
        this.purchaseService = purchaseService;
    }


    /**
     * This method gets the purchase linked to a payment intent id for the frontend to display its details on a payment summary page.
     * @param paymentIntentId
     * @return ResponseEntity<PurchaseShortDTO>
     * @throws StripeException
     */
    @GetMapping("/getPurchase/{paymentIntentId}")
    public ResponseEntity<PurchaseShortDTO> getPurchaseByPaymentIntent(@PathVariable String paymentIntentId) throws StripeException {
        return ResponseEntity.ok(purchaseService.getPurchaseByPaymentIntent(paymentIntentId));
    }



    /**
     * This method is called by Stripe automatically when a payment is completed by a customer.
     * @param payload
     * @param sigHeader
     * @return ResponseEntity<?>
     */
    @PostMapping(value = "/webhook")
    public ResponseEntity<?> paymentIntentStatus(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        return purchaseService.paymentIntentStatus(payload, sigHeader);
    }




}
