package com.barbenheimer.movieservice.service;
import com.barbenheimer.movieservice.dto.MovieDetailsDTO;
import com.barbenheimer.movieservice.dto.MovieScheduleDateDetailDTO;
import com.barbenheimer.movieservice.dto.MovieShortDTO;
import com.barbenheimer.movieservice.dto.MovieTitleDTO;
import com.barbenheimer.movieservice.model.Movie;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.web.multipart.MultipartFile;


public interface MovieService {

    public List<MovieShortDTO> getAllCurrent();
    
    public List<MovieTitleDTO> getAllTitleAndId();

    public List<MovieShortDTO> getMoviesBySearch(String movieTitle);

    public MovieDetailsDTO getDetails(Long movieId);

    public List<LocalDateTime> getMovieShowtimes(Movie movie);

    public String uploadMovieImage(Long movieId, MultipartFile file) throws IOException;

    public String createMovies(List<MovieDetailsDTO> movies);

    public String setMovieSchedules(MovieTitleDTO movie, List<MovieScheduleDateDetailDTO> movieSchedules);
}
