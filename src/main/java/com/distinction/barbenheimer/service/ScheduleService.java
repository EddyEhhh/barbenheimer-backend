package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.DTO.HallScheduleSeatDetailDTO;
import com.distinction.barbenheimer.DTO.SeatSelectDTO;

import java.util.*;

public interface ScheduleService {

    public HallScheduleSeatDetailDTO getHallLayout(long showTimeId);

    public HallScheduleSeatDetailDTO selectSeats(long showTimeId, List<SeatSelectDTO> seatSelectDTOs)


}
