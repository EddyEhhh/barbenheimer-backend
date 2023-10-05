package com.barbenheimer.mailerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieScheduleTime {

    private long id;

    private MovieScheduleDate movieScheduleDate;

    private LocalTime showTime;

    private Hall hall;

    private List<SeatStatus> seatStatuses;



}
