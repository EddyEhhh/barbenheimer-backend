package com.barbenheimer.movieservice.serviceImpl;


import com.barbenheimer.movieservice.dto.*;
import com.barbenheimer.movieservice.model.*;
import com.barbenheimer.movieservice.repository.SeatStatusRepository;
import com.barbenheimer.movieservice.exception.ResourceNotFoundException;
import com.barbenheimer.movieservice.repository.OngoingPurchaseRepository;
import com.barbenheimer.movieservice.service.OngoingPurchaseService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OngoingPurchaseServiceImpl implements OngoingPurchaseService {

    private OngoingPurchaseRepository ongoingPurchaseRepository;

    private ModelMapper modelMapper;

    private SeatStatusRepository seatStatusRepository;


    @Autowired
    public OngoingPurchaseServiceImpl(SeatStatusRepository seatStatusRepository, OngoingPurchaseRepository ongoingPurchaseRepository, ModelMapper modelMapper ){
        this.ongoingPurchaseRepository = ongoingPurchaseRepository;
        this.modelMapper = modelMapper;
        this.seatStatusRepository = seatStatusRepository;
    }


    /**
     * This method will be called before a payment intent is confirmed. It checks to see if the token referring to an ongoing purchase
     * is still valid (within 10 mins). If it is not valid, the payment intent will be canceled.
     *
     * @param ongoingPurchaseTokenDTO
     * @return
     * @throws StripeException
     */
    public PaymentIntentValidationDTO checkIfValidToken(OngoingPurchaseTokenDTO ongoingPurchaseTokenDTO) throws StripeException {
        OngoingPurchase ongoingPurchase = getOngoingPurchaseByPaymentIntent(ongoingPurchaseTokenDTO.getToken());

        if(ongoingPurchase.getExpireTimeStamp().isBefore(LocalDateTime.now())){
            deleteOngoingPurchase(ongoingPurchaseTokenDTO.getToken());
            PaymentIntent paymentIntent = PaymentIntent.retrieve(ongoingPurchaseTokenDTO.getToken());
            paymentIntent.setCancellationReason("Token has expired.");
            try {
                PaymentIntent updatedPaymentIntent = paymentIntent.cancel();
                System.out.println("Token has expired, corresponding payment intent has been canceled.");
                return PaymentIntentValidationDTO.builder().validity(false).build();
            } catch (Error e){
                // An error is thrown by Stripe if the payment intent is already canceled or isn't in a cancelable state.
                System.out.println(e.getMessage());
                throw new RuntimeException("Payment Intent failed to cancel. " + e.getMessage());
            }
        }
        return PaymentIntentValidationDTO.builder().validity(true).build();
    }


    /**
     *
     * @param ongoingPurchaseTokenDTO
     * @return ongoingPurchaseDetailDTO
     */

    public OngoingPurchaseDetailDTO getDetail(OngoingPurchaseTokenDTO ongoingPurchaseTokenDTO){
        String token = ongoingPurchaseTokenDTO.getToken();
        Optional<OngoingPurchase> ongoingPurchaseOptional = ongoingPurchaseRepository.findByToken(token);
        if(ongoingPurchaseOptional.isEmpty()){
            throw new ResourceNotFoundException("error.token.notFound");
        }
        OngoingPurchase ongoingPurchase = ongoingPurchaseOptional.get();
        OngoingPurchaseDetailDTO ongoingPurchaseDetailDTO = modelMapper.map(ongoingPurchase, OngoingPurchaseDetailDTO.class);

        List<SeatDetailDTO> seatDetailDTOList = new ArrayList<SeatDetailDTO>();
        for(SeatStatus eachSeatStatus : ongoingPurchase.getSeatStatus()){
            SeatDetailDTO seatDetailDTO = modelMapper.map(eachSeatStatus.getSeat(), SeatDetailDTO.class);
            seatDetailDTOList.add(seatDetailDTO);
        }

        MovieScheduleTime movieScheduleTime = ongoingPurchase.getSeatStatus().get(0).getMovieScheduleTime();
        MovieScheduleDate movieScheduleDate = movieScheduleTime.getMovieScheduleDate();
        Hall hall = movieScheduleTime.getHall();
        Movie movie = movieScheduleDate.getMovie();

        ongoingPurchaseDetailDTO.setSeatDetails(seatDetailDTOList);
        ongoingPurchaseDetailDTO.setMovie(modelMapper.map(movie, MovieShortDTO.class));
        ongoingPurchaseDetailDTO.setMovieTime(movieScheduleTime.getShowTime());
        ongoingPurchaseDetailDTO.setMovieDate(movieScheduleDate.getShowDate());
        ongoingPurchaseDetailDTO.setHallNumber(hall.getNumber());

        return ongoingPurchaseDetailDTO;
    }


    public void invalidateAllExpiredPurchaseToken(){
        List<OngoingPurchase> ongoingPurchases = ongoingPurchaseRepository.findAll();
        for(OngoingPurchase ongoingPurchase : ongoingPurchases){
            if(ongoingPurchase.getExpireTimeStamp().compareTo(LocalDateTime.now()) == -1){
                removeOngoingPurchase(ongoingPurchase);
            }
        }
    }


    //ORPHAN
    private void removeOngoingPurchase(OngoingPurchase ongoingPurchase){

        if(ongoingPurchase == null){
            return;
        }
        seatStatusRepository.deleteAllInBatch(ongoingPurchase.getSeatStatus());
        ongoingPurchaseRepository.delete(ongoingPurchase);

    }

    public boolean validateOngoingPurchaseToken(String token){
        Optional<OngoingPurchase> ongoingPurchase = ongoingPurchaseRepository.findByToken(token);
        if(ongoingPurchase.isEmpty()){
            throw new ResourceNotFoundException("error.token.notFound");
        }else if(ongoingPurchase.get().getExpireTimeStamp().compareTo(LocalDateTime.now()) == -1){ //check expiry of ongoingPurchase
            removeOngoingPurchase(ongoingPurchase.get());
            return false;
        }else{
            return true;
        }
    }

    public OngoingPurchase getOngoingPurchaseByPaymentIntent(String paymentIntentId){
        OngoingPurchase ongoingPurchase = ongoingPurchaseRepository.findByToken(paymentIntentId)
                .orElseThrow(() -> new ResourceNotFoundException("Ongoing purchase with token: " + paymentIntentId + " does not exist."));
        return ongoingPurchase;
    }

    public void deleteOngoingPurchase(String paymentIntentId){
        ongoingPurchaseRepository.deleteByToken(paymentIntentId);
    }


}
