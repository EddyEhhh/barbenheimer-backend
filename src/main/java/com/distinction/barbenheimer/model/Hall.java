package com.distinction.barbenheimer.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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

//    @OneToMany(mappedBy = "hall")
//    private List<MovieScheduleDate> movieScheduleDates;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.PERSIST)
    private List<Seat> seats;
}
