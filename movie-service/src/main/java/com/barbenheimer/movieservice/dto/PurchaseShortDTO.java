package com.barbenheimer.movieservice.dto;

import com.barbenheimer.movieservice.model.CustomerDetail;
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
public class PurchaseShortDTO {

    private CustomerDetail customerDetail;

    private double paidAmount;

    private List<SeatStatus> seatStatuses;
}
