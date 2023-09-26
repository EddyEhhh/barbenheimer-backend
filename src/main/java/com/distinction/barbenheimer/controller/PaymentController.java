package com.distinction.barbenheimer.controller;

import com.distinction.barbenheimer.DTO.PurchaseDTO;
import com.distinction.barbenheimer.DTO.SeatStatusDetailDTO;
import com.distinction.barbenheimer.service.PurchaseService;
import com.distinction.barbenheimer.service.SeatPurchaseService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/pay")
@Slf4j
public class PaymentController {

    private final SeatPurchaseService seatPurchaseService;

    private final PurchaseService purchaseService;

    @Autowired
    public PaymentController(SeatPurchaseService seatPurchaseService, PurchaseService purchaseService){
        this.seatPurchaseService = seatPurchaseService;
        this.purchaseService = purchaseService;
    }

    //TODO: add seat to be stored
    @PostMapping("/ongoingPurchase")
    public ResponseEntity<?> saveOngoingPurchase(HttpSession httpSession){
        httpSession.setAttribute("customerToken", seatPurchaseService.createCustomerIdentifyingToken());
        return ResponseEntity.ok(seatPurchaseService.saveOngoingPurchase(httpSession));
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> savePurchase(SeatStatusDetailDTO seatStatusDetailDTO, PurchaseDTO purchaseDTO, HttpSession httpSession){
        seatPurchaseService.deleteOngoingPurchase(httpSession);
        return purchaseService.savePurchase(seatStatusDetailDTO, purchaseDTO, httpSession);
    }


}
