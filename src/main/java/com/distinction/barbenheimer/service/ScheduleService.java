package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.dto.HallScheduleSeatDetailDTO;
import com.distinction.barbenheimer.dto.OngoingPurchaseTokenDTO;
import com.distinction.barbenheimer.dto.SeatSelectDTO;

import java.util.*;

public interface ScheduleService {

    public HallScheduleSeatDetailDTO getHallLayout(long showTimeId);

    public boolean validateOngoingPurchaseToken(String token);

    public void invalidateAllExpiredPurchaseToken();


    public OngoingPurchaseTokenDTO selectSeats(long showTimeId, List<SeatSelectDTO> seatSelectDTOs);


}
