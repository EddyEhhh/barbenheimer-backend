package com.distinction.barbenheimer.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Seat {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="hall_id")
    private Hall hall;

    @Id
    private String seatRow;

    @Id
    private int number;

    @OneToMany(mappedBy = "seat")
    private List<SeatPurchase> seatPurchases;

    @OneToMany(mappedBy = "seat")
    private List<SeatStatus> seatStatuses;
}
