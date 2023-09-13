package com.distinction.barbenheimer.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatStatus {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    private MovieScheduleTime movieScheduleTime;

    @ManyToOne(fetch = FetchType.EAGER)
    private Seat seat;

    private int state;





}
