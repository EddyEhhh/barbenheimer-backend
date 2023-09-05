package com.distinction.barbenheimer.service;
import java.time.LocalDateTime;
import java.util.*;


interface MovieService {
    
    public List<Movie> getMovie(String movieName);

    public Movie getMovieDetails(Movie movie);

    public List<LocalDateTime> getMovieShowtimes(Movie movie);


}
