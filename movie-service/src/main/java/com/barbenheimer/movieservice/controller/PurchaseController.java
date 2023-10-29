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
     * This method creates an ongoing purchase.
     * @param ongoingPurchaseShortDTO
     * @return ResponseEntity<?>
     */
    @PostMapping
    public ResponseEntity<?> createOngoingPurchase(@RequestBody OngoingPurchaseShortDTO ongoingPurchaseShortDTO){
        return purchaseService.createOngoingPurchase(ongoingPurchaseShortDTO);
    }


    /**
     * This method checks if an ongoing purchase is still valid.
     * @param paymentIntentId
     * @return ResponseEntity<?>
     * @throws StripeException
     */
    @GetMapping("/checkToken")
    public ResponseEntity<?> checkIfValidToken(@RequestBody String paymentIntentId) throws StripeException {
        return purchaseService.checkIfValidToken(paymentIntentId);
    }

    /**
     * This method gets the purchase linked to a payment intent id for the frontend to display its details on a payment summary page.
     * @param paymentIntentId
     * @return ResponseEntity<PurchaseShortDTO>
     * @throws StripeException
     */
    @GetMapping("/{paymentIntentId}")
    public ResponseEntity<PurchaseShortDTO> getPurchaseByPaymentIntent(@PathVariable("paymentIntentId") String paymentIntentId) throws StripeException {
        return purchaseService.getPurchaseByPaymentIntent(paymentIntentId);
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
