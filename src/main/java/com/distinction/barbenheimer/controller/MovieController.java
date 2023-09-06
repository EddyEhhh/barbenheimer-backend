package com.distinction.barbenheimer.controller;

import com.distinction.barbenheimer.DTO.MovieDetailDTO;
import com.distinction.barbenheimer.service.MovieServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/movies")
@Slf4j
public class MovieController {

    @Autowired
    private MovieServiceImpl movieServiceImpl;

    
    
    /** 
     * method returns all movies stored
     * 
     * @return ResponseEntity<List<Movie>>
     */
    @GetMapping
    public ResponseEntity<List<MovieDetailDTO>> getAllMovies() {
        return ResponseEntity.ok(movieServiceImpl.getAllMovies());
    }

    
    /** 
     * The method `getMovies` is a GET request handler.
     * It retrieves a list of filtered movies based on the provided movie title. 
     * It takes in a `movieTitle` as a path variable and returns a ResponseEntity` 
     * containing a list of `Movie` objects.
     * 
     * @param movieTitle 
     * @return A response entity containing the list of all filtered movies matching input in search bar
     */
    @GetMapping("/search")
    public ResponseEntity<List<MovieDetailDTO>> getMoviesBySearch(@RequestParam("q") String movieTitle) {
        return ResponseEntity.ok(movieServiceImpl.getMoviesBySearch(movieTitle));
    }

    
    /** 
     * method returns everything related to movie selevted by user
     * 
     * @param movieId
     * @return ResponseEntity<MovieDetailsDTO>
     */
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDetailDTO> getMovieDetails(@PathVariable("movieId") long movieId ) {
        return ResponseEntity.ok(movieServiceImpl.getMovieDetails(movieId));
    }

}
