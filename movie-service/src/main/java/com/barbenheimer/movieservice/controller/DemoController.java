package com.barbenheimer.movieservice.controller;

import com.barbenheimer.movieservice.event.TicketPurchaseCompleteEvent;
import com.barbenheimer.movieservice.model.Purchase;
import com.barbenheimer.movieservice.repository.PurchaseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/demo")
@Slf4j
public class DemoController {

    private ApplicationEventPublisher applicationEventPublisher;

    private PurchaseRepository purchaseRepository;

    private final KafkaTemplate<String, TicketPurchaseCompleteEvent> kafkaTemplate;

    @Autowired
    public DemoController(KafkaTemplate kafkaTemplate, ApplicationEventPublisher applicationEventPublisher, PurchaseRepository purchaseRepository){
        this.applicationEventPublisher = applicationEventPublisher;
        this.purchaseRepository = purchaseRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping
    public String demo(){
        return "Hello there!";
    }


    @GetMapping("/mail")
    public String demoMail(){
        Purchase purchase = purchaseRepository.findById(Long.valueOf(1)).get();
        log.info(purchase.toString());
        kafkaTemplate.send("mailerTopic", new TicketPurchaseCompleteEvent(purchase));
        return "mailed";
//        return "emailing";
    }

}
