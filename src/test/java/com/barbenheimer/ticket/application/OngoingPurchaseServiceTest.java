package com.barbenheimer.ticket.application;

import com.barbenheimer.ticket.dto.MovieShortDTO;
import com.barbenheimer.ticket.dto.OngoingPurchaseDetailDTO;
import com.barbenheimer.ticket.dto.OngoingPurchaseTokenDTO;
import com.barbenheimer.ticket.dto.SeatDetailDTO;
import com.barbenheimer.ticket.exception.ResourceNotFoundException;
import com.barbenheimer.ticket.model.*;
import com.barbenheimer.ticket.repository.OngoingPurchaseRepository;
import com.barbenheimer.ticket.repository.SeatStatusRepository;
import com.barbenheimer.ticket.serviceImpl.OngoingPurchaseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OngoingPurchaseServiceTest {

    @Mock
    private OngoingPurchaseRepository ongoingPurchases;

    @Mock
    private SeatStatusRepository seatStatuses;

    @Spy
    private ModelMapper modelMapper;

    @InjectMocks
    private OngoingPurchaseServiceImpl ongoingPurchaseService;

    private final String token = "axl873fpq2bg3kjd920231014204204";

    @Test
    void createCustomerIdentifyingToken_ReturnValidToken(){
        String generatedToken = ongoingPurchaseService.createCustomerIdentifyingToken();
        assertNotNull(generatedToken);
        assertFalse(generatedToken.contains("-"));
    }

    @Test
    void getDetail_ReturnOngoingPurchaseDTO(){
        LocalDateTime expiryTimeStamp = LocalDateTime.now().plusMinutes(9);
        OngoingPurchaseTokenDTO ongoingPurchaseTokenDTO = new OngoingPurchaseTokenDTO(token);
        OngoingPurchase ongoingPurchase = generateFakeOngoingPurchase(expiryTimeStamp);
        OngoingPurchaseDetailDTO expectedOngoingPurchaseDetailDTO = generateFakeOngoingPurchaseDetailDTO(expiryTimeStamp, ongoingPurchase);

        when(ongoingPurchases.findByToken(token)).thenReturn(Optional.of(ongoingPurchase));

        OngoingPurchaseDetailDTO actualOngoingPurchaseDetailDTO = ongoingPurchaseService.getDetail(ongoingPurchaseTokenDTO);

        assertNotNull(actualOngoingPurchaseDetailDTO);
        assertEquals(expectedOngoingPurchaseDetailDTO, actualOngoingPurchaseDetailDTO);
        assertEquals(expectedOngoingPurchaseDetailDTO.getSeatDetails(), actualOngoingPurchaseDetailDTO.getSeatDetails());
        verify(ongoingPurchases).findByToken(token);
    }

    @Test
    void getDetail_ThrowsException(){
        OngoingPurchaseTokenDTO ongoingPurchaseTokenDTO = new OngoingPurchaseTokenDTO(token);

        when(ongoingPurchases.findByToken(token)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            ongoingPurchaseService.getDetail(ongoingPurchaseTokenDTO);
        });

        assertEquals("error.token.notFound", exception.getMessage());
    }


    @Test
    void invalidateAllExpiredPurchaseToken(){
        LocalDateTime expiryTimeStamp = LocalDateTime.now().minusMinutes(10);
        OngoingPurchase ongoingPurchase = generateFakeOngoingPurchase(expiryTimeStamp);

        List<OngoingPurchase> ongoingPurchaseList = new ArrayList<>(Collections.singletonList(ongoingPurchase));

        when(ongoingPurchases.findAll()).thenReturn(ongoingPurchaseList);

        ongoingPurchaseService.invalidateAllExpiredPurchaseToken();

        verify(seatStatuses).deleteAllInBatch(ongoingPurchase.getSeatStatus());
        verify(ongoingPurchases).delete(ongoingPurchase);
    }

    @Test
    void validateOngoingPurchaseToken_ReturnTrue(){
        LocalDateTime expiryTimeStamp = LocalDateTime.now().plusMinutes(10);
        OngoingPurchase ongoingPurchase = generateFakeOngoingPurchase(expiryTimeStamp);

        when(ongoingPurchases.findByToken(token)).thenReturn(Optional.ofNullable(ongoingPurchase));

        boolean result = ongoingPurchaseService.validateOngoingPurchaseToken(token);
        assertTrue(result);
    }

    @Test
    void validateOngoingPurchaseToken_RemoveExpiredOngoingPurchase(){
        LocalDateTime expiryTimeStamp = LocalDateTime.now().minusMinutes(10);
        OngoingPurchase ongoingPurchase = generateFakeOngoingPurchase(expiryTimeStamp);

        when(ongoingPurchases.findByToken(token)).thenReturn(Optional.ofNullable(ongoingPurchase));

        boolean result = ongoingPurchaseService.validateOngoingPurchaseToken(token);

        verify(seatStatuses).deleteAllInBatch(ongoingPurchase.getSeatStatus());
        verify(ongoingPurchases).delete(ongoingPurchase);
        assertFalse(result);
    }

    @Test
    void validateOngoingPurchaseToken_ThrowsException(){
        when(ongoingPurchases.findByToken(token)).thenReturn(Optional.empty());

        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            ongoingPurchaseService.validateOngoingPurchaseToken(token);
        });

        assertEquals("error.token.notFound", exception.getMessage());
    }


    List<SeatStatus> generateFakeSeatStatusList(){
        Seat seat = Seat.builder().rowCharacter("A").columnNumber(5).x(5).y(5).build();
        Hall hall = Hall.builder().number(5).build();
        Movie movie = Movie.builder().title("fakename").build();
        MovieScheduleDate movieScheduleDate = MovieScheduleDate.builder().movie(movie).showDate(LocalDate.of(2023, 10, 4)).build();
        MovieScheduleTime movieScheduleTime = MovieScheduleTime.builder().movieScheduleDate(movieScheduleDate).showTime(LocalTime.of(8, 30)).hall(hall).build();
        SeatStatus seatStatus = SeatStatus.builder().seat(seat).movieScheduleTime(movieScheduleTime).state(1).build();
        List<SeatStatus> seatStatusList = new ArrayList<>();
        seatStatusList.add(seatStatus);
        return seatStatusList;
    }

    OngoingPurchaseDetailDTO generateFakeOngoingPurchaseDetailDTO(LocalDateTime expiryTimeStamp, OngoingPurchase ongoingPurchase){
        List<SeatDetailDTO> seatDetailDTOList = new ArrayList<>();
        seatDetailDTOList.add(SeatDetailDTO.builder().rowCharacter("A").columnNumber(5).x(5).y(5).build());
        List<SeatStatus> seatStatusList = generateFakeSeatStatusList();

        OngoingPurchaseDetailDTO ongoingPurchaseDetailDTO = OngoingPurchaseDetailDTO.builder()
                .movie(MovieShortDTO.builder().id(0L).title("fakename").build())
                .movieTime(seatStatusList.get(0).getMovieScheduleTime().getShowTime())
                .movieDate(seatStatusList.get(0).getMovieScheduleTime().getMovieScheduleDate().getShowDate())
                .expireTimeStamp(expiryTimeStamp)
                .totalPrice(ongoingPurchase.getTotalPrice())
                .hallNumber(seatStatusList.get(0).getMovieScheduleTime().getHall().getNumber())
                .seatDetails(seatDetailDTOList)
                .build();
        return ongoingPurchaseDetailDTO;
    }

    OngoingPurchase generateFakeOngoingPurchase(LocalDateTime expiryTimeStamp){
        OngoingPurchase ongoingPurchase = OngoingPurchase.builder()
                .token(token)
                .expireTimeStamp(expiryTimeStamp)
                .totalPrice(8.00)
                .seatStatus(generateFakeSeatStatusList()).build();
        return ongoingPurchase;
    }



}
