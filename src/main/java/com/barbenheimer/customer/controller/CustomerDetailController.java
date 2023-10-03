package com.barbenheimer.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barbenheimer.customer.service.CustomerDetailService;

import jakarta.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/customers")
@Slf4j
public class CustomerDetailController {

    private CustomerDetailService customerDetailsService;

    @Autowired
    public CustomerDetailController(CustomerDetailService customerDetailsService){
        this.customerDetailsService = customerDetailsService;
    }
    
    @PostMapping
    public ResponseEntity<?> inputCustomerDetails(@RequestBody @Email String email) {
        try {
            customerDetailsService.inputCustomerDetails(email);
            return ResponseEntity.ok("A confirmation email was sent to your email");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Please enter a valid email address.");
        }
    }
}
