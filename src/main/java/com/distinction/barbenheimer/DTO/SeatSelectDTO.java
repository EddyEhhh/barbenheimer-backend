package com.distinction.barbenheimer.DTO;

import java.time.LocalDateTime;

import org.springframework.cglib.core.Local;

import com.distinction.barbenheimer.model.Hall;

public class SeatSelectDTO {

    public Long movieId;

    public LocalDateTime showDate;

    public LocalDateTime showTime;
    
    private String rowCharacter;

    private int columnNumber;
}
