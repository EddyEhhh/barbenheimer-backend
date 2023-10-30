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

    private OngoingPurchaseService ongoingPurchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService, OngoingPurchaseService ongoingPurchaseService){
        this.purchaseService = purchaseService;
        this.ongoingPurchaseService = ongoingPurchaseService;
    }

    /**
     * This method gets an ongoing purchase's details with its token.
     * @param paymentIntentId
     * @return ResponseEntity<OngoingPurchaseDetailDTO>
     */
    @GetMapping("/{paymentIntentId}")
    public ResponseEntity<OngoingPurchaseDetailDTO> getOngoingPurchaseDetail(@PathVariable String paymentIntentId){
        return ResponseEntity.ok(ongoingPurchaseService.getDetail(paymentIntentId));
    }


    /**
     * This method checks if an ongoing purchase is still valid.
     * @param paymentIntentId
     * @return ResponseEntity<?>
     * @throws StripeException
     */
    @GetMapping("/checkToken/{paymentIntentId}")
    public ResponseEntity<?> checkIfValidToken(@PathVariable String paymentIntentId) throws StripeException {
        PaymentIntentValidationDTO paymentIntentValidationDTO = ongoingPurchaseService.checkIfValidToken(paymentIntentId);
        return ResponseEntity.ok(paymentIntentValidationDTO);
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
