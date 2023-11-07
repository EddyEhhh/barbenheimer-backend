package com.barbenheimer.movieservice.controller;

import com.barbenheimer.movieservice.dto.TicketMailDetailDTO;
import com.barbenheimer.movieservice.event.TicketPurchaseCompleteEvent;
import com.barbenheimer.movieservice.model.Purchase;
import com.barbenheimer.movieservice.repository.PurchaseRepository;
import com.barbenheimer.movieservice.service.PurchaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/demo")
@Slf4j
public class DemoController {

    private ApplicationEventPublisher applicationEventPublisher;

    private PurchaseRepository purchaseRepository;

    private PurchaseService purchaseService;


    @Autowired
    public DemoController(ApplicationEventPublisher applicationEventPublisher,
                          PurchaseRepository purchaseRepository,
                          PurchaseService purchaseService){
        this.applicationEventPublisher = applicationEventPublisher;
        this.purchaseRepository = purchaseRepository;
        this.purchaseService = purchaseService;
    }

    @GetMapping("/movie")
    public String demo(){
        return "Hello there!";
    }


    @GetMapping("/mail")
    public String demoMail(){
        log.info("Mailer sent");
        Purchase purchase = purchaseRepository.findById(Long.valueOf(1)).get();
        purchaseService.sendMail(purchase);
        return "emailing";
    }

}
