package com.distinction.barbenheimer.controller;

import com.distinction.barbenheimer.DTO.MovieDetailsDTO;
import com.distinction.barbenheimer.DTO.MovieShortDTO;
import com.distinction.barbenheimer.DTO.MovieTitleDTO;
import com.distinction.barbenheimer.model.Movie;
import com.distinction.barbenheimer.service.MovieService;

import com.distinction.barbenheimer.service.MovieServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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
    public ResponseEntity<List<MovieTitleDTO>> getAllCurrentTitleAndId() {
        return ResponseEntity.ok(movieService.getAllCurrentTitleAndId());
    }

}
