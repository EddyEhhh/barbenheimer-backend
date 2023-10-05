package com.barbenheimer.mailerservice.serviceImpl;

import com.barbenheimer.mailerservice.service.MailService;
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
public class MailServiceImpl implements MailService {

    private JavaMailSender mailSender;


    @Value("${mail.email}")
    private String email;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender){
        this.mailSender = mailSender;
    }


    /**
     * sends an email to the specified email address with the specified message and subject
     *
     * @param to receiver email
     * @param subject email subject
     * @param message email body
     * @return void
     */
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

    /**
     * sends an email to the specified email address with the specified message, subject and image
     *
     * @param to receiver email
     * @param subject email subject
     * @param message email body
     * @param image image data in bytes
     * @return void
     */
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
