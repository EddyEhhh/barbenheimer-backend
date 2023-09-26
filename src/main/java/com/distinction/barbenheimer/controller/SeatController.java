package com.distinction.barbenheimer.controller;

import java.time.*;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.distinction.barbenheimer.DTO.SeatSelectDTO;
import com.distinction.barbenheimer.DTO.SeatStatusDetailDTO;
import com.distinction.barbenheimer.exception.AlreadyExistsException;
import com.distinction.barbenheimer.exception.SeatUnavailableException;
import com.distinction.barbenheimer.model.CustomerDetail;
import com.distinction.barbenheimer.model.MovieScheduleTime;
import com.distinction.barbenheimer.model.Seat;
import com.distinction.barbenheimer.model.SeatStatus;
import com.distinction.barbenheimer.service.SeatService;

@RestController
@RequestMapping("/api/v1/movies/{movieId}/showDates/{showDate}/showTimes/{showTime}")
public class SeatController {
    
    private SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    } 

    // @GetMapping
    // public ResponseEntity<?> checkStatus(@PathVariable Long movieId, @PathVariable LocalDate showDate, @PathVariable LocalTime showTime) {
    
    //     seatService.checkStatus(movieId, showDate, showTime);
    //     return ResponseEntity.ok(seatService.checkStatus(movieId, showDate, showTime));
    // }

    @PostMapping("/customer")
    public ResponseEntity<List<Seat>> selectedBy(CustomerDetail customerDetail) {
        return ResponseEntity.ok(seatService.selectedBy(customerDetail));
    }

    //testing
    // @GetMapping
    // public ResponseEntity<MovieScheduleTime> getTime(@PathVariable("movieId") long movieId, @PathVariable("showDate") LocalDate showDate, @PathVariable("showTime") LocalTime showTime) {
    //     return ResponseEntity.ok(seatService.getTime(movieId, showDate, showTime));
    // }
}
