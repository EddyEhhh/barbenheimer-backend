package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.DTO.MovieDetailsDTO;
import com.distinction.barbenheimer.DTO.MovieScheduleDetailDTO;
import com.distinction.barbenheimer.DTO.MovieScheduleShowtimeDTO;
import com.distinction.barbenheimer.model.Movie;

import java.time.LocalDateTime;
import java.util.List;


public interface MovieScheduleService {

    public List<MovieScheduleDetailDTO> getAll(Movie movie);

    public List<MovieScheduleShowtimeDTO> getShowtime(Movie movie);

    public List<MovieScheduleShowtimeDTO> getShowtime(Movie movie, LocalDateTime before);



}
