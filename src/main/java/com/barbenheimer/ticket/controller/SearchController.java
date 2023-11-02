package com.barbenheimer.ticket.controller;

import com.barbenheimer.ticket.dto.MovieTitleDTO;
import com.barbenheimer.ticket.service.MovieService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
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
     * returns all currently showing movies' title and Id
     * @param movieTitle
     * @return ResponseEntity<List<MovieTitleDTO>>
     */
    @GetMapping("/movies")
    public ResponseEntity<List<MovieTitleDTO>> getAllTitleAndId() {
        return ResponseEntity.ok(movieService.getAllTitleAndId());
    }

}
