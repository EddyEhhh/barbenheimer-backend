package com.barbenheimer.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ScheduleSeatDetailDTO {

    private long id;
 
    private String rowCharacter;

    private int columnNumber;

    private int x;

    private int y;

    private int state;

}
