package com.distinction.barbenheimer.service;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;

import com.distinction.barbenheimer.DTO.MovieDetailsDTO;
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
    public List<MovieDetailsDTO> getAllMovies() {
        List<Movie> movieList = movieRepository.findAll();
        List<MovieDetailsDTO> movieDTOList = new ArrayList<>();
        for (Movie movie : movieList) {
            movieDTOList.add(MovieDetailsDTO.builder().id(movie.getId())
                                        .title(movie.getTitle())
                                        .description(movie.getDescription())
                                        .runtimeInMinute(movie.getRuntimeInMinute())
                                        .director(movie.getDirector())
                                        .cast(movie.getCast())
                                        .genre(movie.getGenre())
                                        .releaseDate(movie.getReleaseDate())
                                        .language(movie.getLanguage())
                                        .ageRestriction(movie.getAgeRestriction())
                                        .movieSchedules(movie.getMovieSchedules())
                                        .movieImages(movie.getMovieImages())
                                        .build()
                            );
        }
        return movieDTOList;
    }
    
    
    /** 
     * method returns all movies with names matching that of user input
     * 
     * @param movieName
     * @return List<Movie>
     */
    @Override
    public List<MovieDetailsDTO> getMoviesBySearch(String movieName) {
        List<Movie> matchingMovies = movieRepository.findByTitleContaining(movieName);
        if (matchingMovies == null) {
            throw new RuntimeException("Movie does not exist");
        } 
        List<MovieDetailsDTO> matchingDTOs = new ArrayList<>();
        for (Movie movie : matchingMovies) {
            matchingDTOs.add(MovieDetailsDTO.builder().id(movie.getId())
                                        .title(movie.getTitle())
                                        .description(movie.getDescription())
                                        .runtimeInMinute(movie.getRuntimeInMinute())
                                        .director(movie.getDirector())
                                        .cast(movie.getCast())
                                        .genre(movie.getGenre())
                                        .releaseDate(movie.getReleaseDate())
                                        .language(movie.getLanguage())
                                        .ageRestriction(movie.getAgeRestriction())
                                        .movieSchedules(movie.getMovieSchedules())
                                        .movieImages(movie.getMovieImages())
                                        .build()
                            );
        }
        return matchingDTOs;
    
    }


    /** 
     * returns details of movie when user selects it
     * @param movie
     * @return MovieDetailsDTO
     */
    @Override
    public MovieDetailsDTO getMovieDetails(Long movieId) {
        Movie movie = movieRepository.findById(movieId).get();
        
        return MovieDetailsDTO.builder().id(movie.getId())
                                        .title(movie.getTitle())
                                        .description(movie.getDescription())
                                        .runtimeInMinute(movie.getRuntimeInMinute())
                                        .director(movie.getDirector())
                                        .cast(movie.getCast())
                                        .genre(movie.getGenre())
                                        .releaseDate(movie.getReleaseDate())
                                        .language(movie.getLanguage())
                                        .ageRestriction(movie.getAgeRestriction())
                                        .movieSchedules(movie.getMovieSchedules())
                                        .movieImages(movie.getMovieImages())
                                        .build();
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
