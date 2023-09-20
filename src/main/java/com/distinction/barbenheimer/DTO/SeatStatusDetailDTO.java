package com.distinction.barbenheimer.DTO;


import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeatStatusDetailDTO {

    private SeatNumberDTO seat;

    // 1 for unavailable and 2 for pending confirmation
    private int state;

}
