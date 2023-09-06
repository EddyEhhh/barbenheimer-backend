package com.distinction.barbenheimer.controller;

import com.distinction.barbenheimer.model.Movie;
import com.distinction.barbenheimer.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/movies")
@Slf4j
public class MovieController {

    @Autowired
    private MovieService movieService;

    //TODO: get all movies

    @GetMapping
    public ResponseEntity<Movie> getMovieByTitle(@PathVariable("movieTitle") String movieTitle){
        return ResponseEntity.ok(movieService.getMovieByTitle(movieTitle));
    }

}
