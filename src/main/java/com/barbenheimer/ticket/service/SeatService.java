package com.barbenheimer.ticket.service;

import java.time.*;
import java.util.*;

import com.barbenheimer.ticket.dto.SeatDetailDTO;
import com.barbenheimer.ticket.model.Seat;
import com.barbenheimer.customer.model.CustomerDetail;

public interface SeatService{

    public List<SeatDetailDTO> getLayout(Long movieId, LocalDate showDate, LocalTime showTime);

    //public boolean checkStatus(Long movieId, LocalDate showDate, LocalTime showTime);

//    public List<Seat> selectedBy(CustomerDetail customerDetail);


    //public MovieScheduleTime getTime(long movieId, LocalDate showDate, LocalTime showTime);


}
