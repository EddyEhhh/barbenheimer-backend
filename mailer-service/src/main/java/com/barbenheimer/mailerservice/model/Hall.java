package com.barbenheimer.mailerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Hall {

    private Long id;

    private int number;

    private int width;

    private int height;

    private List<Seat> seats;
}
