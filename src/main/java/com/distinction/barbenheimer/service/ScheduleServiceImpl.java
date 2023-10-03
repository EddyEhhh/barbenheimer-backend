package com.distinction.barbenheimer.service;

import java.time.LocalDateTime;
import java.util.*;

import com.distinction.barbenheimer.dto.*;
import com.distinction.barbenheimer.exception.ResourceNotFoundException;
import com.distinction.barbenheimer.model.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.distinction.barbenheimer.exception.AlreadyExistsException;
import com.distinction.barbenheimer.model.OngoingPurchase;
import com.distinction.barbenheimer.repository.MovieScheduleTimeRepository;
import com.distinction.barbenheimer.repository.OngoingPurchaseRepository;
import com.distinction.barbenheimer.repository.SeatRepository;
import com.distinction.barbenheimer.repository.SeatStatusRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private MovieScheduleTimeRepository movieScheduleTimeRepository;

    private SeatStatusRepository seatStatusRepository;

    private OngoingPurchaseRepository ongoingPurchaseRepository;

    private SeatRepository seatRepository;
     
    private OngoingPurchaseService ongoingPurchaseService;

    private ModelMapper modelMapper;
    @Autowired
    public ScheduleServiceImpl(ModelMapper modelMapper, OngoingPurchaseService ongoingPurchaseService, MovieScheduleTimeRepository movieScheduleTimeRepository, SeatRepository seatRepository, SeatStatusRepository seatStatusRepository, OngoingPurchaseRepository ongoingPurchaseRepository) {

        this.modelMapper = modelMapper;
        this.movieScheduleTimeRepository = movieScheduleTimeRepository;
        this.seatStatusRepository = seatStatusRepository;
        this.ongoingPurchaseRepository = ongoingPurchaseRepository;
        this.seatRepository = seatRepository;
        this.ongoingPurchaseService = ongoingPurchaseService;

    }

    @Override
    public HallScheduleSeatDetailDTO getHallLayout(long showTimeId) { // can use hashmap to solve as well
        MovieScheduleTime movieScheduleTime = movieScheduleTimeRepository.findById(showTimeId);
        Hall hall = movieScheduleTime.getHall();
        List<Seat> seats = hall.getSeats();
        // List<ScheduleSeatDetailDTO> scheduleSeatDetailDTO = new ArrayList<>();
        HallScheduleSeatDetailDTO hallScheduleSeatDetailDTO = modelMapper.map(hall, HallScheduleSeatDetailDTO.class);
        List<ScheduleSeatDetailDTO> scheduleSeatDetailDTOList = hallScheduleSeatDetailDTO.getSeats();

        invalidateAllExpiredPurchaseToken();

        for(int seatIndex = 0 ; seatIndex < hall.getSeats().size() ; seatIndex++ ){
            Optional<SeatStatus> seatStatus = seatStatusRepository.findByMovieScheduleTimeAndSeat(movieScheduleTime, seats.get(seatIndex));
            if(seatStatus.isPresent()){
                ScheduleSeatDetailDTO tempScheduleSeatDetailDTO = scheduleSeatDetailDTOList.get(seatIndex);
                tempScheduleSeatDetailDTO.setState(seatStatus.get().getState());
                scheduleSeatDetailDTOList.set(seatIndex, tempScheduleSeatDetailDTO);
            }

        }


        return hallScheduleSeatDetailDTO;
    }

    public void invalidateAllExpiredPurchaseToken(){
        List<OngoingPurchase> ongoingPurchases = ongoingPurchaseRepository.findAll();
        for(OngoingPurchase ongoingPurchase : ongoingPurchases){
            if(ongoingPurchase.getExpireTimeStamp().compareTo(LocalDateTime.now()) == -1){
                removeOngoingPurchase(ongoingPurchase);
            }
        }
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

    private void removeOngoingPurchase(OngoingPurchase ongoingPurchase){

        if(ongoingPurchase == null){
            return;
        }
        seatStatusRepository.deleteAllInBatch(ongoingPurchase.getSeatStatus());
        ongoingPurchaseRepository.delete(ongoingPurchase);

    }

    @Override
    public OngoingPurchaseTokenDTO selectSeats(long showTimeId, List<SeatSelectDTO> seatSelectDTOs) throws AlreadyExistsException{ // haven't purchase, so status is 1 (temp reserve)
        MovieScheduleTime movieScheduleTime = movieScheduleTimeRepository.findById(showTimeId);
        Hall hall = movieScheduleTime.getHall();
        Movie movie = movieScheduleTime.getMovieScheduleDate().getMovie();

        List<SeatStatus> seatStatuses = new ArrayList<>();
        OngoingPurchase ongoingPurchase = new OngoingPurchase();
        for (SeatSelectDTO selected : seatSelectDTOs) {
            String rowCharacter = selected.getRowCharacter();
            int columnNumber = selected.getColumnNumber();

            Seat seat = seatRepository.findByHallAndRowCharacterAndColumnNumber(hall, rowCharacter, columnNumber);

            // check whether status exists, if it does, throw exeception
            Optional<SeatStatus> seatStatus = seatStatusRepository.findByMovieScheduleTimeAndSeat(movieScheduleTime, seat);
            if (seatStatus.isPresent()) {
                if (seatStatus.get().getState() == 1) {
                    throw new AlreadyExistsException("error.seats.status.reserve");
                } else if(seatStatus.get().getState() == 2) {
                    throw new AlreadyExistsException("error.seats.status.unavailable");
                } else{
                    throw new AlreadyExistsException("error.seats.generic");
                }
            } else {
                SeatStatus newSeatStatus = new SeatStatus();
                newSeatStatus.setMovieScheduleTime(movieScheduleTime);
                newSeatStatus.setSeat(seat);
                newSeatStatus.setState(1);
                newSeatStatus.setOngoingPurchase(ongoingPurchase);

                seatStatuses.add(newSeatStatus);

            }
        }

        int numberOfTicket = seatSelectDTOs.size();

        ongoingPurchase.setSeatStatus(seatStatuses);
        ongoingPurchase.setToken(ongoingPurchaseService.createCustomerIdentifyingToken());
        ongoingPurchase.setExpireTimeStamp(LocalDateTime.now().plusMinutes(10));
        ongoingPurchase.setTotalPrice(movie.getBasePrice()*numberOfTicket);
        ongoingPurchaseRepository.save(ongoingPurchase);
        seatStatusRepository.saveAll(seatStatuses);


        OngoingPurchaseTokenDTO ongoingPurchaseTokenDTO = new OngoingPurchaseTokenDTO(ongoingPurchase.getToken());



//        HallScheduleSeatDetailDTO hallScheduleSeatDetailDTO = modelMapper.map(hall, HallScheduleSeatDetailDTO.class);

        return ongoingPurchaseTokenDTO;
    }
}
