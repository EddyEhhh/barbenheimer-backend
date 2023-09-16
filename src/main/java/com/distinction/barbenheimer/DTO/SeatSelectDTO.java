package com.distinction.barbenheimer.DTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import com.distinction.barbenheimer.model.Hall;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class SeatSelectDTO {

    public Long movieId;

    public LocalDate showDate;

    public LocalTime showTime;
    
    private String rowCharacter;

    private int columnNumber;

    @Override
    public String toString(){
        return "Movie: " + movieId + " | " + showDate.toString() + " | " + showTime.toString() + " | " + "Row: " + rowCharacter +  " | " + "Column: " + columnNumber;
    }
}
