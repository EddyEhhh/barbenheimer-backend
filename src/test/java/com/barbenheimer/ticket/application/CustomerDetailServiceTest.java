package com.barbenheimer.ticket.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.barbenheimer.ticket.model.CustomerDetail;
import com.barbenheimer.ticket.repository.CustomerDetailRepository;
import com.barbenheimer.ticket.serviceImpl.CustomerDetailServiceImpl;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CustomerDetailServiceTest {

    @Mock
    private CustomerDetailRepository customerDetailRepository;

    @InjectMocks
    private CustomerDetailServiceImpl customerDetailServiceImpl;

    @Test
    public void inputCustomerDetails_validInput_success() {
        // arrange
        String email = "abc@def.com";
        CustomerDetail customerDetail = new CustomerDetail();
        customerDetail.setEmail(email);
        // act
        when(customerDetailRepository.save(customerDetail)).thenReturn(customerDetail);
        CustomerDetail result = customerDetailRepository.save(customerDetail);
        // assert
        assertNotNull(result);
        assertEquals(customerDetail.getEmail(), result.getEmail());
        assertEquals(customerDetail.getId(), result.getId());
    }


    @Test
    public void inputCustomerDetails_invalidInput_failure() {
        // arrange
        String invalidEmail = "abc";
        CustomerDetail customerDetail = new CustomerDetail();
        customerDetail.setEmail(invalidEmail);

        // act
        lenient().when(customerDetailRepository.save(customerDetail))
                .thenThrow(new IllegalArgumentException("Please enter a valid email."));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerDetailServiceImpl.inputCustomerDetails(invalidEmail);
        });

        // assert
        assertEquals("Please enter a valid email address.", exception.getMessage());
    }
}