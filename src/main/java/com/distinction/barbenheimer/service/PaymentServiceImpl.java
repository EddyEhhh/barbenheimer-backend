package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.DTO.PaymentDTO;
import com.distinction.barbenheimer.repository.SeatPurchaseRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PaymentServiceImpl implements PaymentService {

    private SeatPurchaseRepository seatPurchaseRepository;

    @Value("${STRIPE_PRIVATE_KEY}")
    private String stripeKey;

    @Autowired
    public PaymentServiceImpl(SeatPurchaseRepository seatPurchaseRepository){
        this.seatPurchaseRepository = seatPurchaseRepository;
    }

    public String createCustomerIdentifyingToken(){
        StringBuilder token = new StringBuilder();
        long currentTimeInMillisecond = Instant.now().toEpochMilli();
        token.append(currentTimeInMillisecond).append(UUID.randomUUID().toString());
        return token.toString().replace("-","");
    }

    public String checkout(List<PaymentDTO> paymentDTOList, HttpServletResponse response) throws StripeException, IOException {
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        for(PaymentDTO paymentDTO : paymentDTOList){

            SessionCreateParams.LineItem.PriceData.ProductData productData = SessionCreateParams.LineItem.PriceData.ProductData.builder()
                    .setName(paymentDTO.getMovieName()).build();

            SessionCreateParams.LineItem.PriceData priceData = SessionCreateParams.LineItem.PriceData.builder()
                    .setCurrency("sgd")
                    .setProductData(productData)
                    .setUnitAmount(paymentDTO.getPriceInCents())
                    .build();

            SessionCreateParams.LineItem lineItem = SessionCreateParams.LineItem.builder()
                    .setQuantity(paymentDTO.getQuantity())
                    .setPriceData(priceData)
                    .build();

            lineItems.add(lineItem);
        }

        Stripe.apiKey = stripeKey;

        String domainUrl = "http://localhost:3000";
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(domainUrl + "/movie/2") //dummypath
                .setCancelUrl(domainUrl)
                .addAllLineItem(lineItems)
                .build();

        Session session = Session.create(params);
        response.sendRedirect(session.getUrl());
        return "success";
    }

//    private boolean existToken(String token){
//        return seatPurchaseRepository.findByToken(token).isPresent();
//    }

    public void purchaseSeats(){
        //TODO: implement this
    }


}
