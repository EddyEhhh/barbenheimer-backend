package com.barbenheimer.movieservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MovieScheduleDetailDTO {


    private LocalDate showDate;

    private LocalTime showTime;

//    private Hall hall;
//
//
//    private List<SeatStatus> seatStatuses;



}
