package com.distinction.barbenheimer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieScheduleTime {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    private MovieScheduleDate movieScheduleDate;

    @Id
    private LocalTime showTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "hall_id")
    private Hall hall;


    @OneToMany(mappedBy = "movieScheduleTime")
    private List<SeatStatus> seatStatuses;



}
