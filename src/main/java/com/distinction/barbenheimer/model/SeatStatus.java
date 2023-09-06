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
    @JoinColumns( {
            @JoinColumn(name="movie_id", referencedColumnName="movie_id"),
            @JoinColumn(name="show_time", referencedColumnName="showTime")
    } )
    private MovieSchedule movieSchedule;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns( {
            @JoinColumn(name="hall_id", referencedColumnName="hall_id"),
            @JoinColumn(name="seat_row", referencedColumnName="seatRow"),
            @JoinColumn(name="seat_number", referencedColumnName="number")
    } )
    private Seat seat;

    private int state;





}
