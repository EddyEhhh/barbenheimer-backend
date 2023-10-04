package com.barbenheimer.ticket.application;

import com.barbenheimer.ticket.dto.MovieShortDTO;
import com.barbenheimer.ticket.dto.OngoingPurchaseDetailDTO;
import com.barbenheimer.ticket.dto.OngoingPurchaseTokenDTO;
import com.barbenheimer.ticket.exception.ResourceNotFoundException;
import com.barbenheimer.ticket.model.*;
import com.barbenheimer.ticket.repository.OngoingPurchaseRepository;
import com.barbenheimer.ticket.repository.SeatStatusRepository;
import com.barbenheimer.ticket.service.OngoingPurchaseService;
import com.barbenheimer.ticket.serviceImpl.OngoingPurchaseServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OngoingPurchaseServiceTest {

    @Mock
    private OngoingPurchaseRepository ongoingPurchases;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private SeatStatusRepository seatStatuses;

    @InjectMocks
    private OngoingPurchaseServiceImpl ongoingPurchaseService;

    @Test
    void getDetail_ReturnCreatedDTO(){

        //arrange
        String token = "axl873fpq2bg3kjd920231014204204";
        LocalDateTime expiryTimeStamp = LocalDateTime.of(2023, 10, 4, 20, 57, 4);

        OngoingPurchaseTokenDTO ongoingPurchaseTokenDTO = new OngoingPurchaseTokenDTO(token);

        Seat seat = Seat.builder().rowCharacter("A").columnNumber(5).x(5).y(5).build();

        Hall hall = Hall.builder().number(5).build();

        Movie movie = Movie.builder().title("Test").build();

        MovieScheduleDate movieScheduleDate = MovieScheduleDate.builder().movie(movie).showDate(LocalDate.of(2023, 10, 4)).build();

        MovieScheduleTime movieScheduleTime = MovieScheduleTime.builder().movieScheduleDate(movieScheduleDate).showTime(LocalTime.of(8, 30)).hall(hall).build();

        SeatStatus seatStatus = SeatStatus.builder().seat(seat).movieScheduleTime(movieScheduleTime).state(1).build();

        List<SeatStatus> seatStatusList = new ArrayList<>();
        seatStatusList.add(seatStatus);

        OngoingPurchase ongoingPurchase = OngoingPurchase.builder().token(token).expireTimeStamp(expiryTimeStamp).totalPrice(8.00).seatStatus(seatStatusList).build();

        OngoingPurchaseDetailDTO ongoingPurchaseDetailDTO = OngoingPurchaseDetailDTO.builder()
                .movie(new MovieShortDTO())
                .movieTime(movieScheduleTime.getShowTime())
                .movieDate(movieScheduleDate.getShowDate())
                .expireTimeStamp(expiryTimeStamp)
                .totalPrice(8.00)
                .hallNumber(5)
                .build();

        when(ongoingPurchases.findByToken(token)).thenReturn(Optional.ofNullable(ongoingPurchase));
        when(modelMapper.map(ongoingPurchase, OngoingPurchaseDetailDTO.class)).thenReturn(ongoingPurchaseDetailDTO);

        //act
        OngoingPurchaseDetailDTO existingOngoingPurchaseDetailDTO = ongoingPurchaseService.getDetail(ongoingPurchaseTokenDTO);

        //assert
        assertNotNull(existingOngoingPurchaseDetailDTO);
        verify(ongoingPurchases).findByToken(token);
    }

    @Test
    void getDetail_ThrowsException(){

        String token = "axl873fpq2bg3kjd920231014204204";
        OngoingPurchaseTokenDTO ongoingPurchaseTokenDTO = new OngoingPurchaseTokenDTO(token);

        when(ongoingPurchases.findByToken(token)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            ongoingPurchaseService.getDetail(ongoingPurchaseTokenDTO);
        });

        assertEquals("error.token.notFound", exception.getMessage());
    }


    @Test
    void invalidateAllExpiredPurchaseToken(){

        Seat seat = Seat.builder().rowCharacter("A").columnNumber(5).x(5).y(5).build();

        Hall hall = Hall.builder().number(5).build();

        Movie movie = Movie.builder().title("Test").build();

        MovieScheduleDate movieScheduleDate = MovieScheduleDate.builder().movie(movie).showDate(LocalDate.of(2023, 10, 4)).build();

        MovieScheduleTime movieScheduleTime = MovieScheduleTime.builder().movieScheduleDate(movieScheduleDate).showTime(LocalTime.of(8, 30)).hall(hall).build();

        SeatStatus seatStatus = SeatStatus.builder().seat(seat).movieScheduleTime(movieScheduleTime).state(1).build();

        LocalDateTime expiryTimeStamp = LocalDateTime.of(2023, 10, 4, 20, 57, 4);

        List<SeatStatus> seatStatusList = new ArrayList<>();
        seatStatusList.add(seatStatus);

        String token = "axl873fpq2bg3kjd920231014204204";

        OngoingPurchase ongoingPurchase = OngoingPurchase.builder()
                .token(token)
                .expireTimeStamp(expiryTimeStamp)
                .seatStatus(seatStatusList)
                .build();

        List<OngoingPurchase> ongoingPurchaseList = new ArrayList<>();
        ongoingPurchaseList.add(ongoingPurchase);

        when(ongoingPurchases.findAll()).thenReturn(ongoingPurchaseList);

        //act
        ongoingPurchaseService.invalidateAllExpiredPurchaseToken();

        //assert

        verify(seatStatuses).deleteAllInBatch(ongoingPurchase.getSeatStatus());
        verify(ongoingPurchases).delete(ongoingPurchase);

        Optional<OngoingPurchase> deletedOngoingPurchase = ongoingPurchases.findByToken(token);
        assertEquals(Optional.empty(), deletedOngoingPurchase);
    }

    @Test
    void validateOngoingPurchaseToken_ReturnTrue(){
        String token = "axl873fpq2bg3kjd920231014204204";
        LocalDateTime expiryTimeStamp = LocalDateTime.now().plusMinutes(10);

        OngoingPurchase ongoingPurchase = OngoingPurchase.builder()
                .token(token)
                .expireTimeStamp(expiryTimeStamp)
                .build();

        when(ongoingPurchases.findByToken(token)).thenReturn(Optional.ofNullable(ongoingPurchase));

        boolean result = ongoingPurchaseService.validateOngoingPurchaseToken(token);
        assertTrue(result);
    }

    @Test
    void validateOngoingPurchaseToken_RemoveExpiredOngoingPurchase(){

        String token = "axl873fpq2bg3kjd920231014204204";

        Seat seat = Seat.builder().rowCharacter("A").columnNumber(5).x(5).y(5).build();

        Hall hall = Hall.builder().number(5).build();

        Movie movie = Movie.builder().title("Test").build();

        MovieScheduleDate movieScheduleDate = MovieScheduleDate.builder().movie(movie).showDate(LocalDate.of(2023, 10, 4)).build();

        MovieScheduleTime movieScheduleTime = MovieScheduleTime.builder().movieScheduleDate(movieScheduleDate).showTime(LocalTime.of(8, 30)).hall(hall).build();

        SeatStatus seatStatus = SeatStatus.builder().seat(seat).movieScheduleTime(movieScheduleTime).state(1).build();

        List<SeatStatus> seatStatusList = new ArrayList<>();
        seatStatusList.add(seatStatus);

        LocalDateTime expiryTimeStamp = LocalDateTime.of(2023, 10, 4, 20, 57, 4);

        OngoingPurchase ongoingPurchase = OngoingPurchase.builder()
                .token(token)
                .expireTimeStamp(expiryTimeStamp)
                .seatStatus(seatStatusList)
                .build();

        when(ongoingPurchases.findByToken(token)).thenReturn(Optional.ofNullable(ongoingPurchase));

        //act
        boolean result = ongoingPurchaseService.validateOngoingPurchaseToken(token);

        //assert
        verify(seatStatuses).deleteAllInBatch(ongoingPurchase.getSeatStatus());
        verify(ongoingPurchases).delete(ongoingPurchase);

        assertFalse(result);

    }

    @Test
    void validateOngoingPurchaseToken_ThrowsException(){
        String token = "axl873fpq2bg3kjd920231014204204";

        when(ongoingPurchases.findByToken(token)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            ongoingPurchaseService.validateOngoingPurchaseToken(token);
        });

        assertEquals("error.token.notFound", exception.getMessage());
    }



}
