package com.barbenheimer.movieservice.service;

import com.barbenheimer.movieservice.dto.OngoingPurchaseDetailDTO;
import com.barbenheimer.movieservice.dto.OngoingPurchaseTokenDTO;

public interface OngoingPurchaseService {

    public String createCustomerIdentifyingToken();

    public OngoingPurchaseDetailDTO getDetail(OngoingPurchaseTokenDTO ongoingPurchaseTokenDTO);

    public boolean validateOngoingPurchaseToken(String token);

    public void invalidateAllExpiredPurchaseToken();

}
