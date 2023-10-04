package com.barbenheimer.ticket.controller;

import com.barbenheimer.ticket.dto.MovieDetailsDTO;
import com.barbenheimer.ticket.dto.MovieShortDTO;
import com.barbenheimer.ticket.service.MovieService;

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
@RequestMapping("/api/v1/movies")
@Slf4j
public class MovieController {


    private MovieService movieService;


    @Autowired
    public MovieController(MovieService movieService){
        this.movieService = movieService;
    }

    
    /** 
     * method is a GET request handler
     * method returns all movies stored
     * 
     * @return ResponseEntity<List<Movie>>
     */
    @GetMapping
    public ResponseEntity<List<MovieShortDTO>> getAllMovies() {
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
    @GetMapping(params = "q")
    public ResponseEntity<List<MovieShortDTO>> getMoviesBySearch(@RequestParam("q") String movieTitle) {
        return ResponseEntity.ok(movieService.getMoviesBySearch(movieTitle));
    }


    
    /** 
     * method returns everything related to movie selected by user
     * 
     * @param movieId
     * @return ResponseEntity<MovieDetailsDTO>
     */
    @GetMapping("/{movieId}")
    public ResponseEntity<MovieDetailsDTO> getMovieDetails(@PathVariable("movieId") long movieId ) {
        return ResponseEntity.ok(movieService.getDetails(movieId));
    }


    /**
     * method is for website administrators to upload images for the movies
     *
     * @param movieId
     * @param file
     * @return ResponseEntity<?> defining the response message upon uploading image
     * @throws IOException
     */
    @PostMapping(value = "/{movieId}/imageUpload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> uploadMovieImage(@PathVariable("movieId") Long movieId,
                                                @RequestParam("image") MultipartFile file) throws IOException {
        String uploadImageResponse = movieService.uploadMovieImage(movieId, file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImageResponse);
    }

}
