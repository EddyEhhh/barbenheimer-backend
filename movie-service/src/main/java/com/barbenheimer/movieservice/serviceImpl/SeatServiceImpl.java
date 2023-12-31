package com.barbenheimer.movieservice.serviceImpl;

import java.time.*;

import com.barbenheimer.movieservice.model.MovieScheduleTime;
import com.barbenheimer.movieservice.service.SeatService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.barbenheimer.movieservice.repository.MovieRepository;
import com.barbenheimer.movieservice.repository.MovieScheduleDateRepository;
import com.barbenheimer.movieservice.repository.MovieScheduleTimeRepository;
import com.barbenheimer.movieservice.repository.SeatRepository;
import com.barbenheimer.movieservice.repository.SeatStatusRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SeatServiceImpl implements SeatService {

    private ModelMapper modelMapper;

    private MovieRepository movieRepository;

    private SeatRepository seatRepository;

    private com.barbenheimer.movieservice.repository.SeatStatusRepository SeatStatusRepository;

    private MovieScheduleDateRepository movieScheduleDateRepository;

    private MovieScheduleTimeRepository movieScheduleTimeRepository;

    public SeatServiceImpl(ModelMapper modelMapper, SeatRepository seatRepository, SeatStatusRepository seatStatusRepository){
        this.modelMapper = modelMapper;
        this.SeatStatusRepository = seatStatusRepository;
        this.seatRepository = seatRepository;
    }


    /**
     * method is a GET request handler
     * returns the MovieScheduleTime object of a specific movie showing at a specific datetime
     * @return MovieScheduleTime
     */
    @Override
    public MovieScheduleTime getTime(long movieId, LocalDate showDate, LocalTime showTime) {
        MovieScheduleTime movieScheduleTime2 = movieScheduleTimeRepository.findByShowTime(LocalTime.of(8,0)).get(0);
//        log.info("ORDER: " + movieScheduleTime2.toString());
//        log.info("TEST MOVIEID: " + movieId);
//        log.info("TEST SHOWDATE: " + showDate.toString());
//        log.info("TEST SHOWTIME: " + showTime.toString());
        MovieScheduleTime movieScheduleTime = movieScheduleTimeRepository.findByMovieScheduleDate_Movie_IdAndMovieScheduleDate_ShowDateAndShowTime(movieId, showDate, showTime).get();
        return movieScheduleTime;
    }

}
