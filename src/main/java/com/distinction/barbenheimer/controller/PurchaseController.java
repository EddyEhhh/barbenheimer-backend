package com.distinction.barbenheimer.controller;

import com.distinction.barbenheimer.dto.*;
import com.distinction.barbenheimer.service.OngoingPurchaseService;
import com.distinction.barbenheimer.service.PurchaseService;
import com.distinction.barbenheimer.service.SeatPurchaseService;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/pay")
@Slf4j
public class PurchaseController {

    private final SeatPurchaseService seatPurchaseService;

    private final PurchaseService purchaseService;

    private OngoingPurchaseService ongoingPurchaseService;

    @Autowired
    public PurchaseController(SeatPurchaseService seatPurchaseService, PurchaseService purchaseService, OngoingPurchaseService ongoingPurchaseService){
        this.seatPurchaseService = seatPurchaseService;
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
    @PostMapping("/purchase")
    public ResponseEntity<?> savePurchase(@RequestBody SeatStatusDetailDTO seatStatusDetailDTO, @RequestBody PurchaseDTO purchaseDTO, HttpSession httpSession){
        seatPurchaseService.deleteOngoingPurchase(httpSession);
        return purchaseService.savePurchase(seatStatusDetailDTO, purchaseDTO, httpSession);
    }

    @PostMapping(value = "/paymentIntent", consumes = "application/json")
    public ResponseEntity<?> createPaymentIntent(@RequestBody PurchaseDTO purchaseDTO) throws StripeException {
        PurchaseResponseDTO purchaseResponseDTO = purchaseService.createPaymentIntent(purchaseDTO);
        return ResponseEntity.ok(purchaseResponseDTO);
    }






}
