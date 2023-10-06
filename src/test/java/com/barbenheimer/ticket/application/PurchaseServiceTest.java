package com.barbenheimer.ticket.application;

import com.barbenheimer.ticket.model.CustomerDetail;
import com.barbenheimer.ticket.dto.PurchaseDTO;
import com.barbenheimer.ticket.dto.SeatStatusDetailDTO;
import com.barbenheimer.ticket.model.Purchase;
import com.barbenheimer.ticket.repository.PurchaseRepository;
import com.barbenheimer.ticket.serviceImpl.PurchaseServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {

    @Mock
    private PurchaseRepository purchases;

    @InjectMocks
    private PurchaseServiceImpl purchaseService;


    //TODO: needs revising after serviceImpl method equivalent is complete.
    @Test
    void addPurchase_ReturnSavedPurchase(){
        SeatStatusDetailDTO seatStatusDetailDTO = new SeatStatusDetailDTO();
        PurchaseDTO purchaseDTO = PurchaseDTO.builder().priceInCents(800L).quantity(1L).build();
        Purchase purchase = generateFakePurchase(purchaseDTO);

        when(purchases.save(any(Purchase.class))).thenReturn(purchase);

        ResponseEntity<?> response = purchaseService.savePurchase(purchase.getCustomerDetail(), seatStatusDetailDTO, purchaseDTO);

        assertNotNull(response.getBody());
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertEquals(purchase, response.getBody());
        verify(purchases).save(purchase);
    }

    Purchase generateFakePurchase(PurchaseDTO purchaseDTO){
        CustomerDetail customerDetail = CustomerDetail.builder().email("abcdefg@gmail.com").build();
        return Purchase.builder()
                .customerDetail(customerDetail)
                .dateTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS))
                .paidAmount(purchaseDTO.getPriceInCents() * purchaseDTO.getQuantity())
                .build();
    }



}
