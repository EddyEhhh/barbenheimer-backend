package com.distinction.barbenheimer.service;

import java.time.LocalDate;
import java.util.*;

import com.distinction.barbenheimer.model.CustomerDetail;
import com.distinction.barbenheimer.model.Seat;
import com.distinction.barbenheimer.model.SeatStatus;

public interface SeatService{
    
    //public int selected(long movieId, LocalDate movieSchedules, List<SeatSelectDTO> seats, CustomerDetail customerDetail);



    public List<Seat> selectedBy(CustomerDetail customerDetail);


}
