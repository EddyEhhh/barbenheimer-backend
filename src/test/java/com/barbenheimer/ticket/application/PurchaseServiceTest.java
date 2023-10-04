package com.barbenheimer.ticket.application;

import com.barbenheimer.customer.model.CustomerDetail;
import com.barbenheimer.ticket.dto.PurchaseDTO;
import com.barbenheimer.ticket.dto.SeatStatusDetailDTO;
import com.barbenheimer.ticket.model.Purchase;
import com.barbenheimer.ticket.repository.PurchaseRepository;
import com.barbenheimer.ticket.serviceImpl.PurchaseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {

    @Mock
    private Clock clock;

    @Mock
    private PurchaseRepository purchases;

    @InjectMocks
    private PurchaseServiceImpl purchaseService;

    private  static ZonedDateTime NOW = ZonedDateTime.of(
            2023,
            10,
            4,
            12,
            30,
            30,
            0,
            ZoneId.of("GMT")
    );

    /**
     * This method sets up a fake clock returning a fixed timing.
     */
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        when(clock.getZone()).thenReturn(NOW.getZone());
        when(clock.instant()).thenReturn(NOW.toInstant());
    }


    //TODO: needs revising after serviceImpl method equivalent is complete.
    @Test
    void addPurchase_ReturnSavedPurchase(){

        //arrange
        CustomerDetail customerDetail = CustomerDetail.builder()
                .email("abcdefg@gmail.com")
                .build();

        SeatStatusDetailDTO seatStatusDetailDTO = new SeatStatusDetailDTO();

        PurchaseDTO purchaseDTO = PurchaseDTO.builder()
                .priceInCents(800L)
                .quantity(1L)
                .build();

        Purchase purchase = Purchase.builder()
                .customerDetail(customerDetail)
                .dateTime(LocalDateTime.now(clock))
                .paidAmount(purchaseDTO.getPriceInCents() * purchaseDTO.getQuantity())
                .build();

        //mock the save operation
        when(purchases.save(any(Purchase.class))).thenReturn(purchase);

        //act
        ResponseEntity<?> response = purchaseService.savePurchase(customerDetail, seatStatusDetailDTO, purchaseDTO, clock);

        assertNotNull(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        verify(purchases).save(purchase);

    }



}
