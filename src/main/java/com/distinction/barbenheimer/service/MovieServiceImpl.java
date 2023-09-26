package com.distinction.barbenheimer.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import com.distinction.barbenheimer.DTO.MovieShortDTO;
import com.distinction.barbenheimer.DTO.MovieTitleDTO;
import com.distinction.barbenheimer.exception.ResourceNotFoundException;
import com.distinction.barbenheimer.model.MovieImage;
import com.distinction.barbenheimer.repository.MovieImageRepository;
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
        String imageUrl = "https://barbenheimer203-movies.s3.ap-southeast-1.amazonaws.com/movie-images/" + movieTitle + "/" + movieImageId;
        MovieImage movieImage = new MovieImage();
        movieImage.setMovie(movie);
        movieImage.setImageUrl(imageUrl);
        movieImageRepository.save(movieImage);
        movieImages.add(movieImage);
        movie.setMovieImages(movieImages);
        movieRepository.save(movie);
    }









    
}
