package com.barbenheimer.movieservice.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HallScheduleSeatDetailDTO {

    private MovieShortDTO movie;

    private LocalDateTime showtime;

    private int number;

    private int width;

    private int height;

    private List<ScheduleSeatDetailDTO> seats;


}
