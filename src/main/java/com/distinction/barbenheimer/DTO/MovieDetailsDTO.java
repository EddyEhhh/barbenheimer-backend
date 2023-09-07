package com.distinction.barbenheimer.DTO;

import java.time.LocalDate;
import java.util.List;

import com.distinction.barbenheimer.model.MovieImage;
import com.distinction.barbenheimer.model.MovieSchedule;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class MovieDetailsDTO {

    private Long id;

    private String title;

    private String description;

    private int runtimeInMinute;

    private String director;

    private String cast;

    private String genre;

    private LocalDate releaseDate;

    private String language;

    private int ageRestriction;

    private List<MovieSchedule> movieSchedules;

    private List<MovieImage> movieImages;
}
