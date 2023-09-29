package com.distinction.barbenheimer.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.distinction.barbenheimer.model.CustomerDetail;
import com.distinction.barbenheimer.repository.CustomerDetailRepository;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class CustomerDetailServiceImpl implements CustomerDetailService{

    private CustomerDetailRepository customerDetailRepository;

    @Autowired
    public CustomerDetailServiceImpl(CustomerDetailRepository customerDetailRepository){
        this.customerDetailRepository = customerDetailRepository;
    }

    public void inputCustomerDetails(String email) {
        CustomerDetail customerDetail = new CustomerDetail();
        customerDetail.setEmail(email);
        customerDetailRepository.save(customerDetail);
    }

}