package com.barbenheimer.ticket.serviceImpl;


import com.barbenheimer.ticket.dto.MovieShortDTO;
import com.barbenheimer.ticket.dto.OngoingPurchaseDetailDTO;
import com.barbenheimer.ticket.dto.OngoingPurchaseTokenDTO;
import com.barbenheimer.ticket.dto.SeatDetailDTO;
import com.barbenheimer.ticket.exception.ResourceNotFoundException;
import com.barbenheimer.ticket.model.*;
import com.barbenheimer.ticket.repository.OngoingPurchaseRepository;
import com.barbenheimer.ticket.repository.SeatStatusRepository;
import com.barbenheimer.ticket.service.OngoingPurchaseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public String createCustomerIdentifyingToken(){
        StringBuilder token = new StringBuilder();
        long currentTimeInMillisecond = Instant.now().toEpochMilli();
        token.append(currentTimeInMillisecond).append(UUID.randomUUID().toString());
        return token.toString().replace("-","");
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


}
