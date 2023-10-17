package com.barbenheimer.movieservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OngoingPurchaseDetailDTO {


    private MovieShortDTO movie;

    private LocalTime movieTime;

    private LocalDate movieDate;

    private LocalDateTime expireTimeStamp;

    private double totalPrice;

    private int hallNumber;

    private List<SeatDetailDTO> seatDetails;


}
