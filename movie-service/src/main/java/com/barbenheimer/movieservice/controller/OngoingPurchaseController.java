package com.barbenheimer.movieservice.controller;

import com.barbenheimer.movieservice.dto.OngoingPurchaseDetailDTO;
import com.barbenheimer.movieservice.dto.PaymentIntentValidationDTO;
import com.barbenheimer.movieservice.service.OngoingPurchaseService;
import com.barbenheimer.movieservice.service.PurchaseService;
import com.stripe.exception.StripeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/reserves")
@Slf4j
public class OngoingPurchaseController {

    private final OngoingPurchaseService ongoingPurchaseService;

    @Autowired
    public OngoingPurchaseController(OngoingPurchaseService ongoingPurchaseService){
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



}
