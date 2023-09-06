package com.distinction.barbenheimer.service;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.distinction.barbenheimer.model.Movie;
import com.distinction.barbenheimer.repository.MovieRepository;

public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieRepository movieRepository;

    
    /** 
     * method returns all movies available
     * @return List<Movie>
     */
    @Override
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }
    
    /** 
     * method returns the movie object when user selects the specific movie on the website
     * 
     * @param movieTitle
     * @return Movie
     */
    public Movie getMovieByTitle(String movieTitle) {
        Movie movie = movieRepository.findByTitle(movieTitle);
        if (movie == null){
            throw new RuntimeException("Movie does not exists");
        }
        return movie;
}
    
    /** 
     * method returns all movies with names matching that of user input
     * 
     * @param movieName
     * @return List<Movie>
     */
    @Override
    public List<Movie> getMoviesBySearch(String movieName) {
        List<Movie> matchingMovies = movieRepository.findByTitleContaining(movieName);
        if (matchingMovies == null) {
            throw new RuntimeException("Movie does not exist");
        } 
        return matchingMovies;
    
    }


    /** 
     * 
     * @param movie
     * @return Movie
     */
    @Override
    public Movie getMovieDetails(Movie movie) {
        // TODO Auto-generated method stub
        return null;
    }

    
    /** 
     * @param movie
     * @return List<LocalDateTime>
     */
    @Override
    public List<LocalDateTime> getMovieShowtimes(Movie movie) {
        // TODO Auto-generated method stub
        return null;
    }

    
}
