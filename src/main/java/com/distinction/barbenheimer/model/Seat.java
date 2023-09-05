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
    private char seatRow;

    @Id
    private int number;

    private int state;

    @OneToMany(mappedBy = "seat")
    private List<PurchaseSeats> purchaseSeats;
}
