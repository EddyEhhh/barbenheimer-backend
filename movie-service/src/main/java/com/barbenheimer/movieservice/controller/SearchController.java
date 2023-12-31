package com.barbenheimer.movieservice.controller;

import com.barbenheimer.movieservice.dto.MovieTitleDTO;
import com.barbenheimer.movieservice.service.MovieService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/search")
@Slf4j
public class SearchController {


    private MovieService movieService;


    @Autowired
    public SearchController(MovieService movieService){
        this.movieService = movieService;
    }
    
    /** 
     * method is a GET request handler
     * * method receives move title and Id from user input in search bar
     * returns all currently showing movies' title and Id
     * @return ResponseEntity<List<MovieTitleDTO>>
     */
    @GetMapping("/movies")
    public ResponseEntity<List<MovieTitleDTO>> getAllTitleAndId() {
        return ResponseEntity.ok(movieService.getAllTitleAndId());
    }

}
