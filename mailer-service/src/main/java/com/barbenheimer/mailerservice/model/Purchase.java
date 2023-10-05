package com.barbenheimer.mailerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Purchase {

    private Long id;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name="customer_id")
//    private Customer customer;

    private CustomerDetail customerDetail;

    private double paidAmount;

    private List<SeatStatus> seatStatuses;

    private LocalDateTime dateTime;

    public Purchase(double paidAmount){
        this.paidAmount = paidAmount;
    }






}
