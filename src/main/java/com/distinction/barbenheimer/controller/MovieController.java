package com.distinction.barbenheimer.controller;

import com.distinction.barbenheimer.DTO.MovieDetailsDTO;
import com.distinction.barbenheimer.model.Movie;
import com.distinction.barbenheimer.service.MovieService;

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


    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    
    /** 
     * method returns all movies stored
     * 
     * @return ResponseEntity<List<Movie>>
     */
    @GetMapping
    public ResponseEntity<List<MovieDetailsDTO>> getAllMovies() {
        return ResponseEntity.ok(movieService.getAllCurrent());
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
    public ResponseEntity<List<MovieDetailsDTO>> getMoviesBySearch(@RequestParam("q") String movieTitle) {
        return ResponseEntity.ok(movieService.getMoviesBySearch(movieTitle));
    }

    
    /** 
     * method returns everything related to movie selevted by user
     * 
     * @param movieId
     * @return ResponseEntity<MovieDetailsDTO>
     */
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDetailsDTO> getMovieDetails(@PathVariable("movieId") long movieId ) {
        return ResponseEntity.ok(movieService.getMovieDetails(movieId));
    }

}
