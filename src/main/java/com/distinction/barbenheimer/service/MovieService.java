package com.distinction.barbenheimer.service;
import com.distinction.barbenheimer.DTO.MovieDetailsDTO;
import com.distinction.barbenheimer.model.Movie;

import java.time.LocalDateTime;
import java.util.*;

import com.distinction.barbenheimer.model.Movie;


public interface MovieService {

    public List<MovieDetailsDTO> getAllMovies();

    public List<MovieDetailsDTO> getMoviesBySearch(String movieTitle);

    public MovieDetailsDTO getMovieDetails(Long movieId);

    public List<LocalDateTime> getMovieShowtimes(Movie movie);


}
