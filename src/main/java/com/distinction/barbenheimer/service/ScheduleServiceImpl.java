package com.distinction.barbenheimer.service;

import java.util.*;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.distinction.barbenheimer.DTO.HallScheduleSeatDetailDTO;
import com.distinction.barbenheimer.DTO.ScheduleSeatDetailDTO;
import com.distinction.barbenheimer.model.Hall;
import com.distinction.barbenheimer.model.MovieScheduleTime;
import com.distinction.barbenheimer.model.Seat;
import com.distinction.barbenheimer.model.SeatStatus;
import com.distinction.barbenheimer.repository.MovieScheduleTimeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService{

    private MovieScheduleTimeRepository movieScheduleTimeRepository;

    private ModelMapper modelMapper;

    @Override
    public HallScheduleSeatDetailDTO getHall(long showTimeId) {
        MovieScheduleTime movieScheduleTime = movieScheduleTimeRepository.findById(showTimeId);
        Hall hall = movieScheduleTime.getHall();
        List<ScheduleSeatDetailDTO> scheduleSeatDetailDTO = new ArrayList<>();
        for (Seat seat : hall.getSeats()) {
            List<SeatStatus> seatStatus = seat.getSeatStatuses();
            if (seatStatus != null) {
                int state = seatStatus.get(0).getState();
                scheduleSeatDetailDTO.add(ScheduleSeatDetailDTO.builder()
                                          .rowCharacter(seat.getRowCharacter())
                                          .columnNumber(seat.getColumnNumber())
                                          .x(seat.getX())
                                          .y(seat.getY())
                                          .state(state)
                                          .build());
            }
        }
        HallScheduleSeatDetailDTO hallScheduleSeatDetailDTO = HallScheduleSeatDetailDTO.builder()
                                                              .number(hall.getNumber())
                                                              .seats(scheduleSeatDetailDTO)
                                                              .build();
        return hallScheduleSeatDetailDTO;
    }
}
