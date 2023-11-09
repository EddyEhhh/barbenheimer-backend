package com.barbenheimer.movieservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barbenheimer.movieservice.service.CustomerDetailService;

import jakarta.validation.constraints.Email;
import lombok.extern.slf4j.Slf4j;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/customers")
@Slf4j
@Validated
public class CustomerDetailController {

    private CustomerDetailService customerDetailsService;

    @Autowired
    public CustomerDetailController(CustomerDetailService customerDetailsService){
        this.customerDetailsService = customerDetailsService;
    }
    
    
    /** 
     * method is POST request handler
     * receives email and checks if it is a valid email
     * if invalid, throws exception with appropriate message
     * if valid, stores the CustomerDetail object into the CustomerDetailRepository
     * and sends confirmation message
     * 
     * @param email
     * @return ResponseEntity<?>
     */
    @PostMapping
    public ResponseEntity<?> inputCustomerDetails(@RequestBody String email) {
        try {
            customerDetailsService.inputCustomerDetails(email);
            return ResponseEntity.ok("A confirmation email was sent to your email");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
