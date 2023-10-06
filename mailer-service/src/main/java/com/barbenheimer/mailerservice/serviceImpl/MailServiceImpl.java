package com.barbenheimer.mailerservice.serviceImpl;

import com.barbenheimer.mailerservice.dto.TicketMailDetailDTO;
import com.barbenheimer.mailerservice.event.TicketPurchaseCompleteEvent;
import com.barbenheimer.mailerservice.service.MailService;
import com.barbenheimer.mailerservice.util.StringUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.activation.DataHandler;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.mail.util.ByteArrayDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

@Service
@Slf4j
public class MailServiceImpl implements MailService {

    private JavaMailSender mailSender;

    private StringUtil stringUtil;


    @Value("${mail.email}")
    private String email;

    @Autowired
    public MailServiceImpl(JavaMailSender mailSender,  StringUtil stringUtil){
        this.mailSender = mailSender;
        this.stringUtil = stringUtil;
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

    public void sendEmailOnTickerPurchaseCompleteEvent(TicketMailDetailDTO ticketMailDetailDTO) {

        String mailSubject = "Movie ticket purchase id: " + ticketMailDetailDTO.getPurchaseId();
        String mailMessage = stringUtil.getStringFromFile("/template/ticket.html");


        Map<String, Object> params = new HashMap<>();
        params.put("ticketId", ticketMailDetailDTO.getPurchaseId());
        params.put("movieTitle", ticketMailDetailDTO.getMovieTitle());
        params.put("movieRating", ticketMailDetailDTO.getMovieAgeRating());
        params.put("movieShowtime", ticketMailDetailDTO.getMovieShowtime());
        params.put("hallNumber", ticketMailDetailDTO.getHallNumber());
        params.put("ticketSeats", ticketMailDetailDTO.getTicketSeats());
        params.put("purchaseDetail", ticketMailDetailDTO.getPurchaseDetail());
        params.put("purchaseTotalPrice", ticketMailDetailDTO.getPurchaseTotalPrice());
        params.put("ticketQrCid", "cid:image");

//        Map<String, String> imageAttach = new HashMap<>();

        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        try {
            bitMatrix = barcodeWriter.encode("/purchaseId/".concat(String.valueOf(ticketMailDetailDTO.getPurchaseId())), BarcodeFormat.QR_CODE, 200, 200);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] image;
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, "png", bos);
            image = bos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        mailMessage = StringSubstitutor.replace(mailMessage, params, "${", "}");


        try {
            sendEmailWithImage(ticketMailDetailDTO.getCustomerEmail(), mailSubject, mailMessage, image);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
//        log.info(String.format("Verify account link temp: %s", url));
    }


}
