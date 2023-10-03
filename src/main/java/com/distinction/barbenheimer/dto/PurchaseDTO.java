package com.distinction.barbenheimer.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class PurchaseDTO {

    private Long Id;

    private String movieName;

    private Long priceInCents;

    private Long quantity;

}
