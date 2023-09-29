package com.distinction.barbenheimer.service;

import org.springframework.stereotype.Service;

import com.distinction.barbenheimer.model.CustomerDetail;
import com.distinction.barbenheimer.repository.CustomerDetailsRepository;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class CustomerDetailsServiceImpl implements CustomerDetailsService{

    private CustomerDetailsRepository customerDetailsRepository;
    
    public void inputCustomerDetails(String email) {
        CustomerDetail customerDetail = new CustomerDetail();
        customerDetail.setEmail(email);
        customerDetailsRepository.save(customerDetail);
    }

}