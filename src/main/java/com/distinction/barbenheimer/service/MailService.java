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

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Service
@Slf4j
public class MailService {

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
    public void sendEmailWithBase64Attachment(String to, String subject, String message, Map<String, String> inlineImages) throws MessagingException {
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
        if (inlineImages != null && inlineImages.size() > 0) {
            Set<String> setImageID = inlineImages.keySet();

            for (String contentId : setImageID) {
                MimeBodyPart imagePart = new MimeBodyPart();
                try {
                    ByteArrayDataSource imageDataSource = new ByteArrayDataSource(inlineImages.get(contentId), "image/png");
                    imagePart.setHeader("Content-ID", "<" + contentId + ">");
                    imagePart.setDisposition(MimeBodyPart.INLINE);
                    imagePart.setDataHandler(new DataHandler(imageDataSource));
//                    imagePart.setFileName(contentId+".png");
                    imagePart.setContent(inlineImages.get(contentId), "image/png");
                    log.info("Image Part: " + inlineImages.get(contentId));

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                multipart.addBodyPart(imagePart);
            }
        }

        mimeMessage.setContent(multipart);

        this.mailSender.send(mimeMessage);

    }

}
