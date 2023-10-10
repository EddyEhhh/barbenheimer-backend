package com.barbenheimer.movieservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieScheduleTimeDetailDTO {

    private long id;

    private LocalTime showTime;

    private HallIdDTO hall;

}
