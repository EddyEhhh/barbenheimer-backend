package com.barbenheimer.ticket.service;

import java.time.*;

import com.barbenheimer.ticket.model.MovieScheduleTime;

public interface SeatService{

    //public List<SeatDetailDTO> getLayout(Long movieId, LocalDate showDate, LocalTime showTime);

    //public boolean checkStatus(Long movieId, LocalDate showDate, LocalTime showTime);

//    /**
//     * this method is a GET request handler
//     * receives details of a customer
//     * returns the seats that the customer selected during this purchase
//     * @return ResponseEntity<List<Seat>>
//     */
//    public List<Seat> selectedBy(CustomerDetail customerDetail);


    /**
     * method is a GET request handler
     * returns the MovieScheduleTime object of a specific movie showing at a specific datetime
     * @return MovieScheduleTime
     */
    public MovieScheduleTime getTime(long movieId, LocalDate showDate, LocalTime showTime);


}
