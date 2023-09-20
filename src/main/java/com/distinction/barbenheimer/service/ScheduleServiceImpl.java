package com.distinction.barbenheimer.service;

import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.distinction.barbenheimer.DTO.HallScheduleSeatDetailDTO;
import com.distinction.barbenheimer.DTO.ScheduleSeatDetailDTO;
import com.distinction.barbenheimer.model.Hall;
import com.distinction.barbenheimer.model.MovieScheduleTime;
import com.distinction.barbenheimer.model.Seat;
import com.distinction.barbenheimer.model.SeatStatus;
import com.distinction.barbenheimer.repository.MovieScheduleTimeRepository;
import com.distinction.barbenheimer.repository.SeatStatusRepository;

import ch.qos.logback.core.model.Model;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private MovieScheduleTimeRepository movieScheduleTimeRepository;

    private ModelMapper modelMapper;

    private SeatStatusRepository seatStatusRepository;

    @Autowired
    public ScheduleServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public HallScheduleSeatDetailDTO getHallLayout(long showTimeId) { // can use hashmap to solve as well
        MovieScheduleTime movieScheduleTime = movieScheduleTimeRepository.findById(showTimeId);
        Hall hall = movieScheduleTime.getHall();
        // List<ScheduleSeatDetailDTO> scheduleSeatDetailDTO = new ArrayList<>();
        HallScheduleSeatDetailDTO hallScheduleSeatDetailDTO = modelMapper.map(hall, HallScheduleSeatDetailDTO.class);


        // for (Seat seat : hall.getSeats()) {
        //     SeatStatus seatStatus = seatStatusRepository.findByMovieScheduleTimeAndSeat(movieScheduleTime, seat);
        //     if (seatStatus != null) {
        //         int state = seatStatus.getState();
        //         scheduleSeatDetailDTO.add(ScheduleSeatDetailDTO.builder()
        //                 .rowCharacter(seat.getRowCharacter())
        //                 .columnNumber(seat.getColumnNumber())
        //                 .x(seat.getX())
        //                 .y(seat.getY())
        //                 .state(state)
        //                 .build());
        //     }
            // List<SeatStatus> seatStatus = seat.getSeatStatuses();
            // int counter = 0;
            // while (counter < seatStatus.size()) {
            // if (seatStatus != null) {
            //     int state = seatStatus.get(counter).getState();
            //     scheduleSeatDetailDTO.add(ScheduleSeatDetailDTO.builder()
            //             .rowCharacter(seat.getRowCharacter())
            //             .columnNumber(seat.getColumnNumber())
            //             .x(seat.getX())
            //             .y(seat.getY())
            //             .state(state)
            //             .build());
            // }
            // counter++;
            // }
       // }


        return hallScheduleSeatDetailDTO;
    }
}
