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
public class Seat {

    private long id;

    private Hall hall;
 
    private String rowCharacter;

    private int columnNumber;

    private int x;

    private int y;

}
