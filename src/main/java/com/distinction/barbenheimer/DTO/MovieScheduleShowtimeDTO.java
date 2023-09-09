package com.distinction.barbenheimer.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MovieScheduleShowtimeDTO {

    private LocalDate showDate;

    private LocalTime showTime;

//    private HallDTO hallDTO;

//    private List<SeatStatusDTO> seatStatuses;
}
