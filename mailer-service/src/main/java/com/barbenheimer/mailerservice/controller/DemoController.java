package com.barbenheimer.mailerservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/demo")
@Slf4j
public class DemoController {

    private ApplicationEventPublisher applicationEventPublisher;


    @Autowired
    public DemoController(ApplicationEventPublisher applicationEventPublisher){
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @GetMapping
    public String demo(){
        return "Hello from mailer service!";
    }


//    @GetMapping("/mail")
//    public String demoMail(Purchase purchase){
//        applicationEventPublisher.publishEvent(new TicketPurchaseCompleteEvent(purchase));
//        return "emailing";
//    }

}
