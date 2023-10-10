package com.barbenheimer.mailerservice.listener;

import com.barbenheimer.mailerservice.dto.TicketMailDetailDTO;
import com.barbenheimer.mailerservice.service.MailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

@Configuration
@Slf4j
public class Consumer {



    private ObjectMapper objectMapper;

    private MailService mailService;

    @Autowired
    public Consumer(ObjectMapper objectMapper, MailService mailService) {
        this.objectMapper = objectMapper;
        this.mailService = mailService;
    }

    @KafkaListener(topics = "mailerTopic", id = "mailerId")
    public void handleMailer(String ticketMailDetail){
        try{
            TicketMailDetailDTO ticketMailDetailDTO = objectMapper.readValue(ticketMailDetail, TicketMailDetailDTO.class);
            mailService.sendEmailOnTickerPurchaseCompleteEvent(ticketMailDetailDTO);

        }catch (Exception e){
        }
    }

}
