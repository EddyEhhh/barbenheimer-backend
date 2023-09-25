package com.distinction.barbenheimer.controller;

import com.distinction.barbenheimer.DTO.PaymentDTO;
import com.distinction.barbenheimer.DTO.SeatSelectDTO;
import com.distinction.barbenheimer.service.SeatPurchaseService;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/pay")
@Slf4j
public class SeatPurchaseController {


    private final SeatPurchaseService seatPurchaseService;

    @Value("${STRIPE_PRIVATE_KEY}")
    private String stripeKey;

    @Autowired
    public SeatPurchaseController(SeatPurchaseService seatPurchaseService){
        this.seatPurchaseService = seatPurchaseService;
    }

    //Things this needs: Customer Detail and Seat Selected
    // upon landing on this payment page there will be a timeout after 10 mins.
    @GetMapping
    public ResponseEntity<?> awaitPurchase(HttpSession httpSession){
        httpSession.setAttribute("customerToken", seatPurchaseService.createCustomerIdentifyingToken());
        httpSession.setMaxInactiveInterval(600);
        return ResponseEntity.ok(httpSession.getAttribute("customerToken"));
    }

    @PostMapping("/create-checkout-session")
    public String purchaseTickets (@RequestBody List<PaymentDTO> paymentDTOs, HttpServletResponse response) throws StripeException, IOException {

        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        for(PaymentDTO paymentDTO : paymentDTOs){

            SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                    .setName(paymentDTO.getMovieName()).build();

            SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                    .setCurrency("sgd")
                    .setProductData(productData)
                    .setUnitAmount(paymentDTO.getPriceInCents())
                    .build();

            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                                    .setQuantity(paymentDTO.getQuantity())
                                    .setPriceData(priceData)
                                    .build();

            lineItems.add(lineItem);
        }

        Stripe.apiKey = stripeKey;

        String domainUrl = "http://localhost:3000";
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(domainUrl + "/movie/2")
                .setCancelUrl(domainUrl)
                .addAllLineItem(lineItems)
                .build();

        Session session = Session.create(params);
        response.sendRedirect(session.getUrl());
        return "success";
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
