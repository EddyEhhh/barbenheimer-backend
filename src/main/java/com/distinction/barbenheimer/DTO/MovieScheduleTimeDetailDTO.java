package com.distinction.barbenheimer.DTO;

import com.distinction.barbenheimer.model.Hall;
import com.distinction.barbenheimer.model.MovieScheduleDate;
import com.distinction.barbenheimer.model.SeatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieScheduleTimeDetailDTO {

    private long id;
}
