package com.barbenheimer.movieservice.dto;


import com.barbenheimer.movieservice.model.OngoingPurchase;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PurchaseDTO {

    private Long Id;

    //For storing in database

    private String customerEmail;

    private OngoingPurchase ongoingPurchase;

    //For PaymentIntent

    private String movieName;

    private String movieShowtime;

    private Long priceInCents;

    private Long quantity;

}
