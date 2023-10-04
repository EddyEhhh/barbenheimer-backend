package com.barbenheimer.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SeatNumberDTO {

    private String rowCharacter;
    
    private int columnNumber;

}
