package com.distinction.barbenheimer.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.distinction.barbenheimer.model.CustomerDetail;
import com.distinction.barbenheimer.model.Seat;
import com.distinction.barbenheimer.service.SeatService;

@RestController
@RequestMapping("/api/v1/seats")
public class SeatController {
    
    private SeatService seatService;

    @Autowired
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    } 

    @PutMapping("/customer")
    public ResponseEntity<List<Seat>> selectedBy(CustomerDetail customerDetail) {
        return ResponseEntity.ok(seatService.selectedBy(customerDetail));
    }
}
