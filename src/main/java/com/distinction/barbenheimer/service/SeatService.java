package com.distinction.barbenheimer.service;

import java.time.*;
import java.util.*;

import com.distinction.barbenheimer.dto.SeatDetailDTO;
import com.distinction.barbenheimer.model.CustomerDetail;
import com.distinction.barbenheimer.model.Seat;

public interface SeatService{

    public List<SeatDetailDTO> getLayout(Long movieId, LocalDate showDate, LocalTime showTime);

    //public boolean checkStatus(Long movieId, LocalDate showDate, LocalTime showTime);

    public List<Seat> selectedBy(CustomerDetail customerDetail);


    //public MovieScheduleTime getTime(long movieId, LocalDate showDate, LocalTime showTime);


}
