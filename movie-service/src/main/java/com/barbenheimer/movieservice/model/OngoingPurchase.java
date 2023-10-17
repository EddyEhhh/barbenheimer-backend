package com.barbenheimer.movieservice.model;

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
public class OngoingPurchase {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(fetch = FetchType.EAGER, mappedBy="ongoingPurchase")
    private List<SeatStatus> seatStatuses;


    @NotEmpty
    @Column(unique = true)
    private String token;

    private LocalDateTime expireTimeStamp;

    private double totalPrice;

}
