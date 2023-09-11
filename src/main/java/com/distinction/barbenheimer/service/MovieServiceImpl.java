package com.distinction.barbenheimer.service;

import java.time.LocalDateTime;
import java.util.*;

import com.distinction.barbenheimer.DTO.MovieImageDetailDTO;
import com.distinction.barbenheimer.DTO.MovieScheduleShowtimeDTO;
import com.distinction.barbenheimer.model.MovieImage;
import com.distinction.barbenheimer.model.MovieSchedule;
import com.distinction.barbenheimer.s3.S3Buckets;
import com.distinction.barbenheimer.s3.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.distinction.barbenheimer.DTO.MovieDetailsDTO;
import com.distinction.barbenheimer.model.Movie;
import com.distinction.barbenheimer.repository.MovieRepository;
import org.springframework.cglib.core.Local;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService{

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private S3Service s3Service;

    @Autowired
    private S3Buckets s3Buckets;

    private ModelMapper modelMapper;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, ModelMapper modelMapper){
        this.modelMapper = modelMapper;
        this.movieRepository = movieRepository;
    }



    
    /** 
     * method returns all movies now showing
     * @return List<Movie>
     */
    @Override
    public List<MovieDetailsDTO> getAllCurrent() {
        LocalDateTime now = LocalDateTime.now();
        List<Movie> movieList = movieRepository.findByLastShowingDateAfter(now);
        log.info("PRINT: " + movieList.size());
        List<MovieDetailsDTO> movieDTOList = new ArrayList<>();
        for (Movie eachMovie : movieList) {
            movieDTOList.add(modelMapper.map(eachMovie, MovieDetailsDTO.class));
        }
        return movieDTOList;
    }


    
    
    /** 
     * method returns all movies with names matching that of user input
     * 
     * @param movieName
     * @return List<Movie>
     */
    @Override
    public List<MovieDetailsDTO> getMoviesBySearch(String movieName) {
        List<Movie> matchingMovies = movieRepository.findByTitleContaining(movieName);
        if (matchingMovies == null) {
            throw new RuntimeException("Movie does not exist");
        } 
        List<MovieDetailsDTO> matchingDTOs = new ArrayList<>();
        for (Movie movie : matchingMovies) {
            matchingDTOs.add(MovieDetailsDTO.builder().id(movie.getId())
                                        .title(movie.getTitle())
                                        .description(movie.getDescription())
                                        .runtimeInMinute(movie.getRuntimeInMinute())
                                        .director(movie.getDirector())
                                        .cast(movie.getCast())
                                        .genre(movie.getGenre())
                                        .releaseDate(movie.getReleaseDate())
                                        .language(movie.getLanguage())
                                        .ageRestriction(movie.getAgeRestriction())
//                                        .movieSchedules(movie.getMovieSchedules())
//                                        .movieImages(movie.getMovieImages())
                                        .build()
                            );
        }
        return matchingDTOs;
    
    }


    /** 
     * returns details of movie when user selects it
     * @param movieId the id of the movie to retrieve
     * @return MovieDetailsDTO the DTO of the retrieved movie
     */
    @Override
    public MovieDetailsDTO getMovieDetails(Long movieId) {
        Movie movie = movieRepository.findById(movieId).get();
        MovieDetailsDTO movieDetailsDTO = modelMapper.map(movie, MovieDetailsDTO.class);


        return movieDetailsDTO;
        
//        return MovieDetailsDTO.builder().id(movie.getId())
//                                        .title(movie.getTitle())
//                                        .description(movie.getDescription())
//                                        .runtimeInMinute(movie.getRuntimeInMinute())
//                                        .director(movie.getDirector())
//                                        .cast(movie.getCast())
//                                        .genre(movie.getGenre())
//                                        .releaseDate(movie.getReleaseDate())
//                                        .language(movie.getLanguage())
//                                        .ageRestriction(movie.getAgeRestriction())
////                                        .movieScheduleShowtimeDTOs(convertToDTOList(movie.getMovieSchedules()))
////                                        .movieImageDetailDTOs(movie.getMovieImages())
//                                        .build();


    }




    /**
     * @param movie
     * @return List<LocalDateTime>
     */
    @Override
    public List<LocalDateTime> getMovieShowtimes(Movie movie) {
        // TODO Auto-generated method stub
        return null;
    }





    
}
