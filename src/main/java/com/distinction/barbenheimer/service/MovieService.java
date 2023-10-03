package com.distinction.barbenheimer.service;
import com.distinction.barbenheimer.dto.MovieDetailsDTO;
import com.distinction.barbenheimer.dto.MovieShortDTO;
import com.distinction.barbenheimer.dto.MovieTitleDTO;
import com.distinction.barbenheimer.model.Movie;

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

    


}
