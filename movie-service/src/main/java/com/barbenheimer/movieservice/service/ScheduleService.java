package com.barbenheimer.movieservice.service;

import com.barbenheimer.movieservice.dto.HallScheduleSeatDetailDTO;
import com.barbenheimer.movieservice.dto.OngoingPurchaseTokenDTO;
import com.barbenheimer.movieservice.dto.SeatSelectDTO;

import java.util.*;

public interface ScheduleService {

    public HallScheduleSeatDetailDTO getSeats(long showTimeId);


    public OngoingPurchaseTokenDTO selectSeats(long showTimeId, List<SeatSelectDTO> seatSelectDTOs);


}
