package com.barbenheimer.movieservice.dto;

import com.barbenheimer.movieservice.model.CustomerDetail;
import com.barbenheimer.movieservice.model.SeatStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PurchaseShortDTO {

    private CustomerDetailDTO customerDetail;

    private double paidAmount;

    private MovieShortDTO movie;

    private LocalTime movieTime;

    private LocalDate movieDate;

    private int hallNumber;

    private List<SeatDetailDTO> seatDetails;

}
