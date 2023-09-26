package com.distinction.barbenheimer.controller;

import com.distinction.barbenheimer.DTO.PaymentDTO;
import com.distinction.barbenheimer.service.SeatPurchaseService;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/pay")
@Slf4j
public class PaymentController {

    private final SeatPurchaseService seatPurchaseService;

    @Autowired
    public PaymentController(SeatPurchaseService seatPurchaseService){
        this.seatPurchaseService = seatPurchaseService;
    }

    @PostMapping("/create-checkout-session")
    public String checkout (@RequestBody PaymentDTO paymentDTO, HttpServletResponse response, HttpSession httpSession) throws StripeException, IOException {
//        httpSession.setAttribute("seatSelectDTO", seatSelectDTO);
        return seatPurchaseService.checkout(paymentDTO, response, httpSession);
    }

//    @GetMapping
//    public ResponseEntity<?> purchaseSeats(@RequestBody SeatSelectDTO seatSelectDTO, HttpSession httpSession){
//        httpSession.setAttribute("customerToken", seatPurchaseService.createCustomerIdentifyingToken());
//        httpSession.setAttribute("seatSelectDTO", seatSelectDTO);
//        return ResponseEntity.ok(httpSession.getAttribute("customerToken"));
//    }

//    @GetMapping("/check")
//    public ResponseEntity<?> checkSomething(HttpSession httpSession){
//        SeatSelectDTO seatSelectDTO = (SeatSelectDTO) httpSession.getAttribute("seatSelectDTO");
//        return ResponseEntity.ok(seatSelectDTO.toString());
//    }

//    @GetMapping
//    public ResponseEntity<?> awaitPurchase(HttpSession httpSession){
//        httpSession.setAttribute("customerToken", seatPurchaseService.createCustomerIdentifyingToken());
//        httpSession.setMaxInactiveInterval(600);
//        return ResponseEntity.ok(httpSession.getAttribute("customerToken"));
//    }


}
