package com.barbenheimer.mailerservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieScheduleDate {

    private long id;

    private Movie movie;

    private LocalDate showDate;

    private List<MovieScheduleTime> movieScheduleTimes;


}
