package com.barbenheimer.customer.serviceImpl;

import com.barbenheimer.customer.repository.CustomerDetailRepository;
import com.barbenheimer.customer.service.CustomerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barbenheimer.customer.model.CustomerDetail;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class CustomerDetailServiceImpl implements CustomerDetailService {

    private CustomerDetailRepository customerDetailRepository;

    @Autowired
    public CustomerDetailServiceImpl(CustomerDetailRepository customerDetailRepository){
        this.customerDetailRepository = customerDetailRepository;
    }

    public void inputCustomerDetails(String email){
        CustomerDetail customerDetail = new CustomerDetail();
        String regex = "^[A-Z0-9+_.-]+@[A-Z0-9.-]+$";
        if (!email.matches(regex)) {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }
        customerDetail.setEmail(email);
        customerDetailRepository.save(customerDetail);
    }

}