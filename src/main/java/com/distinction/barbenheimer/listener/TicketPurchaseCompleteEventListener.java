package com.distinction.barbenheimer.listener;

import com.distinction.barbenheimer.event.TicketPurchaseCompleteEvent;
import com.distinction.barbenheimer.model.Movie;
import com.distinction.barbenheimer.model.MovieScheduleTime;
import com.distinction.barbenheimer.model.Purchase;
import com.distinction.barbenheimer.model.SeatStatus;
import com.distinction.barbenheimer.service.MailService;
import com.distinction.barbenheimer.service.MailServiceImpl;
import com.distinction.barbenheimer.util.StringUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;

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

        Purchase purchase = event.getPurchase();
        MovieScheduleTime movieScheduleTime = purchase.getSeatStatuses().get(0).getMovieScheduleTime();
        Movie movie = purchase.getSeatStatuses().get(0).getMovieScheduleTime().getMovieScheduleDate().getMovie();
        String showdatetime = LocalDateTime.of(movieScheduleTime.getMovieScheduleDate().getShowDate(), movieScheduleTime.getShowTime()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM,FormatStyle.SHORT));


        List<String> ratingBadgeString = Arrays.asList("G","PG","PG13","NC16","M18","R21","NAR");
        String mailSubject = "Movie ticket purchase id: " + purchase.getId();
        String mailMessage = stringUtil.getStringFromFile("/template/ticket.html");

        StringBuilder ticketSeats = new StringBuilder();
        int ticketCount = 0;
        for(SeatStatus eachSeat :purchase.getSeatStatuses()){
            ticketSeats.append(eachSeat.getSeat().getRowCharacter().concat(String.valueOf(eachSeat.getSeat().getColumnNumber())));
            ticketSeats.append(", ");
            ticketCount++;
        }
        ticketSeats.delete(ticketSeats.length()-3,ticketSeats.length()-1);

        Map<String, Object> params = new HashMap<>();
        params.put("ticketId", purchase.getId());
        params.put("movieTitle", movie.getTitle());
        params.put("movieRating", ratingBadgeString.get(movie.getAgeRestriction()));
        params.put("movieShowtime", showdatetime);
        params.put("hallNumber", movieScheduleTime.getHall().getNumber());
        params.put("ticketSeats", ticketSeats.toString());
        params.put("purchaseDetail", String.valueOf(ticketCount).concat(" x ticket(s)"));
        params.put("purchaseTotalPrice", "$".concat(String.valueOf(purchase.getPaidAmount())));
        params.put("ticketQrCid", "cid:image");

        Map<String, String> imageAttach = new HashMap<>();

        QRCodeWriter barcodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix;
        try {
            bitMatrix = barcodeWriter.encode("/purchase/".concat(String.valueOf(purchase.getId())), BarcodeFormat.QR_CODE, 200, 200);
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
            mailService.sendEmailWithImage(purchase.getCustomerDetail().getEmail(), mailSubject, mailMessage, image);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
//        log.info(String.format("Verify account link temp: %s", url));
    }

}
