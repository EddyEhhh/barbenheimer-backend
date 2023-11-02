package com.barbenheimer.ticket.serviceImpl;

import java.time.*;
import java.util.List;
import java.util.Optional;

import com.barbenheimer.ticket.dto.SeatDetailDTO;
import com.barbenheimer.ticket.model.Movie;
import com.barbenheimer.ticket.model.MovieScheduleDate;
import com.barbenheimer.ticket.model.MovieScheduleTime;
import com.barbenheimer.ticket.model.Seat;
import com.barbenheimer.ticket.service.SeatService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.barbenheimer.customer.model.CustomerDetail;
import com.barbenheimer.ticket.repository.MovieRepository;
import com.barbenheimer.ticket.repository.MovieScheduleDateRepository;
import com.barbenheimer.ticket.repository.MovieScheduleTimeRepository;
import com.barbenheimer.ticket.repository.SeatRepository;
import com.barbenheimer.ticket.repository.SeatStatusRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SeatServiceImpl implements SeatService {

    private ModelMapper modelMapper;

    private MovieRepository movieRepository;

    private SeatRepository seatRepository;

    private com.barbenheimer.ticket.repository.SeatStatusRepository SeatStatusRepository;

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

    // @Override
    // public boolean checkStatus(Long movieId, LocalDate showDate, LocalTime showTime){
    //     return null;
    // }


//    @Override
//    public List<Seat> selectedBy(CustomerDetail customerDetail) {
//        // TODO Auto-generated method stub
//        throw new UnsupportedOperationException("Unimplemented method 'selectedBy'");
//    }
    
//    @Override
//    public List<SeatStatus> status(List<SeatSelectDTO> seats, Long movieId, LocalDateTime showDate, LocalDateTime showTime) {
//         LocalDateTime currentTime = LocalDateTime.now();
//         LocalDateTime withinTime = LocalDateTime.now().plusMinutes(10);
//         for (SeatSelectDTO seatSelectDTO : seats) {
            
//         }
//         return null;
//    }
}
