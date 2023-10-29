package com.barbenheimer.movieservice.service;

import com.barbenheimer.movieservice.dto.OngoingPurchaseDetailDTO;
import com.barbenheimer.movieservice.dto.OngoingPurchaseShortDTO;
import com.barbenheimer.movieservice.dto.OngoingPurchaseTokenDTO;
import com.barbenheimer.movieservice.dto.PaymentIntentValidationDTO;
import com.stripe.exception.StripeException;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface OngoingPurchaseService {

    public OngoingPurchaseDetailDTO getDetail(OngoingPurchaseTokenDTO ongoingPurchaseTokenDTO);

    public boolean validateOngoingPurchaseToken(String token);

    public void invalidateAllExpiredPurchaseToken();

    public PaymentIntentValidationDTO checkIfValidToken(OngoingPurchaseTokenDTO ongoingPurchaseTokenDTO) throws StripeException;
}
