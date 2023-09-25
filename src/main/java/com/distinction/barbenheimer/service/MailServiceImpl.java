package com.distinction.barbenheimer.service;

import jakarta.activation.DataHandler;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class MailServiceImpl implements MailService{

    @Autowired
    private JavaMailSender mailSender;


    @Value("${mail.email}")
    private String email;


    @Async
    public void sendEmail(String to, String subject, String message){
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();

            mimeMessage.setSubject(subject);
            MimeMessageHelper helper;
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(email);
            helper.setTo(to);
            helper.setText(message, true);
            this.mailSender.send(mimeMessage);
        }catch (MessagingException e){

        }

    }

    @Async
    public void sendEmailWithImage(String to, String subject, String message, byte[] image) throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        mimeMessage.setFrom(email);
        mimeMessage.setRecipients(Message.RecipientType.TO, to);
        mimeMessage.setSentDate(new Date());
        mimeMessage.setSubject(subject);

        // message
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(message, "text/html");

        // multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);

        // image attatchments
        if (image.length > 0) {
                MimeBodyPart imagePart = new MimeBodyPart();
                ByteArrayDataSource imageDataSource = new ByteArrayDataSource(image, "image/png");
                imagePart.setHeader("Content-ID", "<" + "image" + ">");
                imagePart.setDataHandler(new DataHandler(imageDataSource));
                multipart.addBodyPart(imagePart);

        }

        mimeMessage.setContent(multipart);

        this.mailSender.send(mimeMessage);

    }

}