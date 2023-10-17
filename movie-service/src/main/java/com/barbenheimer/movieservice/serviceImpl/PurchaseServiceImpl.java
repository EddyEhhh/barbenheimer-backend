package com.barbenheimer.movieservice.serviceImpl;



import com.barbenheimer.movieservice.dto.PurchaseDTO;
import com.barbenheimer.movieservice.dto.SeatStatusDetailDTO;
import com.barbenheimer.movieservice.dto.TicketMailDetailDTO;
import com.barbenheimer.movieservice.exception.ResourceNotFoundException;
import com.barbenheimer.movieservice.model.*;
import com.barbenheimer.movieservice.repository.CustomerDetailRepository;
import com.barbenheimer.movieservice.repository.OngoingPurchaseRepository;
import com.barbenheimer.movieservice.repository.PurchaseRepository;
import com.barbenheimer.movieservice.service.PurchaseService;
import com.google.gson.JsonSyntaxException;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;

import com.stripe.model.PaymentIntent;
import com.stripe.net.Webhook;
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
import java.util.*;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Value("${STRIPE_PRIVATE_KEY}")
    private String stripeApiKey;

    @Value("${WEBHOOK_SECRET}")
    private String endpointSecret;

    private PurchaseRepository purchaseRepository;

    private CustomerDetailRepository customerDetailRepository;

    private OngoingPurchaseRepository ongoingPurchaseRepository;

    private final KafkaTemplate<String, TicketMailDetailDTO> kafkaTemplate;


    @Autowired
    public PurchaseServiceImpl(KafkaTemplate kafkaTemplate, PurchaseRepository purchaseRepository, CustomerDetailRepository customerDetailRepository, OngoingPurchaseRepository ongoingPurchaseRepository){
        this.kafkaTemplate = kafkaTemplate;
        this.purchaseRepository = purchaseRepository;
        this.customerDetailRepository = customerDetailRepository;
        this.ongoingPurchaseRepository = ongoingPurchaseRepository;
    }


    /**
     * This method creates a payment intent detailing the amount for payment, the currency in which the payment is to be made in and the
     * description of the product the customer is paying for.
     * @param purchaseDTO
     * @return PaymentIntent
     * @throws StripeException
     */
    public PaymentIntent createPaymentIntent(PurchaseDTO purchaseDTO) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        Map<String, String> metadata = new HashMap<>();
        metadata.put("customerDetailId", String.valueOf(purchaseDTO.getCustomerDetail().getId()));
        metadata.put("ongoingPurchaseId", String.valueOf(purchaseDTO.getOngoingPurchase().getId()));

        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                .setAmount(purchaseDTO.getQuantity() * purchaseDTO.getPriceInCents())
                .setCurrency("sgd")
                .setDescription(purchaseDTO.getQuantity() + " ticket(s) for " + purchaseDTO.getMovieName() + " on " + purchaseDTO.getMovieShowtime() + ", seats " + ticketSeatDescription(purchaseDTO))
                .putAllMetadata(metadata)
                .build();
        return PaymentIntent.create(params);
    }


    /**
     * This method will be called when the customer's payment has successfully went through. It saves the purchase into
     * the database for persistence.
     * @param payload
     * @param sigHeader
     * @return
     */
    public ResponseEntity<?> updatePaymentIntentStatus(String payload, String sigHeader){
        Event event;

        try{
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (JsonSyntaxException | SignatureVerificationException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }

        switch (event.getType()) {
            case "payment_intent.succeeded": {
                //Casts the event into a PaymentIntent object for retrieving purchase data to save into repository.
               PaymentIntent paymentIntent = (PaymentIntent) event
                       .getDataObjectDeserializer()
                       .getObject()
                       .get();
               //Saves the purchase into repository
               savePurchase(paymentIntent);
                return ResponseEntity.status(200).body("Payment Succeeded");
            }
            // ... handle other event types
            default:
                System.out.println("Unhandled event type: " + event.getType());
        }

        return ResponseEntity.ok("Customer purchase succeeded and saved.");
    }



    public void sendMail(Purchase purchase){

        MovieScheduleTime movieScheduleTime = purchase.getSeatStatuses().get(0).getMovieScheduleTime();
        Movie movie = purchase.getSeatStatuses().get(0).getMovieScheduleTime().getMovieScheduleDate().getMovie();
        String showdatetime = LocalDateTime.of(movieScheduleTime.getMovieScheduleDate().getShowDate(), movieScheduleTime.getShowTime()).format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM, FormatStyle.MEDIUM));

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

    public String ticketSeatDescription(PurchaseDTO purchaseDTO){
        StringBuilder ticketSeats = new StringBuilder();
        List<SeatStatus> seatStatuses = purchaseDTO.getOngoingPurchase().getSeatStatus();

        for(int i = 0; i < seatStatuses.size(); i++){
            ticketSeats.append(seatStatuses.get(i).getSeat().getRowCharacter() + seatStatuses.get(i).getSeat().getColumnNumber());
            if(i != seatStatuses.size() - 1){
                ticketSeats.append(", ");
            }
        }
        return ticketSeats.toString();
    }

    @Override
    public ResponseEntity savePurchase(PaymentIntent paymentIntent) {
        Long customerDetailId = Long.parseLong(paymentIntent.getMetadata().get("customerDetailId"));
        CustomerDetail customerDetail = customerDetailRepository.findById(customerDetailId)
                .orElseThrow(() -> new ResourceNotFoundException("CustomerDetail with id: " + customerDetailId + "does not exist."));

        Long ongoingPurchaseId = Long.parseLong(paymentIntent.getMetadata().get("ongoingPurchaseId"));
        OngoingPurchase ongoingPurchase = ongoingPurchaseRepository.findById(ongoingPurchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("OngoingPurchase with id: " + ongoingPurchaseId + "does not exist."));

        Purchase purchase = purchaseRepository.save(Purchase.builder()
                .customerDetail(customerDetail)
                .seatStatuses(ongoingPurchase.getSeatStatus())
                .paidAmount(paymentIntent.getAmount())
                .dateTime(LocalDateTime.now())
                .build());

        return ResponseEntity.ok(purchase);
    }

}
