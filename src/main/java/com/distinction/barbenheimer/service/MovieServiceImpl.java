package com.distinction.barbenheimer.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

import com.distinction.barbenheimer.DTO.MovieShortDTO;
import com.distinction.barbenheimer.model.MovieImage;
import com.distinction.barbenheimer.s3.S3Buckets;
import com.distinction.barbenheimer.s3.S3Service;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.distinction.barbenheimer.DTO.MovieDetailsDTO;
import com.distinction.barbenheimer.model.Movie;
import com.distinction.barbenheimer.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    public List<MovieShortDTO> getAllCurrent() {
        LocalDateTime now = LocalDateTime.now();
        List<Movie> movieList = movieRepository.findByLastShowingDateAfter(now);
        List<MovieShortDTO> movieDTOList = new ArrayList<>();
        for (Movie eachMovie : movieList) {
            movieDTOList.add(modelMapper.map(eachMovie, MovieShortDTO.class));
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
    public List<MovieDetailsDTO> getMoviesBySearch(String movieTitle) {
        List<Movie> matchingMovies = movieRepository.findByTitleContaining(movieTitle);
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
     * method returns only the name and id of a movie upon a user's search input
     * 
     * @param movieName
     * @return List<MovieDetailsDTO>
     */
    @Override
    public List<MovieDetailsDTO> getMovieNameAndIdBySearch(String movieTitle) {
        List<Movie> matchingMovies = movieRepository.findByTitleContaining(movieTitle);
        if (matchingMovies == null) {
            throw new RuntimeException("Movie does not exist");
        } 
        List<MovieDetailsDTO> matchingDTOs = new ArrayList<>();
        for (Movie movie : matchingMovies) {
            matchingDTOs.add(MovieDetailsDTO.builder().id(movie.getId())
                                        .title(movie.getTitle())
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
    public MovieDetailsDTO getDetails(Long movieId) {
        Movie movie = movieRepository.findById(movieId).get();
        MovieDetailsDTO movieDetailsDTO = modelMapper.map(movie, MovieDetailsDTO.class);
        return movieDetailsDTO;
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

    /**
     *
     * @param movieId the id of the movie to upload image for
     * @param file the image file you wish to upload
     * @return String the result message upon successfully upload
     * @throws IOException
     */

    public String uploadMovieImage(Long movieId, MultipartFile file)throws IOException {

        Movie movie = movieRepository.findById(movieId).get();
        if(movie == null){
            return "Movie with specified id does not exist.";
        }
        String movieTitle = movie.getTitle();
        String movieImageId = "img" + movieId;

        //puts into aws
        try {
            s3Service.putObject(
                    s3Buckets.getAccount(),
                    "movie-images/%s/%s".formatted(movieTitle, movieImageId),
                    file.getBytes());
            return "File uploaded Successfully";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }





    
}
