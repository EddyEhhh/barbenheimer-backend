package com.barbenheimer.ticket.controller;

import com.barbenheimer.ticket.event.TicketPurchaseCompleteEvent;
import com.barbenheimer.ticket.repository.PurchaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

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
