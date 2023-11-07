package com.barbenheimer.movieservice.controller;

import java.util.List;

import com.barbenheimer.movieservice.dto.HallScheduleSeatDetailDTO;
import com.barbenheimer.movieservice.dto.OngoingPurchaseShortDTO;
import com.barbenheimer.movieservice.dto.SeatSelectDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.barbenheimer.movieservice.exception.AlreadyExistsException;
import com.barbenheimer.movieservice.service.ScheduleService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/schedules/{scheduleTimeId}")
@Slf4j
public class ScheduleController {

    private ScheduleService scheduleService;


    @Autowired
    public ScheduleController(ScheduleService scheduleService){
        this.scheduleService = scheduleService;
    }

    
    /** 
     * method is a GET request handler to obtain a list of seats based on a specific show schedule
     *
     * @param showTimeId selected show schedule
     * @return ResponseEntity<HallScheduleSeatDetailDTO>
     */
    @GetMapping
    public ResponseEntity<HallScheduleSeatDetailDTO> getSeats(@PathVariable("scheduleTimeId") long showTimeId) {
        return ResponseEntity.ok(scheduleService.getSeats(showTimeId));
    }


    /**
     * method is a Post request handler to select a list of seats based on a specific show schedule
     *
     * @param showTimeId selected show schedule
     * @param ongoingPurchaseShortDTO seats being selected and payment intent id as token
     * @return ResponseEntity<?>
     */
    @PostMapping
    public ResponseEntity<?> selectSeats(@PathVariable("scheduleTimeId") long showTimeId, @RequestBody OngoingPurchaseShortDTO ongoingPurchaseShortDTO){
        try {
            return ResponseEntity.ok(scheduleService.selectSeats(showTimeId, ongoingPurchaseShortDTO));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    
}
