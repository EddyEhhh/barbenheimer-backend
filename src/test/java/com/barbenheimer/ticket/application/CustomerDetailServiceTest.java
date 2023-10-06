package com.barbenheimer.ticket.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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
    private CustomerDetailServiceImpl customerDetailServiceImpl;

    private CustomerDetailRepository customerDetailRepository;

    @BeforeEach
    public void setUp(CustomerDetailRepository customerDetailRepository) {
        customerDetailServiceImpl = new CustomerDetailServiceImpl(customerDetailRepository);
    }

    @Test
    public void inputCustomerDetails_validInput_success() {
        //arrange
        String email = "abc@def.com";
        CustomerDetail customerDetail = new CustomerDetail();
        customerDetail.setEmail(email);
        //act
        when(customerDetailRepository.save(customerDetail)).thenReturn(customerDetail);
        CustomerDetail result = customerDetailRepository.findById(customerDetail.getId());
        //assert
        assertNotNull(result);
        assertEquals(customerDetail.getEmail(), result.getEmail());
        assertEquals(customerDetail.getId(), result.getId());
    }

    @Test
    public void inputCustomerDetails_invalidInput_failure() {
        //arrange
        String invalidEmail = "abc";
        CustomerDetail customerDetail = new CustomerDetail();

        //act
        when(customerDetailRepository.save(customerDetail))
        .thenThrow(new IllegalArgumentException("Please enter a valid email address."));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            customerDetailServiceImpl.inputCustomerDetails(invalidEmail);
        });

        //assert
        assertEquals("Please enter a valid email address.", exception.getMessage());
    }
}