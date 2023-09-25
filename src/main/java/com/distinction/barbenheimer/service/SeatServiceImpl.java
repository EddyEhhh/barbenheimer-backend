package com.distinction.barbenheimer.service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.distinction.barbenheimer.DTO.SeatDetailDTO;
import com.distinction.barbenheimer.DTO.SeatSelectDTO;
import com.distinction.barbenheimer.DTO.SeatStatusDetailDTO;
import com.distinction.barbenheimer.model.CustomerDetail;
import com.distinction.barbenheimer.model.Hall;
import com.distinction.barbenheimer.model.Movie;
import com.distinction.barbenheimer.model.MovieScheduleDate;
import com.distinction.barbenheimer.model.MovieScheduleTime;
import com.distinction.barbenheimer.model.Seat;
import com.distinction.barbenheimer.model.SeatStatus;
import com.distinction.barbenheimer.repository.MovieRepository;
import com.distinction.barbenheimer.repository.MovieScheduleDateRepository;
import com.distinction.barbenheimer.repository.MovieScheduleTimeRepository;
import com.distinction.barbenheimer.repository.SeatRepository;
import com.distinction.barbenheimer.repository.SeatStatusRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SeatServiceImpl implements SeatService{

    private ModelMapper modelMapper;

    private MovieRepository movieRepository;

    private SeatRepository seatRepository;

    private SeatStatusRepository SeatStatusRepository;

    private MovieScheduleDateRepository movieScheduleDateRepository;

    private MovieScheduleTimeRepository movieScheduleTimeRepository;

    public SeatServiceImpl(ModelMapper modelMapper, SeatRepository seatRepository, SeatStatusRepository seatStatusRepository){
        this.modelMapper = modelMapper;
        this.SeatStatusRepository = seatStatusRepository;
        this.seatRepository = seatRepository;
    }

    // @Override
    // public List<SeatDetailDTO> getLayout(Long movieId, LocalDate showDate, LocalTime showTime) {
    //     MovieScheduleTime movieScheduleTime = movieScheduleTimeRepository.findByMovieScheduleDate_Movie_IdAndMovieScheduleDate_ShowDateAndShowTime(movieId, showDate, showTime).get();
    //     Hall hall = movieScheduleTime.getHall();
    //     List<Seat> layoutSeats = hall.getSeats();
    //     List<SeatDetailDTO> layoutDTO = new ArrayList<>();
    //     for (Seat seat: layoutSeats) {
    //         layoutDTO.add(modelMapper.map(seat, SeatDetailDTO.class));
    //     }
    //     return layoutDTO;
    // }

    @Override
    public List<SeatDetailDTO> getLayout(Long movieId, LocalDate showDate, LocalTime showTime) {
        Optional<Movie> movie = movieRepository.findById(movieId);
        MovieScheduleDate movieScheduleDate = movieScheduleDateRepository.findByMovieAndShowDate(movie, showDate);
        MovieScheduleTime movieScheduleTime = movieScheduleTimeRepository.findByMovieScheduleDateAndShowTime(movieScheduleDate, showTime);
        return null;
    }


    // @Override
    // public MovieScheduleTime getTime(long movieId, LocalDate showDate, LocalTime showTime) {
    //     MovieScheduleTime movieScheduleTime2 = movieScheduleTimeRepository.findByShowTime(LocalTime.of(8,0)).get(0);
    //     log.info("ORDER: " + movieScheduleTime2.toString());
    //     log.info("TEST MOVIEID: " + movieId);
    //     log.info("TEST SHOWDATE: " + showDate.toString());
    //     log.info("TEST SHOWTIME: " + showTime.toString());
    //     MovieScheduleTime movieScheduleTime = movieScheduleTimeRepository.findByMovieScheduleDate_Movie_IdAndMovieScheduleDate_ShowDateAndShowTime(movieId, showDate, showTime).get();
    //     return movieScheduleTime;
    // }

    @Override
    public List<SeatStatusDetailDTO> getStatus(Long movieId, LocalDate showDate, LocalTime showTime) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<Seat> selectedBy(CustomerDetail customerDetail) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectedBy'");
    }
    
//    @Override
//    public List<SeatStatus> status(List<SeatSelectDTO> seats, Long movieId, LocalDateTime showDate, LocalDateTime showTime) {
//         LocalDateTime currentTime = LocalDateTime.now();
//         LocalDateTime withinTime = LocalDateTime.now().plusMinutes(10);
//         for (SeatSelectDTO seatSelectDTO : seats) {
            
//         }
//         return null;
//    }
}
