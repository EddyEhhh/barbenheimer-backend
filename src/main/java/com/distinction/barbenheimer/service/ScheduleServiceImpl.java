package com.distinction.barbenheimer.service;

import java.time.LocalDateTime;
import java.util.*;

import com.distinction.barbenheimer.exception.ResourceNotFoundException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import com.distinction.barbenheimer.DTO.HallScheduleSeatDetailDTO;
import com.distinction.barbenheimer.DTO.ScheduleSeatDetailDTO;
import com.distinction.barbenheimer.DTO.SeatNumberDTO;
import com.distinction.barbenheimer.DTO.SeatSelectDTO;
import com.distinction.barbenheimer.DTO.SeatStatusDetailDTO;
import com.distinction.barbenheimer.exception.AlreadyExistsException;
import com.distinction.barbenheimer.model.Hall;
import com.distinction.barbenheimer.model.MovieScheduleTime;
import com.distinction.barbenheimer.model.OngoingPurchase;
import com.distinction.barbenheimer.model.Seat;
import com.distinction.barbenheimer.model.OngoingPurchase;
import com.distinction.barbenheimer.model.SeatStatus;
import com.distinction.barbenheimer.repository.MovieScheduleTimeRepository;
import com.distinction.barbenheimer.repository.OngoingPurchaseRepository;
import com.distinction.barbenheimer.repository.SeatRepository;
import com.distinction.barbenheimer.repository.SeatStatusRepository;

import ch.qos.logback.core.model.Model;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private MovieScheduleTimeRepository movieScheduleTimeRepository;

    private SeatStatusRepository seatStatusRepository;

    private OngoingPurchaseRepository ongoingPurchaseRepository;

    private SeatRepository seatRepository;
     
    private SeatService seatService;

    private ModelMapper modelMapper;
    @Autowired
    public ScheduleServiceImpl(ModelMapper modelMapper, MovieScheduleTimeRepository movieScheduleTimeRepository, SeatRepository seatRepository, SeatStatusRepository seatStatusRepository, OngoingPurchaseRepository ongoingPurchaseRepository) {

        this.modelMapper = modelMapper;
        this.movieScheduleTimeRepository = movieScheduleTimeRepository;
        this.seatStatusRepository = seatStatusRepository;
        this.ongoingPurchaseRepository = ongoingPurchaseRepository;
        this.seatRepository = seatRepository;

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
        OngoingPurchase ongoingPurchase = ongoingPurchaseRepository.findByPurchaseToken(token);
        if(ongoingPurchase == null){
            throw new ResourceNotFoundException("error.token.notFound");
        }else if(ongoingPurchase.getExpireTimeStamp().compareTo(LocalDateTime.now()) == -1){ //check expiry of ongoingPurchase
            removeOngoingPurchase(ongoingPurchase);
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
    public HallScheduleSeatDetailDTO selectSeats(long showTimeId, List<SeatSelectDTO> seatSelectDTOs) throws AlreadyExistsException{ // haven't purchase, so status is 1 (temp reserve)
        MovieScheduleTime movieScheduleTime = movieScheduleTimeRepository.findById(showTimeId);
        Hall hall = movieScheduleTime.getHall();

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

        ongoingPurchase.setSeatStatus(seatStatuses);
        ongoingPurchase.setPurchaseToken(LocalDateTime.now().toString());
        ongoingPurchase.setExpireTimeStamp(LocalDateTime.now().plusMinutes(10));
        ongoingPurchaseRepository.save(ongoingPurchase);
        seatStatusRepository.saveAll(seatStatuses);

        HallScheduleSeatDetailDTO hallScheduleSeatDetailDTO = modelMapper.map(hall, HallScheduleSeatDetailDTO.class);

        return hallScheduleSeatDetailDTO;
    }
}
