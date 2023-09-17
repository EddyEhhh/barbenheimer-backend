package com.distinction.barbenheimer.controller;

import java.time.LocalDateTime;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.distinction.barbenheimer.DTO.SeatSelectDTO;
import com.distinction.barbenheimer.DTO.SeatStatusDetailDTO;
import com.distinction.barbenheimer.model.CustomerDetail;
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

    @PostMapping
    public ResponseEntity<List<SeatStatusDetailDTO>> getStatus(@PathVariable Long movieId, @PathVariable LocalDateTime showDate, @PathVariable LocalDateTime showTime) {
        return ResponseEntity.ok(seatService.getStatus(movieId, showDate, showTime));
    }

    @PostMapping("/customer")
    public ResponseEntity<List<Seat>> selectedBy(CustomerDetail customerDetail) {
        return ResponseEntity.ok(seatService.selectedBy(customerDetail));
    }
}
