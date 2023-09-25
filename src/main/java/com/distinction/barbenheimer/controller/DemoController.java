package com.distinction.barbenheimer.controller;

import com.distinction.barbenheimer.DTO.MovieDetailsDTO;
import com.distinction.barbenheimer.event.TicketPurchaseCompleteEvent;
import com.distinction.barbenheimer.model.Purchase;
import com.distinction.barbenheimer.repository.PurchaseRepository;
import com.distinction.barbenheimer.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/demo")
@Slf4j
public class DemoController {

    private ApplicationEventPublisher applicationEventPublisher;

    private PurchaseRepository purchaseRepository;

    @Autowired
    public DemoController(ApplicationEventPublisher applicationEventPublisher, PurchaseRepository purchaseRepository){
        this.applicationEventPublisher = applicationEventPublisher;
        this.purchaseRepository = purchaseRepository;
    }

    @GetMapping
    public String demo(){
        return "Hello there!";
    }


    @GetMapping("/mail")
    public String demoMail(){
        applicationEventPublisher.publishEvent(new TicketPurchaseCompleteEvent(purchaseRepository.findById(Long.valueOf(1)).get()));
        return "emailing";
    }

}
