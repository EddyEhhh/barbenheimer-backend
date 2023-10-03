package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.dto.MovieScheduleDetailDTO;
import com.distinction.barbenheimer.dto.MovieScheduleDateDetailDTO;
import com.distinction.barbenheimer.model.Movie;

import java.time.LocalDateTime;
import java.util.List;


public interface MovieScheduleService {

    public List<MovieScheduleDetailDTO> getAll(Movie movie);

    public List<MovieScheduleDateDetailDTO> getShowtime(Movie movie);

    public List<MovieScheduleDateDetailDTO> getShowtime(Movie movie, LocalDateTime before);

}
