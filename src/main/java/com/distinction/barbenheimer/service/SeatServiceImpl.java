package com.distinction.barbenheimer.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.distinction.barbenheimer.DTO.SeatDetailDTO;
import com.distinction.barbenheimer.DTO.SeatSelectDTO;
import com.distinction.barbenheimer.DTO.SeatStatusDetailDTO;
import com.distinction.barbenheimer.model.CustomerDetail;
import com.distinction.barbenheimer.model.Hall;
import com.distinction.barbenheimer.model.Seat;
import com.distinction.barbenheimer.model.SeatStatus;

@Service
public class SeatServiceImpl implements SeatService{

    private ModelMapper modelMapper;

    public SeatServiceImpl(ModelMapper modelMapper){
        this.modelMapper = modelMapper;
    }

    @Override
    public List<SeatDetailDTO> getLayout(Long movieId, LocalDateTime showDate, LocalDateTime showTime) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<SeatStatusDetailDTO> getStatus(Long movieId, LocalDateTime showDate, LocalDateTime showTime) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public List<Seat> selectedBy(CustomerDetail customerDetail) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectedBy'");
    }
    
//    @Override
//    public List<SeatStatus> status(List<SeatSelectDTO> seats, Long movieId, LocalDateTime showDate, LocalDateTime showTime) {
//         LocalDateTime currentTime = LocalDateTime.now();
//         LocalDateTime withinTime = LocalDateTime.now().plusMinutes(10);
//         for (SeatSelectDTO seatSelectDTO : seats) {
            
//         }
//         return null;
//    }
}
