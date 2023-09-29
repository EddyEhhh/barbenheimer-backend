package com.distinction.barbenheimer.service;


import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public interface SeatPurchaseService {


        public ResponseEntity<?> saveOngoingPurchase(HttpSession httpSession);

        public void deleteOngoingPurchase(HttpSession httpSession);
}
