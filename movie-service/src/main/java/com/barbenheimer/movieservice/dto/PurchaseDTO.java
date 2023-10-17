package com.barbenheimer.movieservice.dto;


import com.barbenheimer.movieservice.model.CustomerDetail;
import com.barbenheimer.movieservice.model.OngoingPurchase;
import com.barbenheimer.movieservice.model.SeatStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PurchaseDTO {

    private Long Id;

    //For storing in database

    private CustomerDetail customerDetail;

    private OngoingPurchase ongoingPurchase;

    //For PaymentIntent

    private String movieName;

    private String movieShowtime;

    private Long priceInCents;

    private Long quantity;

}
