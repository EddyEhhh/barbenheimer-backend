package com.distinction.barbenheimer.listener;

import com.distinction.barbenheimer.event.TicketPurchaseCompleteEvent;
import com.distinction.barbenheimer.service.MailService;
import com.distinction.barbenheimer.util.StringUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeBodyPart;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@Slf4j
public class TicketPurchaseCompleteEventListener implements ApplicationListener<TicketPurchaseCompleteEvent> {


    private MailService mailService;

    private StringUtil stringUtil;

    @Autowired
    public TicketPurchaseCompleteEventListener(MailService mailService, StringUtil stringUtil){
        this.mailService = mailService;
        this.stringUtil = stringUtil;
    }

    @Override
    public void onApplicationEvent(TicketPurchaseCompleteEvent event) {



        String mailSubject = "Movie ticket purchase id: " + "12345";
        String mailMessage = stringUtil.getStringFromFile("/template/ticket.html");

        Map<String, Object> params = new HashMap<>();
        params.put("ticketId", "12345");
        params.put("movieTitle", "Oppenheimer");
        params.put("movieRating", "PG 13");
        params.put("movieShowtime", "13 Apr 2023, 08:00");
        params.put("hallNumber", "3");
        params.put("ticketSeats", "A1, A2, A3, B11, B12, B13");
        params.put("purchaseDetail", "6 x ticket(s)");
        params.put("purchaseTotalPrice", "$39.00");

        Map<String, String> imageAttach = new HashMap<>();

        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        try {
            bitMatrix = barcodeWriter.encode("/purchase/1234", BarcodeFormat.QR_CODE, 200, 200);
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        String imageBase64;
        try {
            MatrixToImageWriter.writeToStream(bitMatrix, "png", bos);
            imageBase64 = Base64.getEncoder().encodeToString(bos.toByteArray()); // base64 encode
            log.info("Image BASE64: " + imageBase64.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        imageAttach.put("image-1", imageBase64);
        params.put("ticketQrCid", "image-1");


        mailMessage = StringSubstitutor.replace(mailMessage, params, "${", "}");


        try {
            mailService.sendEmailWithBase64Attachment("theitineraryplanners@gmail.com", mailSubject, mailMessage, imageAttach);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
//        log.info(String.format("Verify account link temp: %s", url));
    }

}
