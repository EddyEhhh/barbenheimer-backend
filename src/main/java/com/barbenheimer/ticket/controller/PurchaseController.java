package com.barbenheimer.ticket.controller;

import com.barbenheimer.ticket.dto.*;
import com.barbenheimer.ticket.service.OngoingPurchaseService;
import com.barbenheimer.ticket.service.PurchaseService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/payment")
@Slf4j
public class PurchaseController {


    private final PurchaseService purchaseService;

    private OngoingPurchaseService ongoingPurchaseService;

    @Autowired
    public PurchaseController(PurchaseService purchaseService, OngoingPurchaseService ongoingPurchaseService){
        this.purchaseService = purchaseService;
        this.ongoingPurchaseService = ongoingPurchaseService;
    }

    @GetMapping
    public ResponseEntity<OngoingPurchaseDetailDTO> getOngoingPurchaseDetail(@RequestBody OngoingPurchaseTokenDTO ongoingPurchaseTokenDTO){
        log.info("token: " + ongoingPurchaseTokenDTO.getToken());
        return ResponseEntity.ok(ongoingPurchaseService.getDetail(ongoingPurchaseTokenDTO));
    }

//    //TODO: add seat to be stored
//    @PostMapping("/ongoingPurchase")
//    public ResponseEntity<?> saveOngoingPurchase(HttpSession httpSession){
//        httpSession.setAttribute("customerToken", seatPurchaseService.createCustomerIdentifyingToken());
//        return ResponseEntity.ok(seatPurchaseService.saveOngoingPurchase(httpSession));
//    }

    //TODO: revise this
//    @PostMapping("/purchase")
//    public ResponseEntity<?> savePurchase(@RequestBody SeatStatusDetailDTO seatStatusDetailDTO, @RequestBody PurchaseDTO purchaseDTO, HttpSession httpSession){
//        seatPurchaseService.deleteOngoingPurchase(httpSession);
//        return purchaseService.savePurchase(seatStatusDetailDTO, purchaseDTO, httpSession);
//    }

    @PostMapping(value = "/paymentIntent", consumes = "application/json")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PurchaseDTO purchaseDTO) throws StripeException {
        PaymentIntent paymentIntent = purchaseService.createPaymentIntent(purchaseDTO);
        return ResponseEntity.ok(paymentIntent);
    }

    @PostMapping(value = "/webhook")
    public ResponseEntity<?> updatePaymentIntentStatus(@RequestBody String payload, @RequestHeader("Stripe-Signature") String sigHeader) {
        return purchaseService.updatePaymentIntentStatus(payload, sigHeader);
    }


}
