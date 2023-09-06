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
public class PurchaseSeat {
    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="purchase_id")
    private Purchase purchase;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns( {
            @JoinColumn(name="hall_id", referencedColumnName="hall_id"),
            @JoinColumn(name="seatRow", referencedColumnName="seatRow"),
            @JoinColumn(name="number", referencedColumnName="number")
    } )
    private Seat seat;
}
