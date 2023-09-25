package com.distinction.barbenheimer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.distinction.barbenheimer.DTO.HallScheduleSeatDetailDTO;
import com.distinction.barbenheimer.DTO.SeatSelectDTO;
import com.distinction.barbenheimer.model.MovieScheduleDate;
import com.distinction.barbenheimer.model.SeatStatus;
import com.distinction.barbenheimer.service.ScheduleService;

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
     * method is a GET request handler
     * it returns 
     * @param showTimeId
     * @return ResponseEntity<HallScheduleSeatDetailDTO>
     */
    @GetMapping
    public ResponseEntity<HallScheduleSeatDetailDTO> getHallLayout(@PathVariable("scheduleTimeId") long showTimeId) {
        return ResponseEntity.ok(scheduleService.getHallLayout(showTimeId));
    }

    @PostMapping
    public ResponseEntity<SeatStatus> selectSeats(@PathVariable("scheduleTimeId") long showTimeId, @RequestBody List<SeatSelectDTO> seatSelectDTOs){
        return null;
    }

    
}
