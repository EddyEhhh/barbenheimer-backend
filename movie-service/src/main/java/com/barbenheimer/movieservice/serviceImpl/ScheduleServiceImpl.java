package com.barbenheimer.movieservice.serviceImpl;

import java.time.LocalDateTime;
import java.util.*;

import com.barbenheimer.movieservice.dto.*;
import com.barbenheimer.movieservice.model.*;
import com.barbenheimer.movieservice.repository.OngoingPurchaseRepository;
import com.barbenheimer.movieservice.service.OngoingPurchaseService;
import com.barbenheimer.movieservice.service.ScheduleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.barbenheimer.movieservice.exception.AlreadyExistsException;
import com.barbenheimer.movieservice.repository.MovieScheduleTimeRepository;
import com.barbenheimer.movieservice.repository.SeatRepository;
import com.barbenheimer.movieservice.repository.SeatStatusRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {

    private final MovieScheduleTimeRepository movieScheduleTimeRepository;

    private final SeatStatusRepository seatStatusRepository;

    private final OngoingPurchaseRepository ongoingPurchaseRepository;

    private final SeatRepository seatRepository;
     
    private final OngoingPurchaseService ongoingPurchaseService;

    private final ModelMapper modelMapper;

    @Autowired
    public ScheduleServiceImpl(ModelMapper modelMapper, OngoingPurchaseService ongoingPurchaseService, MovieScheduleTimeRepository movieScheduleTimeRepository, SeatRepository seatRepository, SeatStatusRepository seatStatusRepository, OngoingPurchaseRepository ongoingPurchaseRepository) {

        this.modelMapper = modelMapper;
        this.movieScheduleTimeRepository = movieScheduleTimeRepository;
        this.seatStatusRepository = seatStatusRepository;
        this.ongoingPurchaseRepository = ongoingPurchaseRepository;
        this.seatRepository = seatRepository;
        this.ongoingPurchaseService = ongoingPurchaseService;

    }

    /**
     * get a list of seats based on a specific show schedule
     *
     * @param showTimeId selected show schedule
     * @return HallScheduleSeatDetailDTO
     */
    @Override
    public HallScheduleSeatDetailDTO getSeats(long showTimeId) { // can use hashmap to solve as well
        MovieScheduleTime movieScheduleTime = movieScheduleTimeRepository.findById(showTimeId);
        MovieScheduleDate movieScheduleDate = movieScheduleTime.getMovieScheduleDate();
        Hall hall = movieScheduleTime.getHall();
        Movie movie = movieScheduleDate.getMovie();

        List<Seat> seats = hall.getSeats();
        // List<ScheduleSeatDetailDTO> scheduleSeatDetailDTO = new ArrayList<>();
        HallScheduleSeatDetailDTO hallScheduleSeatDetailDTO = modelMapper.map(hall, HallScheduleSeatDetailDTO.class);
        List<ScheduleSeatDetailDTO> scheduleSeatDetailDTOList = hallScheduleSeatDetailDTO.getSeats();
        scheduleSeatDetailDTOList.forEach(dto -> dto.setPrice(movie.getBasePrice()));
        hallScheduleSeatDetailDTO.setMovie(modelMapper.map(movie, MovieShortDTO.class));
        hallScheduleSeatDetailDTO.setShowdate(movieScheduleDate.getShowDate());
        hallScheduleSeatDetailDTO.setShowtime(movieScheduleTime.getShowTime());
        ongoingPurchaseService.invalidateAllExpiredPurchaseToken();

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

    /**
     * select a list of seats based on a specific show schedule
     *
     * @param showTimeId selected show schedule
     * @return HallScheduleSeatDetailDTO
     */
    @Override
    public OngoingPurchaseTokenDTO selectSeats(long showTimeId, OngoingPurchaseShortDTO ongoingPurchaseShortDTO) throws AlreadyExistsException{ // haven't purchase, so status is 1 (temp reserve)
        MovieScheduleTime movieScheduleTime = movieScheduleTimeRepository.findById(showTimeId);
        Hall hall = movieScheduleTime.getHall();
        Movie movie = movieScheduleTime.getMovieScheduleDate().getMovie();

        List<SeatStatus> seatStatuses = new ArrayList<>();
        OngoingPurchase ongoingPurchase = new OngoingPurchase();
        for (SeatSelectDTO selected : ongoingPurchaseShortDTO.getSeats()) {
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

        int numberOfTicket = ongoingPurchaseShortDTO.getSeats().size();

        ongoingPurchase.setSeatStatus(seatStatuses);
        ongoingPurchase.setToken(ongoingPurchaseShortDTO.getToken());
        ongoingPurchase.setExpireTimeStamp(LocalDateTime.now().plusMinutes(10));
        ongoingPurchase.setTotalPrice(movie.getBasePrice()*numberOfTicket);
        ongoingPurchaseRepository.save(ongoingPurchase);
        seatStatusRepository.saveAll(seatStatuses);


//        HallScheduleSeatDetailDTO hallScheduleSeatDetailDTO = modelMapper.map(hall, HallScheduleSeatDetailDTO.class);

        return new OngoingPurchaseTokenDTO(ongoingPurchase.getToken());
    }
}
