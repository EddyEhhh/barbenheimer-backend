package com.distinction.barbenheimer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.distinction.barbenheimer.service.CustomerDetailsService;

import jakarta.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/customers")
@Slf4j
public class CustomerDetailController {

    private CustomerDetailsService customerDetailsService;
    
    @PostMapping
    public ResponseEntity<?> inputCustomerDetails(@RequestParam @Email String email) {
        try {
            customerDetailsService.inputCustomerDetails(email);
            return ResponseEntity.ok("A confirmation email was sent to your email");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter a valid email address.");
        }
    }
}
