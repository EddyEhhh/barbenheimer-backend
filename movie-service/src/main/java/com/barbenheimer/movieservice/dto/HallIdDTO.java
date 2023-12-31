package com.barbenheimer.movieservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class HallIdDTO {

    private Long id; // hall id

    private int number; // hall number

}
