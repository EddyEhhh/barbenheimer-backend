package com.distinction.barbenheimer.controller;

import com.distinction.barbenheimer.DTO.SeatSelectDTO;
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
public class SeatPurchaseController {

    private final SeatPurchaseService seatPurchaseService;

    @Autowired
    public SeatPurchaseController(SeatPurchaseService seatPurchaseService){
        this.seatPurchaseService = seatPurchaseService;
    }

    //Things this needs: Customer Detail and Seat Selected

    @GetMapping
    public ResponseEntity<?> purchaseSeats(@RequestBody SeatSelectDTO seatSelectDTO, HttpSession httpSession){
        httpSession.setAttribute("customerToken", seatPurchaseService.createCustomerIdentifyingToken());
        httpSession.setAttribute("seatSelectDTO", seatSelectDTO);
        return ResponseEntity.ok(httpSession.getAttribute("customerToken"));
    }

//    @GetMapping("/check")
//    public ResponseEntity<?> checkSomething(HttpSession httpSession){
//        SeatSelectDTO seatSelectDTO = (SeatSelectDTO) httpSession.getAttribute("seatSelectDTO");
//        return ResponseEntity.ok(seatSelectDTO.toString());
//    }




}
