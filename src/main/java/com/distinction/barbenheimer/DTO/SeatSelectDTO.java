package com.distinction.barbenheimer.DTO;

import java.time.LocalDateTime;

import com.distinction.barbenheimer.model.Hall;
import com.distinction.barbenheimer.model.SeatStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SeatSelectDTO {

    public String rowCharacter;

    public int columnNumber;

}
