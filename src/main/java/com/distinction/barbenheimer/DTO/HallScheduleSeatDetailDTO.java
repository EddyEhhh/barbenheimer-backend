package com.distinction.barbenheimer.DTO;

import java.util.List;

import com.distinction.barbenheimer.model.Seat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HallScheduleSeatDetailDTO {

    private int number;

    private int width;

    private int height;

    private List<ScheduleSeatDetailDTO> seats;


}
