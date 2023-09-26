package com.distinction.barbenheimer.service;

import com.distinction.barbenheimer.DTO.PurchaseDTO;
import com.distinction.barbenheimer.DTO.SeatStatusDetailDTO;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

public interface PurchaseService{

    public ResponseEntity<?> savePurchase(SeatStatusDetailDTO seatStatusDetailDTO, PurchaseDTO purchaseDTO, HttpSession httpSession);
}
