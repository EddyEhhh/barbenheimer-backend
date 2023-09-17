package com.distinction.barbenheimer.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.springframework.cglib.core.Local;

import com.distinction.barbenheimer.DTO.SeatDetailDTO;
import com.distinction.barbenheimer.DTO.SeatSelectDTO;
import com.distinction.barbenheimer.DTO.SeatStatusDetailDTO;
import com.distinction.barbenheimer.model.CustomerDetail;
import com.distinction.barbenheimer.model.Hall;
import com.distinction.barbenheimer.model.Seat;
import com.distinction.barbenheimer.model.SeatStatus;

public interface SeatService{

    public List<SeatDetailDTO> getLayout(Long movieId, LocalDateTime showDate, LocalDateTime showTime);

    public List<SeatStatusDetailDTO> getStatus(Long movieId, LocalDateTime showDate, LocalDateTime showTime);

    public List<Seat> selectedBy(CustomerDetail customerDetail);


}
