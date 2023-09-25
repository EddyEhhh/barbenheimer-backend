package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.DTO.HallScheduleSeatDetailDTO;

public interface ScheduleService {

    public HallScheduleSeatDetailDTO getHallLayout(long showTimeId);


}
