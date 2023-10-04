package com.barbenheimer.ticket.model;

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
public class Hall {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private int number;

    private int width;

    private int height;

//    @OneToMany(mappedBy = "hall")
//    private List<MovieScheduleDate> movieScheduleDates;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Seat> seats;
}
