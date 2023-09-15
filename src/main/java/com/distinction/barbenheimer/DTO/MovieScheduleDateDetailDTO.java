package com.distinction.barbenheimer.DTO;

import com.distinction.barbenheimer.model.Movie;
import com.distinction.barbenheimer.model.MovieScheduleTime;
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
public class MovieScheduleDateDetailDTO {

    private long id;

    private LocalDate showDate;

    private List<MovieScheduleTimeDetailDTO> movieScheduleTimes;

}
