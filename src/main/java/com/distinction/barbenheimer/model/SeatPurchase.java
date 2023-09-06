package com.distinction.barbenheimer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatPurchase {

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns( {
            @JoinColumn(name="hall_id", referencedColumnName="hall_id"),
            @JoinColumn(name="seatRow", referencedColumnName="seatRow"),
            @JoinColumn(name="number", referencedColumnName="number"),
    } )
    private Seat seat;

    @Id
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns( {
            @JoinColumn(name="movie_id", referencedColumnName="movie_id"),
            @JoinColumn(name="show_time", referencedColumnName="showTime")
    } )
    private MovieSchedule movieSchedule;

    @NotEmpty
    private String purchaseToken;

    private LocalDateTime timestamp;
}
