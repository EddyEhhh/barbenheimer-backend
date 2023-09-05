package com.distinction.barbenheimer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Purchase {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumns( {
            @JoinColumn(name="movie_id", referencedColumnName="movie_id"),
            @JoinColumn(name="hall_id", referencedColumnName="hall_id"),
            @JoinColumn(name="show_time", referencedColumnName="showTime")
    } )
    private MovieSchedule movieSchedule;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="customer_id")
    private Customer customer;

    private LocalDateTime dateTime;

    @NotEmpty
    private int ticketQty;

    @NotEmpty
    private float ticketPrice;


}
