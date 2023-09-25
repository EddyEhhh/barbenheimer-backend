package com.distinction.barbenheimer.controller;

import com.distinction.barbenheimer.DTO.PaymentDTO;
import com.distinction.barbenheimer.service.PaymentService;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/pay")
@Slf4j
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    //Things this needs: Customer Detail and Seat Selected
    // upon landing on this payment page there will be a timeout after 10 mins.
//    @GetMapping
//    public ResponseEntity<?> awaitPurchase(HttpSession httpSession){
//        httpSession.setAttribute("customerToken", seatPurchaseService.createCustomerIdentifyingToken());
//        httpSession.setMaxInactiveInterval(600);
//        return ResponseEntity.ok(httpSession.getAttribute("customerToken"));
//    }

    @PostMapping("/create-checkout-session")
    public String checkout (@RequestBody List<PaymentDTO> paymentDTOList, HttpServletResponse response) throws StripeException, IOException {
        return paymentService.checkout(paymentDTOList, response);
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




}
