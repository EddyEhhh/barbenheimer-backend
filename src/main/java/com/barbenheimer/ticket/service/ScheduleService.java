package com.barbenheimer.ticket.service;

import com.barbenheimer.ticket.dto.HallScheduleSeatDetailDTO;
import com.barbenheimer.ticket.dto.OngoingPurchaseTokenDTO;
import com.barbenheimer.ticket.dto.SeatSelectDTO;

import java.util.*;

public interface ScheduleService {

    public HallScheduleSeatDetailDTO getSeats(long showTimeId);


    public OngoingPurchaseTokenDTO selectSeats(long showTimeId, List<SeatSelectDTO> seatSelectDTOs);


}
