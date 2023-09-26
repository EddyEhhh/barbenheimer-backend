package com.distinction.barbenheimer.model;


import java.util.List;

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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private MovieScheduleTime movieScheduleTime;

    @ManyToOne(fetch = FetchType.EAGER)
    private Seat seat;

    @OneToOne(mappedBy = "seatStatus")
    private SeatPurchase seatPurchases;

    // 2 for unavailable and 1 for pending confirmation
    private int state;





}
