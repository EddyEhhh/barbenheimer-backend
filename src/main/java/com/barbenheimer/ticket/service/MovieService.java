package com.barbenheimer.ticket.service;
import com.barbenheimer.ticket.dto.MovieDetailsDTO;
import com.barbenheimer.ticket.dto.MovieShortDTO;
import com.barbenheimer.ticket.dto.MovieTitleDTO;
import com.barbenheimer.ticket.model.Movie;

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
