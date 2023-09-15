package com.distinction.barbenheimer.controller;

import com.distinction.barbenheimer.DTO.MovieDetailsDTO;
import com.distinction.barbenheimer.DTO.MovieShortDTO;
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
    @GetMapping("/search")
    public ResponseEntity<List<MovieDetailsDTO>> getMoviesBySearch(@RequestParam("q") String movieTitle) {
        return ResponseEntity.ok(movieService.getMoviesBySearch(movieTitle));
    }

    
    /** 
     * method returns all applicable movies that match the user's search input
     * method only returns the selected movie title and its respective movieid
     * @param movieTitle
     * @return List<MovieDetailsDTO>
     */
    @GetMapping("/search")
    public List<MovieDetailsDTO> getMovieNameAndIdBySearch(@RequestParam("q") String movieTitle) {
        return ResponseEntity.ok(movieService.getMovieNameAndIdBySearch(movieTitle));
    }

    
    /** 
     * method returns everything related to movie selevted by user
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
