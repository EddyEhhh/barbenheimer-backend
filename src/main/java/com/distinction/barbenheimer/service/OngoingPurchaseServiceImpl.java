package com.distinction.barbenheimer.service;


import com.distinction.barbenheimer.dto.MovieShortDTO;
import com.distinction.barbenheimer.dto.OngoingPurchaseDetailDTO;
import com.distinction.barbenheimer.dto.OngoingPurchaseTokenDTO;
import com.distinction.barbenheimer.dto.SeatDetailDTO;
import com.distinction.barbenheimer.exception.ResourceNotFoundException;
import com.distinction.barbenheimer.model.*;
import com.distinction.barbenheimer.repository.OngoingPurchaseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OngoingPurchaseServiceImpl implements OngoingPurchaseService {



    private OngoingPurchaseRepository ongoingPurchaseRepository;

    private ModelMapper modelMapper;


    @Autowired
    public OngoingPurchaseServiceImpl(OngoingPurchaseRepository ongoingPurchaseRepository, ModelMapper modelMapper ){
        this.ongoingPurchaseRepository = ongoingPurchaseRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public String createCustomerIdentifyingToken(){
        StringBuilder token = new StringBuilder();
        long currentTimeInMillisecond = Instant.now().toEpochMilli();
        token.append(currentTimeInMillisecond).append(UUID.randomUUID().toString());
        return token.toString().replace("-","");
    }

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


}
