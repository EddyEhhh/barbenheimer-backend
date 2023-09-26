package com.distinction.barbenheimer.controller;

import com.distinction.barbenheimer.DTO.OngoingPurchaseDetailDTO;
import com.distinction.barbenheimer.DTO.OngoingPurchaseTokenDTO;
import com.distinction.barbenheimer.DTO.PurchaseDTO;
import com.distinction.barbenheimer.DTO.SeatStatusDetailDTO;
import com.distinction.barbenheimer.service.OngoingPurchaseService;
import com.distinction.barbenheimer.service.PurchaseService;
import com.distinction.barbenheimer.service.SeatPurchaseService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/pay")
@Slf4j
public class PaymentController {

    private final SeatPurchaseService seatPurchaseService;

    private final PurchaseService purchaseService;

    private OngoingPurchaseService ongoingPurchaseService;

    @Autowired
    public PaymentController(SeatPurchaseService seatPurchaseService, PurchaseService purchaseService, OngoingPurchaseService ongoingPurchaseService){
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

    @PostMapping("/purchase")
    public ResponseEntity<?> savePurchase(SeatStatusDetailDTO seatStatusDetailDTO, PurchaseDTO purchaseDTO, HttpSession httpSession){
        seatPurchaseService.deleteOngoingPurchase(httpSession);
        return purchaseService.savePurchase(seatStatusDetailDTO, purchaseDTO, httpSession);
    }


}
