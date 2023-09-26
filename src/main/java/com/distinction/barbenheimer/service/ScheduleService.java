package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.DTO.HallScheduleSeatDetailDTO;
import com.distinction.barbenheimer.DTO.SeatSelectDTO;
import com.distinction.barbenheimer.model.OngoingPurchase;

import java.util.*;

public interface ScheduleService {

    public HallScheduleSeatDetailDTO getHallLayout(long showTimeId);

    public boolean validateOngoingPurchaseToken(String token);

    public void invalidateAllExpiredPurchaseToken();


    public HallScheduleSeatDetailDTO selectSeats(long showTimeId, List<SeatSelectDTO> seatSelectDTOs);


}
