package com.distinction.barbenheimer.service;


import com.distinction.barbenheimer.DTO.PaymentDTO;
import com.stripe.exception.StripeException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

public interface SeatPurchaseService {

        public String createCustomerIdentifyingToken();

        public String checkout(PaymentDTO paymentDTO, HttpServletResponse response, HttpSession httpSession) throws StripeException, IOException;
}
