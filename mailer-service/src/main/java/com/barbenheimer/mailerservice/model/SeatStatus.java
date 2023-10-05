package com.barbenheimer.mailerservice.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatStatus {

    private long id;

    private MovieScheduleTime movieScheduleTime;

    private Seat seat;

    private int state;





}
