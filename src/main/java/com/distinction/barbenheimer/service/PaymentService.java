package com.distinction.barbenheimer.service;


import com.distinction.barbenheimer.DTO.PaymentDTO;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public interface PaymentService {

        public String createCustomerIdentifyingToken();

        public String checkout(List<PaymentDTO> paymentDTOs, HttpServletResponse response) throws StripeException, IOException;
}
