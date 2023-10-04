package com.barbenheimer.ticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MovieScheduleDateDetailDTO {

    private long id;

    private LocalDate showDate;

    private List<MovieScheduleTimeDetailDTO> movieScheduleTimes;

}
