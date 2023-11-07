package com.barbenheimer.movieservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.barbenheimer.movieservice.model.CustomerDetail;
import com.barbenheimer.movieservice.repository.CustomerDetailRepository;
import com.barbenheimer.movieservice.serviceImpl.CustomerDetailServiceImpl;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CustomerDetailServiceTest {

    @Mock
    private CustomerDetailRepository customerDetailRepository;

    @InjectMocks
    private CustomerDetailServiceImpl customerDetailServiceImpl;


    @Test
    public void inputCustomerDetails_validInput_success() {
        //arrange
        String email = "abc@def.com";
        CustomerDetail customerDetail = new CustomerDetail();
        customerDetail.setEmail(email);
        //act
        when(customerDetailRepository.save(any(CustomerDetail.class))).thenReturn(customerDetail);
        CustomerDetail result = customerDetailServiceImpl.inputCustomerDetails(email);

        //assert
        assertNotNull(result);
        assertEquals(customerDetail.getEmail(), result.getEmail());
    }

    @Test
    public void inputCustomerDetails_invalidInput_failure() {
        //arrange
        String invalidEmail = "abc";

        //act
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerDetailServiceImpl.inputCustomerDetails(invalidEmail);
        });

        //assert
        assertEquals("Please enter a valid email address.", exception.getMessage());
    }
}