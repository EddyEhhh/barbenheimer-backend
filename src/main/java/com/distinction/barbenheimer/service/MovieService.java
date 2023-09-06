package com.distinction.barbenheimer.service;
import com.distinction.barbenheimer.model.Movie;

import java.time.LocalDateTime;
import java.util.*;

import com.distinction.barbenheimer.model.Movie;


interface MovieService {

    public List<Movie> getAllMovies();

    public Movie getMovieByTitle(String movieTitle);
    
    public List<Movie> getMoviesBySearch(String movieName);

    public Movie getMovieDetails(Movie movie);

    public List<LocalDateTime> getMovieShowtimes(Movie movie);


}
