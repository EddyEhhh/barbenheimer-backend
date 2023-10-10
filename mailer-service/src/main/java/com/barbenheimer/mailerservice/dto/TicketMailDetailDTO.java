package com.barbenheimer.mailerservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TicketMailDetailDTO {

    private String customerEmail;

    private long purchaseId;

    private String movieTitle;

    private String movieAgeRating;

    private String movieShowtime;

    private String hallNumber;

    private String ticketSeats;

    private String purchaseDetail;

    private String purchaseTotalPrice;


}
