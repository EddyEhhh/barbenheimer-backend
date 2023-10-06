package com.barbenheimer.movieservice.serviceImpl;

import com.barbenheimer.movieservice.dto.PurchaseDTO;
import com.barbenheimer.movieservice.dto.PurchaseResponseDTO;
import com.barbenheimer.movieservice.dto.SeatStatusDetailDTO;
import com.barbenheimer.movieservice.dto.TicketMailDetailDTO;
import com.barbenheimer.movieservice.model.*;
import com.barbenheimer.movieservice.repository.PurchaseRepository;
import com.barbenheimer.movieservice.service.PurchaseService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Value("${STRIPE_PRIVATE_KEY}")
    private String stripeApiKey;

    private PurchaseRepository purchaseRepository;

    private final KafkaTemplate<String, TicketMailDetailDTO> kafkaTemplate;


    @Autowired
    public PurchaseServiceImpl(KafkaTemplate kafkaTemplate, PurchaseRepository purchaseRepository){
        this.kafkaTemplate = kafkaTemplate;
        this.purchaseRepository = purchaseRepository;
    }

    //TODO: define the clock
    @Override
    public ResponseEntity<?> savePurchase(CustomerDetail customerDetail, SeatStatusDetailDTO seatStatusDetailDTO, PurchaseDTO purchaseDTO) {
        Purchase purchase = Purchase.builder()
                .customerDetail(customerDetail)
                .dateTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .paidAmount(purchaseDTO.getPriceInCents() * purchaseDTO.getQuantity())
                .build();
        return ResponseEntity.ok(purchaseRepository.save(purchase));
    }

    public PurchaseResponseDTO createPaymentIntent(PurchaseDTO purchaseDTO) throws StripeException {
        Stripe.apiKey = stripeApiKey;
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(purchaseDTO.getQuantity() * purchaseDTO.getPriceInCents())
                .setCurrency("sgd")
                .build();
        PaymentIntent paymentIntent = PaymentIntent.create(params);
        PurchaseResponseDTO purchaseResponseDTO = PurchaseResponseDTO.builder()
                .clientSecret(paymentIntent.getClientSecret())
                .build();
        return purchaseResponseDTO;
    }

    public void sendMail(Purchase purchase){

        MovieScheduleTime movieScheduleTime = purchase.getSeatStatuses().get(0).getMovieScheduleTime();
        Movie movie = purchase.getSeatStatuses().get(0).getMovieScheduleTime().getMovieScheduleDate().getMovie();
        String showdatetime = LocalDateTime.of(movieScheduleTime.getMovieScheduleDate().getShowDate(), movieScheduleTime.getShowTime()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM,FormatStyle.MEDIUM));

        List<String> ratingBadgeString = Arrays.asList("G","PG","PG13","NC16","M18","R21","NAR");

        StringBuilder ticketSeats = new StringBuilder();
        int ticketCount = 0;
        for(SeatStatus eachSeat :purchase.getSeatStatuses()){
            ticketSeats.append(eachSeat.getSeat().getRowCharacter().concat(String.valueOf(eachSeat.getSeat().getColumnNumber())));
            ticketSeats.append(", ");
            ticketCount++;
        }
        ticketSeats.delete(ticketSeats.length()-2,ticketSeats.length()-1);

        TicketMailDetailDTO ticketMailDetailDTO = TicketMailDetailDTO.builder()
                .customerEmail(purchase.getCustomerDetail().getEmail())
                .purchaseId(purchase.getId())
                .movieTitle(movie.getTitle())
                .movieAgeRating(ratingBadgeString.get(movie.getAgeRestriction()))
                .movieShowtime(showdatetime)
                .hallNumber(String.valueOf(movieScheduleTime.getHall().getNumber()))
                .ticketSeats(ticketSeats.toString())
                .purchaseDetail(String.valueOf(ticketCount).concat(" x ticket(s)"))
                .purchaseTotalPrice("$".concat(String.valueOf(purchase.getPaidAmount())))
                .build();
        kafkaTemplate.send("mailerTopic", ticketMailDetailDTO);

    }




}
