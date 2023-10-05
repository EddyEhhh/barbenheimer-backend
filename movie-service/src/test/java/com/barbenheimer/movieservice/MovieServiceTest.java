package com.barbenheimer.movieservice;

import com.barbenheimer.movieservice.dto.MovieDetailsDTO;
import com.barbenheimer.movieservice.dto.MovieShortDTO;
import com.barbenheimer.movieservice.dto.MovieTitleDTO;
import com.barbenheimer.movieservice.model.Movie;
import com.barbenheimer.movieservice.model.MovieImage;
import com.barbenheimer.movieservice.repository.MovieImageRepository;
import com.barbenheimer.movieservice.repository.MovieRepository;
import com.barbenheimer.movieservice.serviceImpl.MovieServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MovieServiceTest {

    @Mock
    private MovieRepository movies;

    @Mock
    private MovieImageRepository movieImages;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private MovieServiceImpl movieService;


    @Test
    void getAllCurrent_ReturnCurrentMovies(){
        List<Movie> movieList = new ArrayList<>();
        movieList.add(Movie.builder().lastShowingDate(LocalDateTime.now().plusDays(15)).build());
        movieList.add(Movie.builder().lastShowingDate(LocalDateTime.now().plusMonths(1)).build());

        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        when(movies.findByLastShowingDateAfter(now)).thenReturn(movieList);

        List<MovieShortDTO> movieShortDTOList = movieService.getAllCurrent();

        assertNotNull(movieShortDTOList);
        assertEquals(2, movieShortDTOList.size());
        verify(movies).findByLastShowingDateAfter(now);
    }

    @Test
    void getAllTitleAndId_ReturnMovieTitleDTOs(){
        List<Movie> movieList = new ArrayList<>();
        movieList.add(Movie.builder().title("fakename1").build());
        movieList.add(Movie.builder().title("fakename2").build());

        when(movies.findAll()).thenReturn(movieList);

        List<MovieTitleDTO> movieTitleDTOList = movieService.getAllTitleAndId();

        assertNotNull(movieTitleDTOList);
        assertEquals(2, movieTitleDTOList.size());
        assertEquals("fakename1", movieTitleDTOList.get(0).getTitle());
        assertEquals("fakename2", movieTitleDTOList.get(1).getTitle());
        verify(movies).findAll();
    }

    @Test
    void getMoviesBySearch_ReturnMatchingMovies(){
        List<Movie> movieList = new ArrayList<>();
        movieList.add(Movie.builder().title("fakename1").build());
        movieList.add(Movie.builder().title("fakename2").build());

        when(movies.findByTitleContaining("fake")).thenReturn(movieList);

        List<MovieShortDTO> movieShortDTOList = movieService.getMoviesBySearch("fake");

        assertNotNull(movieShortDTOList);
        assertEquals(2, movieShortDTOList.size());
        assertEquals("fakename1", movieShortDTOList.get(0).getTitle());
        assertEquals("fakename2", movieShortDTOList.get(1).getTitle());
        verify(movies).findByTitleContaining("fake");
    }

    @Test
    void getMoviesBySearch_ReturnNull(){
        when(movies.findByTitleContaining("fake")).thenReturn(null);

        Exception exception = assertThrows(RuntimeException.class, () -> {
            movieService.getMoviesBySearch("fake");
        });

        assertEquals("Movie does not exist", exception.getMessage());
        verify(movies).findByTitleContaining("fake");
    }

    @Test
    void getDetails_ReturnMovieDetailsDTO(){
        Long movieId = 1L;
        Movie movie = Movie.builder().id(movieId).title("fakename").description("this is a fake movie").build();

        when(movies.findById(movieId)).thenReturn(Optional.ofNullable(movie));

        MovieDetailsDTO movieDetailsDTO = movieService.getDetails(movieId);

        assertNotNull(movieDetailsDTO);
        assertEquals(movie.getTitle(), movieDetailsDTO.getTitle());
        assertEquals(movie.getDescription(), movieDetailsDTO.getDescription());
        verify(movies).findById(movieId);
    }

    @Test
    void saveMovieImage(){
        String movieImageId = "1";
        Movie movie = Movie.builder().title("fakename").movieImages(new ArrayList<MovieImage>()).build();
        String movieImageUrl = "https://barbenheimer203-movies.s3.ap-southeast-1.amazonaws.com/movie-images/" + movie.getTitle() + "/" + movieImageId;

        MovieImage movieImage = MovieImage.builder().movie(movie).imageUrl(movieImageUrl).build();

        movieService.saveMovieImage(movie, movie.getMovieImages(), movie.getTitle(), movieImageId);

        List<MovieImage> movieImagesList = new ArrayList<>();
        movieImagesList.add(movieImage);

        movie.setMovieImages(movieImagesList);

        verify(movieImages).save(movieImage);
        verify(movies).save(movie);
    }


}

