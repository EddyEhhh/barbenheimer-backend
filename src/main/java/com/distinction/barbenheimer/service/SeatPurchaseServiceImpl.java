package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.DTO.PaymentDTO;
import com.distinction.barbenheimer.repository.OngoingPurchaseRepository;
import com.distinction.barbenheimer.repository.PurchaseRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Instant;
import java.util.UUID;

@Service
public class SeatPurchaseServiceImpl implements SeatPurchaseService {

    private OngoingPurchaseRepository ongoingPurchaseRepository;

    private PurchaseRepository purchaseRepository;

    @Value("${STRIPE_PRIVATE_KEY}")
    private String stripeKey;

    @Autowired
    public SeatPurchaseServiceImpl(OngoingPurchaseRepository ongoingPurchaseRepository, PurchaseRepository purchaseRepository){
        this.ongoingPurchaseRepository = ongoingPurchaseRepository;
    }



    public String checkout(PaymentDTO paymentDTO, HttpServletResponse response, HttpSession httpSession) throws StripeException, IOException {
        double totalPaidAmount = paymentDTO.getPriceInCents() * paymentDTO.getQuantity();

        Stripe.apiKey = stripeKey;

        String domainUrl = "http://localhost:3000";
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(domainUrl + "/movie/2") //dummypath
                .setCancelUrl(domainUrl)
                .addLineItem(generateLineItem(paymentDTO))
                .build();

        Session session = Session.create(params);
        response.sendRedirect(session.getUrl());
//      recordSeatPurchase(httpSession);
//        recordPurchase(totalPaidAmount);
        return "success";
    }

    public SessionCreateParams.LineItem generateLineItem(PaymentDTO paymentDTO){
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

        return lineItem;
    }

//    public void recordSeatPurchase(HttpSession httpSession){
//        SeatPurchase seatPurchase = SeatPurchase.builder()
//                .token((String) httpSession.getAttribute("customerToken"))
//                .timestamp(LocalDateTime.now())
//                .build();
//        seatPurchaseRepository.save(seatPurchase);
//    }

//     public void recordPurchase(SeatStatusDetailDTO seatStatusDetailDTO, double totalPaidAmount){
//        Purchase purchase = Purchase.builder()
//                .dateTime(LocalDateTime.now())
//                .paidAmount(totalPaidAmount)
//                .build();
//        purchaseRepository.save(purchase);
//     }

//    private boolean existToken(String token){
//        return seatPurchaseRepository.findByToken(token).isPresent();
//    }

    public void purchaseSeats(){
        //TODO: implement this
    }


}
