package com.barbenheimer.movieservice.serviceImpl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

import com.barbenheimer.movieservice.dto.MovieDetailsDTO;
import com.barbenheimer.movieservice.dto.MovieShortDTO;
import com.barbenheimer.movieservice.dto.MovieTitleDTO;
import com.barbenheimer.movieservice.s3.S3Buckets;
import com.barbenheimer.movieservice.s3.S3Service;
import com.barbenheimer.movieservice.exception.ResourceNotFoundException;
import com.barbenheimer.movieservice.model.MovieImage;
import com.barbenheimer.movieservice.repository.MovieImageRepository;
import com.barbenheimer.movieservice.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.barbenheimer.movieservice.model.Movie;
import com.barbenheimer.movieservice.repository.MovieRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class MovieServiceImpl implements MovieService {

    private MovieRepository movieRepository;

    private MovieImageRepository movieImageRepository;

    private S3Service s3Service;

    private S3Buckets s3Buckets;

    private ModelMapper modelMapper;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, MovieImageRepository movieImageRepository, ModelMapper modelMapper, S3Service s3Service, S3Buckets s3Buckets){
        this.modelMapper = modelMapper;
        this.movieRepository = movieRepository;
        this.movieImageRepository = movieImageRepository;
        this.s3Service = s3Service;
        this.s3Buckets = s3Buckets;
    }

    
    /** 
     * method returns all movies now showing
     * @return List<Movie>
     */
    @Override
    public List<MovieShortDTO> getAllCurrent() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        List<Movie> movieList = movieRepository.findByLastShowingDateAfter(now);
        List<MovieShortDTO> movieDTOList = new ArrayList<>();
        for (Movie eachMovie : movieList) {
            movieDTOList.add(modelMapper.map(eachMovie, MovieShortDTO.class));
        }
        return movieDTOList;
    }

    


    
    
    
    /** 
     * method returns current showing movies' title and id
     * 
     * @return List<MovieTitleDTO>
     */
    @Override
    public List<MovieTitleDTO> getAllTitleAndId() {
        List<Movie> movieList = movieRepository.findAll();
        List<MovieTitleDTO> movieTitleDTOList = new ArrayList<>();
        for (Movie movie: movieList) {
            movieTitleDTOList.add(modelMapper.map(movie, MovieTitleDTO.class));
        }
        return movieTitleDTOList;
    }




    /** 
     * method returns all movies with names matching that of user input
     * 
     * @param movieName
     * @return List<Movie>
     */
    @Override
    public List<MovieShortDTO> getMoviesBySearch(String movieTitle) {
        List<Movie> matchingMovies = movieRepository.findByTitleContaining(movieTitle);
        if (matchingMovies == null) {
            throw new RuntimeException("Movie does not exist");
        } 
        List<MovieShortDTO> matchingDTOs = matchingMovies
                .stream()
                .map(eachMovie -> modelMapper.map(eachMovie, MovieShortDTO.class))
                .collect(Collectors.toList());

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
            throw new ResourceNotFoundException("Movie with id" + movieId + "does not exist.");
        }
        String movieTitle = movie.getTitle();
        String movieImageId = "img" + movieId;
        List<MovieImage> movieImages = movie.getMovieImages();
        if(movieImages != null && !movieImages.isEmpty()){
            int imageCount = movieImages.size();
            movieImageId += "_" + imageCount;
        }

        // saves the imageUrl into database
        saveMovieImage(movie, movieImages, movieTitle, movieImageId);


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

    public void saveMovieImage(Movie movie, List<MovieImage> movieImages, String movieTitle, String movieImageId){
        String imageUrl = "https://barbenheimer-movies.s3.ap-southeast-1.amazonaws.com/movie-images/" + movieTitle + "/" + movieImageId;
        MovieImage movieImage = new MovieImage();
        movieImage.setMovie(movie);
        movieImage.setImageUrl(imageUrl);
        movieImageRepository.save(movieImage);
        movieImages.add(movieImage);
        movie.setMovieImages(movieImages);
        movieRepository.save(movie);
    }









    
}
