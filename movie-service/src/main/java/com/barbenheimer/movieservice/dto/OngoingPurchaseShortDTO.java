package com.barbenheimer.movieservice.dto;

import com.barbenheimer.movieservice.model.SeatStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OngoingPurchaseShortDTO {

    private List<SeatSelectDTO> seats;

    private String token;


}
