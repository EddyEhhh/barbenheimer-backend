package com.distinction.barbenheimer.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.distinction.barbenheimer.model.Movie;
import com.distinction.barbenheimer.repository.MovieRepository;

public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieRepository movieRepository;

    public Movie getMovieByTitle(String movieTitle) {
        Movie movie = movieRepository.findByTitle(movieTitle);
        if (movie == null){
            throw new RuntimeException("Movie does not exists");
        }
        return movie;
}

    @Override
    public List<Movie> getMovie(String movieName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Movie getMovieDetails(Movie movie) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<LocalDateTime> getMovieShowtimes(Movie movie) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
