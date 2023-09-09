package com.distinction.barbenheimer.DTO;

import com.distinction.barbenheimer.model.Hall;
import com.distinction.barbenheimer.model.Movie;
import com.distinction.barbenheimer.model.SeatStatus;
import jakarta.persistence.*;
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
public class MovieScheduleDetailDTO {


    private LocalDate showDate;

    private LocalTime showTime;

//    private Hall hall;
//
//
//    private List<SeatStatus> seatStatuses;



}
