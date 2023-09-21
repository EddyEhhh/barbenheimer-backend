package com.distinction.barbenheimer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    private Seat seat;

    @OneToOne(fetch = FetchType.EAGER)
    private SeatStatus seatStatus;

    @ManyToOne(fetch = FetchType.EAGER)
    private MovieScheduleDate movieScheduleDate;

    @NotEmpty
    @Column(unique = true)
    private String token;

    private LocalDateTime timestamp;
}
