package com.barbenheimer.movieservice.service;

import com.barbenheimer.movieservice.dto.OngoingPurchaseDetailDTO;
import com.barbenheimer.movieservice.dto.OngoingPurchaseShortDTO;
import com.barbenheimer.movieservice.dto.OngoingPurchaseTokenDTO;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;

public interface OngoingPurchaseService {

    public String createCustomerIdentifyingToken();

    public OngoingPurchaseDetailDTO getDetail(OngoingPurchaseTokenDTO ongoingPurchaseTokenDTO);

    public boolean validateOngoingPurchaseToken(String token);

    public void invalidateAllExpiredPurchaseToken();

//   public OngoingPurchaseShortDTO getOngoingPurchase(OngoingPurchaseTokenDTO ongoingPurchaseTokenDTO);

//    public ResponseEntity<?> createOngoingPurchase(OngoingPurchaseShortDTO ongoingPurchaseShortDTO);

    public ResponseEntity<?> checkIfValidToken(OngoingPurchaseTokenDTO ongoingPurchaseTokenDTO) throws StripeException;
}
