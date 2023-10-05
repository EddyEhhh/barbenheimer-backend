package com.barbenheimer.mailerservice;

import com.barbenheimer.mailerservice.event.TicketPurchaseCompleteEvent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
public class MailerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailerServiceApplication.class, args);
	}

//	@KafkaListener(topics = "mailerTopic")
//	public void handleMailer(TicketPurchaseCompleteEvent ticketPurchaseCompleteEvent){
//
//	}

}
