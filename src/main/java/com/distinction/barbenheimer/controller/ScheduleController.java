package com.distinction.barbenheimer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.distinction.barbenheimer.DTO.HallScheduleSeatDetailDTO;
import com.distinction.barbenheimer.model.MovieScheduleDate;
import com.distinction.barbenheimer.service.ScheduleService;

import lombok.extern.slf4j.Slf4j;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/schedules")
@Slf4j
public class ScheduleController {

    private ScheduleService scheduleService;

    
    /** 
     * method is a GET request handler
     * it returns 
     * @param showTimeId
     * @return ResponseEntity<HallScheduleSeatDetailDTO>
     */
    @GetMapping("/{scheduleTimeId}")
    public ResponseEntity<HallScheduleSeatDetailDTO> getHall(@PathVariable("scheduleTimeId") long showTimeId) {
        return ResponseEntity.ok(scheduleService.getHall(showTimeId));
    }
}
