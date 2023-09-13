package com.distinction.barbenheimer.service;
import com.distinction.barbenheimer.DTO.MovieDetailsDTO;
import com.distinction.barbenheimer.DTO.MovieShortDTO;
import com.distinction.barbenheimer.model.Movie;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import com.distinction.barbenheimer.model.Movie;
import org.springframework.web.multipart.MultipartFile;


public interface MovieService {

    public List<MovieShortDTO> getAllCurrent();

    public List<MovieDetailsDTO> getMoviesBySearch(String movieTitle);

    public MovieDetailsDTO getDetails(Long movieId);

    public List<LocalDateTime> getMovieShowtimes(Movie movie);

    public String uploadMovieImage(Long movieId, MultipartFile file) throws IOException;


}
