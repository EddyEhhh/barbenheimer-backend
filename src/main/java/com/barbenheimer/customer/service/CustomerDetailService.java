package com.barbenheimer.customer.service;

public interface CustomerDetailService {
    
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
    public void inputCustomerDetails(String email);
}