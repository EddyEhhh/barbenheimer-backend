package com.distinction.barbenheimer.DTO;

import com.distinction.barbenheimer.model.MovieImage;
import com.distinction.barbenheimer.model.MovieSchedule;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Data
@Builder
public class MovieDetailDTO {

    private Long id;

    private String title;

    private String description;

    private int runtimeInMinute;

    private String director;

    private String cast;

    private String genre;

    private LocalDateTime releaseDate;

    private String language;

    private int ageRestriction;

    private List<MovieSchedule> movieSchedules;

    private List<MovieImage> movieImages;
}
