package com.distinction.barbenheimer.service;
import com.distinction.barbenheimer.DTO.MovieDetailDTO;
import com.distinction.barbenheimer.model.Movie;

import java.time.LocalDateTime;
import java.util.*;


interface MovieService {

    public List<MovieDetailDTO> getAllMovies();

    public List<MovieDetailDTO> getMoviesBySearch(String movieTitle);

    public MovieDetailDTO getMovieDetails(Long movieId);

    public List<LocalDateTime> getMovieShowtimes(Movie movie);


}
