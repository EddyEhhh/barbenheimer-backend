package com.distinction.barbenheimer.DTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SeatNumberDTO {

    private String rowCharacter;
    
    private int columnNumber;

}
