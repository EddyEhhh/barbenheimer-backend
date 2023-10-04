package com.distinction.barbenheimer.service;

import java.time.*;
import java.util.*;

import org.springframework.cglib.core.Local;

import com.distinction.barbenheimer.DTO.SeatDetailDTO;
import com.distinction.barbenheimer.DTO.SeatSelectDTO;
import com.distinction.barbenheimer.DTO.SeatStatusDetailDTO;
import com.distinction.barbenheimer.model.CustomerDetail;
import com.distinction.barbenheimer.model.Hall;
import com.distinction.barbenheimer.model.MovieScheduleTime;
import com.distinction.barbenheimer.model.Seat;
import com.distinction.barbenheimer.model.SeatStatus;

public interface SeatService{

    //public List<SeatDetailDTO> getLayout(Long movieId, LocalDate showDate, LocalTime showTime);

    //public List<SeatStatusDetailDTO> getStatus(Long movieId, LocalDate showDate, LocalTime showTime);

    public List<Seat> selectedBy(CustomerDetail customerDetail);

    public MovieScheduleTime getTime(long movieId, LocalDate showDate, LocalTime showTime);
}
