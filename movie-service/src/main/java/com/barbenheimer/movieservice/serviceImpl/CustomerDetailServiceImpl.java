package com.barbenheimer.movieservice.serviceImpl;

import com.barbenheimer.movieservice.repository.CustomerDetailRepository;
import com.barbenheimer.movieservice.service.CustomerDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barbenheimer.movieservice.model.CustomerDetail;

import lombok.extern.slf4j.Slf4j;
@Service
@Slf4j
public class CustomerDetailServiceImpl implements CustomerDetailService {

    private CustomerDetailRepository customerDetailRepository;

    @Autowired
    public CustomerDetailServiceImpl(CustomerDetailRepository customerDetailRepository){
        this.customerDetailRepository = customerDetailRepository;
    }

    public CustomerDetail inputCustomerDetails(String email){
        CustomerDetail customerDetail = new CustomerDetail();
        customerDetail.setEmail(email);
        return customerDetailRepository.save(customerDetail);
    }

}