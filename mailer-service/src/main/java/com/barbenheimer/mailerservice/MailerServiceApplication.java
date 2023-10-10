package com.barbenheimer.mailerservice;

import com.barbenheimer.mailerservice.dto.TicketMailDetailDTO;
import com.barbenheimer.mailerservice.event.TicketPurchaseCompleteEvent;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Map;

@SpringBootApplication
@Slf4j
public class MailerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailerServiceApplication.class, args);
	}


//	@Autowired
//	private MailService mailService;


}
