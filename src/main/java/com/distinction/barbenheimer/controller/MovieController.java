package com.distinction.barbenheimer.controller;

import com.distinction.barbenheimer.model.Movie;
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
    public ResponseEntity<List<Movie>> getAllMovies() {
        return ResponseEntity.ok(movieServiceImpl.getAllMovies());
    }

    
    
    /** 
     * method is a GET request handler
     * it returns the specific movie selected by user
     * 
     * @param movieTitle
     * @return ResponseEntity<Movie>
     */
    @GetMapping

    public ResponseEntity<Movie> getMovieByTitle(@PathVariable("movieTitle") String movieTitle){
        return ResponseEntity.ok(movieServiceImpl.getMovieByTitle(movieTitle));
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
    @GetMapping
    public ResponseEntity<List<Movie>> getMovies(@PathVariable("movieTitle") String movieTitle) {
        return ResponseEntity.ok(movieServiceImpl.getMovies(movieTitle));
    }

    

}
