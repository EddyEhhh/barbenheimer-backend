package com.barbenheimer.mailerservice.service;

import com.barbenheimer.mailerservice.dto.TicketMailDetailDTO;
import com.barbenheimer.mailerservice.event.TicketPurchaseCompleteEvent;
import jakarta.mail.MessagingException;

public interface MailService {

    public void sendEmail(String to, String subject, String message);
    public void sendEmailWithImage(String to, String subject, String message, byte[] image) throws MessagingException;

    public void sendEmailOnTickerPurchaseCompleteEvent(TicketMailDetailDTO ticketMailDetailDTO);

}
