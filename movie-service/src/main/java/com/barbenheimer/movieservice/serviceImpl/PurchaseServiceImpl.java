package com.barbenheimer.movieservice.serviceImpl;


import com.barbenheimer.movieservice.dto.*;
import com.barbenheimer.movieservice.exception.ResourceNotFoundException;
import com.barbenheimer.movieservice.model.*;
import com.barbenheimer.movieservice.repository.CustomerDetailRepository;
import com.barbenheimer.movieservice.repository.OngoingPurchaseRepository;
import com.barbenheimer.movieservice.repository.PurchaseRepository;
import com.barbenheimer.movieservice.repository.SeatStatusRepository;
import com.barbenheimer.movieservice.service.PurchaseService;
import com.google.gson.JsonSyntaxException;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.exception.StripeException;
import com.stripe.model.*;
import com.stripe.net.Webhook;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class PurchaseServiceImpl implements PurchaseService {

    @Value("${STRIPE_PRIVATE_KEY}")
    private String stripeApiKey;

    @Value("${WEBHOOK_SECRET}")
    private String endpointSecret;

    private PurchaseRepository purchaseRepository;

    private CustomerDetailRepository customerDetailRepository;

    private OngoingPurchaseRepository ongoingPurchaseRepository;

    private SeatStatusRepository seatStatusRepository;

    private ModelMapper modelMapper;
    private final KafkaTemplate<String, TicketMailDetailDTO> kafkaTemplate;


    @Autowired
    public PurchaseServiceImpl(KafkaTemplate kafkaTemplate, PurchaseRepository purchaseRepository, CustomerDetailRepository customerDetailRepository, OngoingPurchaseRepository ongoingPurchaseRepository, SeatStatusRepository seatStatusRepository, ModelMapper modelMapper){
        this.kafkaTemplate = kafkaTemplate;
        this.purchaseRepository = purchaseRepository;
        this.customerDetailRepository = customerDetailRepository;
        this.ongoingPurchaseRepository = ongoingPurchaseRepository;
        this.seatStatusRepository = seatStatusRepository;
        this.modelMapper = modelMapper;
    }


    /**
     * This method will be called when a payment intent has been completed. It calls the savePurchase function
     * to save the purchase into the database for persistence.
     * @param payload
     * @param sigHeader
     * @return ResponseEntity<?>
     */
    public ResponseEntity<?> paymentIntentStatus(String payload, String sigHeader){
        Event event;

        try{
            event = Webhook.constructEvent(payload, sigHeader, endpointSecret);
        } catch (JsonSyntaxException | SignatureVerificationException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }

        EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
        StripeObject stripeObject = null;
        if (dataObjectDeserializer.getObject().isPresent()) {
            stripeObject = dataObjectDeserializer.getObject().get();
        } else {
            // Deserialization failed, probably due to an API version mismatch.
            // Refer to the Javadoc documentation on `EventDataObjectDeserializer` for
            // instructions on how to handle this case, or return an error here.
        }

        switch (event.getType()) {

            case "payment_intent.processing" : {
                PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                System.out.println("Payment intent with id: " + paymentIntent.getId() + " submitted. Awaiting success/failure.");
                break;
            }
            case "payment_intent.amount_capturable_updated" : {
                System.out.println("Customer's payment is authorised and ready for capture.");
                break;
            }
            case "payment_intent.succeeded": {
                PaymentIntent paymentIntent = (PaymentIntent) stripeObject;
                //creates and saves a new customer detail with email if it doesn't exist in the database
                saveCustomerDetailIfNotExists(paymentIntent.getMetadata().get("customerEmail"));
                // saves the purchase into database and deletes the corresponding ongoing purchase
                // savePurchase(paymentIntent);
                // removeOngoingPurchaseInSeatStatus(getOngoingPurchaseByPaymentIntent(paymentIntent.getId()));
                ongoingPurchaseRepository.delete(getOngoingPurchaseByPaymentIntent(paymentIntent.getId()));
                System.out.println("Payment intent with id: " + paymentIntent.getId() + " has succeeded and purchase is saved successfully");
                break;
            }
            case "payment_intent.payment_failed": {
                PaymentIntent paymentIntent = (PaymentIntent) event.getDataObjectDeserializer().getObject().get();
                System.out.println("Payment intent with id: " + paymentIntent.getId() + " failed. Customer's payment was declined by a card network or otherwise expired.");
                break;
            }
            default:
                System.out.println("Unhandled event type: " + event.getType());
        }

        return ResponseEntity.ok("");
    }


    /**
     * This method returns a purchase corresponding to a payment intent in the form of a purchaseShortDTO.
     * It is meant for the frontend to retrieve a purchase detail to be shown as a payment summary for the customer post-payment.
     * @param paymentIntentId
     * @return PurchaseShortDTO
     * @throws StripeException
     */

    public PurchaseShortDTO getPurchaseByPaymentIntent(String paymentIntentId) throws StripeException {

        savePurchaseIfNotExist(paymentIntentId);

        Purchase purchase = purchaseRepository.findByPaymentIntentId(paymentIntentId).get();

        PurchaseShortDTO purchaseShortDTO = modelMapper.map(purchase, PurchaseShortDTO.class);
        CustomerDetailDTO customerDetailDTO = modelMapper.map(purchase.getCustomerDetail(), CustomerDetailDTO.class);

        List<SeatDetailDTO> seatDetailDTOList = new ArrayList<SeatDetailDTO>();
        for(SeatStatus eachSeatStatus : purchase.getSeatStatuses()){
            SeatDetailDTO seatDetailDTO = modelMapper.map(eachSeatStatus.getSeat(), SeatDetailDTO.class);
            seatDetailDTOList.add(seatDetailDTO);
        }

        MovieScheduleTime movieScheduleTime = purchase.getSeatStatuses().get(0).getMovieScheduleTime();
        MovieScheduleDate movieScheduleDate = movieScheduleTime.getMovieScheduleDate();
        Hall hall = movieScheduleTime.getHall();
        Movie movie = movieScheduleDate.getMovie();

        purchaseShortDTO.setSeatDetails(seatDetailDTOList);
        purchaseShortDTO.setMovie(modelMapper.map(movie, MovieShortDTO.class));
        purchaseShortDTO.setMovieTime(movieScheduleTime.getShowTime());
        purchaseShortDTO.setMovieDate(movieScheduleDate.getShowDate());
        purchaseShortDTO.setHallNumber(hall.getNumber());
        purchaseShortDTO.setCustomerDetail(customerDetailDTO);

        return purchaseShortDTO;
    }

    public void savePurchaseIfNotExist(String paymentIntentId) throws StripeException {
        Stripe.apiKey = stripeApiKey;

        if(purchaseRepository.findByPaymentIntentId(paymentIntentId).isEmpty()) {
            PaymentIntent paymentIntent = PaymentIntent.retrieve(paymentIntentId);
            if (paymentIntent.getStatus().equals("succeeded")) {
                Charge charge = Charge.retrieve(paymentIntent.getLatestCharge());
                saveCustomerDetailIfNotExists(charge.getBillingDetails().getEmail());
                savePurchase(paymentIntent, charge.getBillingDetails().getEmail());
            }
        }
    }

    /**
     * This method saves the purchase made into database given the payment intent.
     * @param paymentIntent
     *
     */
    public void savePurchase(PaymentIntent paymentIntent, String email) {

        CustomerDetail customerDetail = customerDetailRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer Email: " + email + " does not exists."));
        OngoingPurchase ongoingPurchase = getOngoingPurchaseByPaymentIntent(paymentIntent.getId());

        Purchase purchase = Purchase.builder()
                .paymentIntentId(paymentIntent.getId())
                .customerDetail(customerDetail)
                .paidAmount(paymentIntent.getAmount())
                .dateTime(LocalDateTime.now())
                .build();

        purchaseRepository.save(purchase);

        // change seat status from 1 to 2 (reserved to sold)
        List<SeatStatus> seatStatuses = ongoingPurchase.getSeatStatus();
        for(SeatStatus seatStatus : seatStatuses) {
            seatStatus.setState(2);
            seatStatus.setOngoingPurchase(null);
            seatStatus.setPurchase(purchase);
            seatStatusRepository.save(seatStatus);
        }

        ongoingPurchaseRepository.delete(getOngoingPurchaseByPaymentIntent(paymentIntent.getId()));

        Purchase purchase1 = purchaseRepository.findByPaymentIntentId(paymentIntent.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Purchase with payment intent id: " + paymentIntent.getId() + " does not exist."));

        purchase1.setSeatStatuses(seatStatuses);

//        sendMail(pur)

        purchaseRepository.save(purchase1);
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
        log.info("KAFKA MAILS SEND");
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

    public CustomerDetail getCustomerDetailByPaymentIntent(PaymentIntent paymentIntent){
        CustomerDetail customerDetail = customerDetailRepository.findByEmail(paymentIntent.getMetadata().get("customerEmail"))
                .orElseThrow(() -> new ResourceNotFoundException("CustomerDetail with email: " + paymentIntent.getMetadata().get("customerEmail") + " does not exist."));
        return customerDetail;
    }

    public OngoingPurchase getOngoingPurchaseByPaymentIntent(String paymentIntentId){
        OngoingPurchase ongoingPurchase = ongoingPurchaseRepository.findByToken(paymentIntentId)
                .orElseThrow(() -> new ResourceNotFoundException("Ongoing purchase with token: " + paymentIntentId + " does not exist."));
        return ongoingPurchase;
    }


//    public void removeOngoingPurchaseInSeatStatus(OngoingPurchase ongoingPurchase){
//        List<SeatStatus> seatStatusList = ongoingPurchase.getSeatStatus();
//        for(SeatStatus seatStatus : seatStatusList){
//            seatStatus.setOngoingPurchase(null);
//            seatStatusRepository.save(seatStatus);
//        }
//    }


}
