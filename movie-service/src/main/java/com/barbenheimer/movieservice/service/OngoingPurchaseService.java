package com.barbenheimer.movieservice.service;

import com.barbenheimer.movieservice.dto.OngoingPurchaseDetailDTO;
import com.barbenheimer.movieservice.dto.OngoingPurchaseTokenDTO;
import com.barbenheimer.movieservice.dto.PaymentIntentValidationDTO;
import com.stripe.exception.StripeException;

public interface OngoingPurchaseService {

    public OngoingPurchaseDetailDTO getDetail(String paymentIntentId);

    public boolean validateOngoingPurchaseToken(String token);

    public void invalidateAllExpiredPurchaseToken();

    public PaymentIntentValidationDTO checkIfValidToken(String paymentIntentId) throws StripeException;
}
