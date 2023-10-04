package com.barbenheimer.ticket.service;

import jakarta.mail.MessagingException;

public interface MailService {

    public void sendEmail(String to, String subject, String message);
    public void sendEmailWithImage(String to, String subject, String message, byte[] image) throws MessagingException;

}
