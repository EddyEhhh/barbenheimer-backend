package com.barbenheimer.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SeatDetailDTO {

    private String rowCharacter;
    
    private int columnNumber;

    private int x;

    private int y;

}
