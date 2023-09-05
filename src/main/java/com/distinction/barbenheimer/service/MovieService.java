package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.model.Movie;
import com.distinction.barbenheimer.repository.MovieRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;
    public Movie getMovieByTitle(String movieTitle) {
        Movie movie = movieRepository.findByTitle(movieTitle);
        if (movie == null) throw new RuntimeException("Movie does not exists");
        return movie;
    }




}
