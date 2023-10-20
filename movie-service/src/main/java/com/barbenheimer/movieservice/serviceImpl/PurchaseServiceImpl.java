package com.barbenheimer.movieservice.serviceImpl;



import com.barbenheimer.movieservice.dto.PurchaseDTO;
import com.barbenheimer.movieservice.dto.PurchaseShortDTO;
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
import com.stripe.model.Customer;
import com.stripe.model.Event;

import com.stripe.model.PaymentIntent;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionRetrieveParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
     * This method will be called when a checkout session has been completed. It calls the savePurchase function
     * to save the purchase into the database for persistence.
     * @param payload
     * @param sigHeader
     * @return ResponseEntity<?>
     */
    public ResponseEntity<?> checkoutSessionStatus(String payload, String sigHeader){
        Event event;

        try{
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (JsonSyntaxException | SignatureVerificationException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }

        switch (event.getType()) {

            case "checkout.session.completed" : {
                Session session = (Session) event.getDataObjectDeserializer().getObject().get();
                System.out.println("Session with sessionId: " + session.getId() + " completed. Awaiting payment.");
            }
            case "checkout.session.async_payment_succeeded": {
                Session session = (Session) event.getDataObjectDeserializer().getObject().get();
                //creates and saves a new customer detail with email if it doesn't exist in the database
                saveCustomerDetailIfNotExists(session.getCustomerEmail());
                //saves the purchase into database
                savePurchase(session);
                System.out.println("Payment succeeded and purchase is saved successfully");
            }
            case "checkout.session.async_payment_failed": {
                Session session = (Session) event.getDataObjectDeserializer().getObject().get();
                System.out.println("Session with sessionId: " + session.getId() + " failed.");
            }
            default:
                System.out.println("Unhandled event type: " + event.getType());
        }

        return ResponseEntity.ok("");
    }


    /**
     * This method returns a purchase corresponding to a checkout session in the form of a purchaseShortDTO.
     * It is meant for the frontend to retrieve a purchase detail to be shown as a payment summary for the customer post-payment.
     * @param checkoutSessionId
     * @return PurchaseShortDTO
     * @throws StripeException
     */

    public PurchaseShortDTO getPurchaseByCheckoutSession(String checkoutSessionId) throws StripeException {
        Session session = Session.retrieve(checkoutSessionId);

        CustomerDetail customerDetail = getCustomerDetailBySession(session);
        OngoingPurchase ongoingPurchase = getOngoingPurchaseBySession(session);

        return PurchaseShortDTO.builder()
                .customerDetail(customerDetail)
                .paidAmount(session.getAmountTotal())
                .seatStatuses(ongoingPurchase.getSeatStatus())
                .build();
    }

    /**
     * This method saves the purchase made into database given the checkout session.
     * @param session
     * @return ResponseEntity
     */
    public ResponseEntity savePurchase(Session session) {

        CustomerDetail customerDetail = getCustomerDetailBySession(session);
        OngoingPurchase ongoingPurchase = getOngoingPurchaseBySession(session);

        Purchase purchase = purchaseRepository.save(Purchase.builder()
                .customerDetail(customerDetail)
                .seatStatuses(ongoingPurchase.getSeatStatus())
                .paidAmount(session.getAmountTotal())
                .dateTime(LocalDateTime.now())
                .build());

        return ResponseEntity.ok(purchase);
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


    /*
     * Below are all helper methods
     *
    */


    public void saveCustomerDetailIfNotExists(String customerEmail){
        Optional<CustomerDetail> customerDetail = customerDetailRepository.findByEmail(customerEmail);
        if(customerDetail.isEmpty()) {
            customerDetailRepository.save(CustomerDetail.builder().email(customerEmail).build());
        }
    }

    public CustomerDetail getCustomerDetailBySession(Session session){
        CustomerDetail customerDetail = customerDetailRepository.findByEmail(session.getCustomerEmail())
                .orElseThrow(() -> new ResourceNotFoundException("CustomerDetail with email: " + session.getCustomerEmail() + " does not exist."));
        return customerDetail;
    }

    public OngoingPurchase getOngoingPurchaseBySession(Session session){
        Long ongoingPurchaseId = Long.parseLong(session.getMetadata().get("ongoingPurchaseId"));
        OngoingPurchase ongoingPurchase = ongoingPurchaseRepository.findById(ongoingPurchaseId)
                .orElseThrow(() -> new ResourceNotFoundException("OngoingPurchase with id: " + ongoingPurchaseId + " does not exist."));
        return ongoingPurchase;
    }





}
